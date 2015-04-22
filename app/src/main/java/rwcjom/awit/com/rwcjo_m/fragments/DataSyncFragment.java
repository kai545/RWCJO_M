package rwcjom.awit.com.rwcjo_m.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.bean.CJDownsectsite;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.event.DataSyncFragmentEvent;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownsectsiteImpl;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataSyncFragment extends Fragment {
    private final String TAG="DataSyncFragment";
    private Context context;
    private String randomCode;
    private String username;

    private TextView loginedUsername;
    private CircularProgressButton dl_section_btn;

    public DataSyncFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new MainActivityEvent("数据同步"));
        EventBus.getDefault().register(this);
        randomCode=getArguments().getString("randomCode");
        username=getArguments().getString("username");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_data_sync, container, false);
        loginedUsername=(TextView)view.findViewById(R.id.logined_username);
        loginedUsername.setText(username + "（" + randomCode + "）");

        dl_section_btn = (CircularProgressButton) view.findViewById(R.id.data_sync_dl_section);
        dl_section_btn.setIndeterminateProgressMode(true);
        dl_section_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tasks.executeInBackground(context, new BackgroundWork<List<CJDownsectsite>>() {
                    @Override
                    public List<CJDownsectsite> doInBackground() throws Exception {
                        EventBus.getDefault().post(new DataSyncFragmentEvent(50));
                        List<CJDownsectsite> mCJDownsectsite = null;
                        //CommonTools.showProgressDialog(MainActivity.this, "正在登录……");
                        if (randomCode.length() != 0) {
                            mCJDownsectsite = new ArrayList<CJDownsectsite>();
                            CJDownsectsiteImpl mCJDownsectsiteImpl = new CJDownsectsiteImpl();
                            for (int i = 0; i < 3; i++) {
                                List<CJDownsectsite> thismCJDownsectsite = mCJDownsectsiteImpl.getCJDownsectsite("0", i + "", randomCode);//调用接口
                                Log.i(TAG, "本次（" + i + "）数据条目：" + thismCJDownsectsite.size());
                                if (thismCJDownsectsite.size() >= 2) {
                                    //有工点信息
                                    mCJDownsectsite.addAll(thismCJDownsectsite);
                                    Log.i(TAG, "总数据条目：" + mCJDownsectsite.size());
                                    Log.i(TAG, mCJDownsectsite + "");

                                    //开始保存数据

                                } else if (mCJDownsectsite.size() == 1) {
                                    //有错误信息
                                } else {
                                    //其他信息
                                }

                            }
                        }
                        return mCJDownsectsite;
                    }

                }, new Completion<List<CJDownsectsite>>() {
                    @Override
                    public void onSuccess(Context context, List<CJDownsectsite> result) {
                        if (result != null && result.size() != 0) {
                            EventBus.getDefault().post(new DataSyncFragmentEvent(100));
                        } else {
                            CommonTools.showToast(context, "Exception：" + pubUtil.exception.getExceptionMsg());
                        }
                    }

                    @Override
                    public void onError(Context context, Exception e) {
                        //showError(e);
                        EventBus.getDefault().post(new DataSyncFragmentEvent(-1));
                    }
                });
            }

        });
        return view;
    }
    //处理事件
    public void onEventMainThread(DataSyncFragmentEvent event) {
        dl_section_btn.setProgress(event.getCircularProgressButtonProgress());
    }

}
