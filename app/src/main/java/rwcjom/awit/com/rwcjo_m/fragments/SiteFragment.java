package rwcjom.awit.com.rwcjo_m.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.List;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.adapter.SiteListAdapter;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.service.SiteNewsService;

/**
 * 工点信息列表
 * Created by Fantasy on 15/4/23.
 */
public class SiteFragment extends ListFragment {
    private static final String TAG = "SiteFragment";
    private ListView listView;
    private SiteListAdapter siteListAdapter;
    private Context context;
    private SiteNewsService siteNewsService;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private List<SiteNews> siteData;
    private String sectid;

    public static SiteFragment newInstance() {
        SiteFragment f = new SiteFragment();
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
        siteNewsService=SiteNewsService.getInstance(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sectid=getArguments().getString("sectid");
        manager = getFragmentManager();
        Tasks.executeInBackground(context, new BackgroundWork<List<SiteNews>>() {
            @Override
            public List<SiteNews> doInBackground() throws Exception {
                siteData = siteNewsService.querySiteNewsBySection(sectid);

                return siteData;
            }
        }, new Completion<List<SiteNews>>() {
            @Override
            public void onSuccess(Context context, List<SiteNews> result) {
                siteListAdapter = new SiteListAdapter(context, result);
                setListAdapter(siteListAdapter);
            }

            @Override
            public void onError(Context context, Exception e) {
                EventBus.getDefault().post(new MainActivityEvent(false));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_site,null);
        return view;
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
        bundle.putString("siteid", siteData.get(position).getSiteid());
        faceBaseFragment.setArguments(bundle);
        transaction.replace(R.id.face_baseinfo_list, faceBaseFragment, "detail");
        transaction.commit();
    }
}
