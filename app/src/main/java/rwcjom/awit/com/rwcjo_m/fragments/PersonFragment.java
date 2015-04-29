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
import rwcjom.awit.com.rwcjo_m.dao.PersonInfo;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.service.PersonInfoService;

/**
 * 人员列表
 * Created by Fantasy on 15/4/29.
 */
public class PersonFragment extends ListFragment {
    private ArrayAdapter<String> adapter;
    private List<String> userData;
    private Context context;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private PersonInfoService personInfoService;

    public static PersonFragment newInstance() {
        PersonFragment f = new PersonFragment();
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
        personInfoService=PersonInfoService.getInstance(context);
        userData=new ArrayList<String>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String sectid=getArguments().getString("sectid");
        manager =getFragmentManager();
        Tasks.executeInBackground(context, new BackgroundWork<List<String>>() {
            @Override
            public List<String> doInBackground() throws Exception {
                List<PersonInfo> personInfoList = personInfoService.queryPersonBySite(sectid);
                for (int i = 0; i < personInfoList.size(); i++) {
                    PersonInfo mPersonInfo=personInfoList.get(i);
                    userData.add(mPersonInfo.getUsername());
                }
                return userData;
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
