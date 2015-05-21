package rwcjom.awit.com.rwcjo_m.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.List;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.dao.SecNews;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.service.SecNewsService;

/**
 * Created by Fantasy on 15/4/8.
 */
public class ShuiZhunXianLuFragmentContainer extends Fragment {
    private final String LOGTAG="IndexFragment";

    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private Context context;
    private SecNewsService secNewsService;
    private SecNews secNews;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
        secNewsService=SecNewsService.getInstance(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new MainActivityEvent("水准线路"));

        //开始获取标段数据
        Tasks.executeInBackground(context, new BackgroundWork<List<SecNews>>() {
            @Override
            public List<SecNews> doInBackground() throws Exception {
                //考虑有多个标段的情况（实际只有一个标段）
                return secNewsService.loadAllSecNews();
            }
        }, new Completion<List<SecNews>>() {
            @Override
            public void onSuccess(Context context, List<SecNews> result) {
                for (int i = 0; i < result.size(); i++) {
                    secNews = result.get(i);
                }
                initPager();
                initTabsValue();
            }

            @Override
            public void onError(Context context, Exception e) {
                EventBus.getDefault().post(new MainActivityEvent(false));
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab,null);
    }

    private void initPager(){
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) getView().findViewById(R.id.tabs);
        mViewPager = (ViewPager) getView().findViewById(R.id.pager);
        String[] titles={ "待测量", "待上传", "全部" };
        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(),titles));
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                Log.i(LOGTAG, "argo:" + arg0);
                if (arg0 == 1) {
                    //EventBus.getDefault().post(new MainActivityEvent(true));
                } else {
                    //EventBus.getDefault().post(new MainActivityEvent(false));
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * mPagerSlidingTabStrip默认值配置
     *
     */
    private void initTabsValue() {
        // 底部游标颜色
        mPagerSlidingTabStrip.setIndicatorColor(Color.WHITE);
        // tab的分割线颜色
        mPagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);
        // tab背景
        mPagerSlidingTabStrip.setBackgroundColor(Color.parseColor("#ff259b2c"));
        // tab底线高度
        mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, getResources().getDisplayMetrics()));
        // 游标高度
        mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, getResources().getDisplayMetrics()));
        // 选中的文字颜色
        //mPagerSlidingTabStrip.setSelectedTextColor(Color.WHITE);
        // 正常文字颜色
        mPagerSlidingTabStrip.setTextColor(Color.WHITE);
    }

    /* ***************FragmentPagerAdapter***************** */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;


        public MyPagerAdapter(FragmentManager fm,String[] titles) {
            super(fm);
            this.titles=titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return ShuiZhunXianLuFragment.newInstance(position,secNews.getSectid());
        }

    }

}
