package rwcjom.awit.com.rwcjo_m.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;

/**
 * Created by Fantasy on 15/4/8.
 */
public class IndexFragment extends Fragment {
    private final String LOGTAG="IndexFragment";

    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new MainActivityEvent("首页"));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_index,null);
    }

    @Override
    public void onPause() {
        Log.e("HJJ", "ArrayListFragment **** onPause...");
        // TODO Auto-generated method stub
        super.onPause();
    }


}
