package rwcjom.awit.com.rwcjo_m.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.FaceNews;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.service.FaceNewsService;

/**
 * Created by Fantasy on 15/4/24.
 */
public class FaceBaseFragment extends ListFragment {
    private ArrayAdapter<String> adapter;
    private List<String> faceNameData;
    private List<String> faceIdData;
    private Context context;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FaceNewsService faceNewsService;


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
        faceNewsService=FaceNewsService.getInstance(context);
        faceNameData=new ArrayList<String>();
        faceIdData=new ArrayList<String>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String siteid=getArguments().getString("siteid");
        Tasks.executeInBackground(context, new BackgroundWork<List<String>>() {
            @Override
            public List<String> doInBackground() throws Exception {
                List<FaceNews> faceNewsList= faceNewsService.queryFaceNewsBySite(siteid);
                for (int i = 0; i <faceNewsList.size() ; i++) {
                    faceIdData.add(faceNewsList.get(i).getFaceId());
                    faceNameData.add(faceNewsList.get(i).getFaceName());
                }

                return faceNameData;
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
        manager =getFragmentManager();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        transaction = manager.beginTransaction();
        //String str = adapter.getItem(position);
        FaceBaseFragment faceBaseFragment = new FaceBaseFragment();
        /**
         * 使用Bundle类存储传递数据
         */
        Bundle bundle = new Bundle();
        bundle.putString("faceid", faceIdData.get(position));
        faceBaseFragment.setArguments(bundle);
        transaction.replace(R.id.face_baseinfo_list, faceBaseFragment, "detail");
        transaction.commit();
    }
}
