package rwcjom.awit.com.rwcjo_m.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.adapter.SiteListAdapter;
import rwcjom.awit.com.rwcjo_m.dao.SecNews;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;
import rwcjom.awit.com.rwcjo_m.service.SecNewsService;
import rwcjom.awit.com.rwcjo_m.service.SiteNewsService;

/**
 * Created by Fantasy on 15/4/23.
 */
public class SiteFragment extends Fragment {
    private static final String TAG = "SiteFragment";
    private ListView listView;
    private Context context;
    private SecNewsService secNewsService;
    private SiteNewsService siteNewsService;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_site,null);
        listView=(ListView)view.findViewById(R.id.site_list);
        SiteListAdapter siteListAdapter = new SiteListAdapter(context,getData());
        listView.setAdapter(siteListAdapter);
        return view;
    }

    private List<SiteNews> getData(){
        List<SiteNews> data=new ArrayList<SiteNews>();
        List<SecNews> secNewsList=secNewsService.loadAllSecNews();
        for (int i = 0; i < secNewsList.size(); i++) {
            List<SiteNews> siteNewsList=siteNewsService.querySiteNewsBySection(secNewsList.get(i).getSectid());
            data.addAll(siteNewsList);
        }
        return data;
    }
}
