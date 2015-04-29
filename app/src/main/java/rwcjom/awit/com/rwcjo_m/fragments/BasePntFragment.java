package rwcjom.awit.com.rwcjo_m.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.dao.BasePntInfo;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.service.BasePntInfoService;

/**
 * 基点列表
 * Created by Fantasy on 15/4/29.
 */
public class BasePntFragment extends ListFragment {
    private ArrayAdapter<String> adapter;
    private List<String> pntData;
    private Context context;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private BasePntInfoService basePntInfoService;

    public static BasePntFragment newInstance() {
        BasePntFragment f = new BasePntFragment();
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
        basePntInfoService=BasePntInfoService.getInstance(context);
        pntData=new ArrayList<String>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String sectid=getArguments().getString("sectid");
        manager =getFragmentManager();
        Tasks.executeInBackground(context, new BackgroundWork<List<String>>() {
            @Override
            public List<String> doInBackground() throws Exception {
                List<BasePntInfo> basePntInfosList = basePntInfoService.queryBasePntInfoBySite(sectid);
                for (int i = 0; i < basePntInfosList.size(); i++) {
                    BasePntInfo mBasePntInfo=basePntInfosList.get(i);
                    String km=mBasePntInfo.getBasepntvar();
                    pntData.add(mBasePntInfo.getBasepntname()+"，"
                            +mBasePntInfo.getBasepnthigh()+"，"
                            +mBasePntInfo.getBasepntnum()+km.substring(0,km.length()-3)+"+"+km.substring(km.length()-3));
                }
                return pntData;
            }
        }, new Completion<List<String>>() {
            @Override
            public void onSuccess(Context context, List<String> result) {
                adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, result);
                setListAdapter(adapter);
            }

            @Override
            public void onError(Context context, Exception e) {
                EventBus.getDefault().post(new MainActivityEvent(false));
            }
        });
    }
}
