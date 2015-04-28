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

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.adapter.SiteListAdapter;
import rwcjom.awit.com.rwcjo_m.dao.SecNews;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.service.SecNewsService;
import rwcjom.awit.com.rwcjo_m.service.SiteNewsService;

/**
 * Created by Fantasy on 15/4/23.
 */
public class SiteFragment extends ListFragment {
    private static final String TAG = "SiteFragment";
    private ListView listView;
    private SiteListAdapter siteListAdapter;
    private Context context;
    private SecNewsService secNewsService;
    private SiteNewsService siteNewsService;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private List<SiteNews> siteData;

    private int position;
    public static SiteFragment newInstance() {
        SiteFragment f = new SiteFragment();
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
        secNewsService=SecNewsService.getInstance(context);
        siteNewsService=SiteNewsService.getInstance(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_site,null);
        //listView=(ListView)view.findViewById(R.id.site_list);
        //listView.setOnItemClickListener(this);

        Tasks.executeInBackground(context, new BackgroundWork<SiteListAdapter>() {
            @Override
            public SiteListAdapter doInBackground() throws Exception {
                EventBus.getDefault().post(new MainActivityEvent(true));
                siteListAdapter = new SiteListAdapter(context, getData());
                return siteListAdapter;
            }
        }, new Completion<SiteListAdapter>() {
            @Override
            public void onSuccess(Context context, SiteListAdapter result) {
                EventBus.getDefault().post(new MainActivityEvent(false));
                setListAdapter(result);
            }

            @Override
            public void onError(Context context, Exception e) {
                EventBus.getDefault().post(new MainActivityEvent(false));
            }
        });


        return view;
    }

    private List<SiteNews> getData(){
        List<SiteNews> data=new ArrayList<SiteNews>();
        List<SecNews> secNewsList=secNewsService.loadAllSecNews();//考虑有多个标段的情况（实际只有一个标段）
        for (int i = 0; i < secNewsList.size(); i++) {
            EventBus.getDefault().post(new MainActivityEvent(secNewsList.get(i).getSectname()));//设置标段作为标题
            List<SiteNews> siteNewsList=siteNewsService.querySiteNewsBySection(secNewsList.get(i).getSectid());
            data.addAll(siteNewsList);
        }
        siteData=data;
        return data;
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
