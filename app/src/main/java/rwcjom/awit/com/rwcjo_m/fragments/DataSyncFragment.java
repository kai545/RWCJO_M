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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.bean.CJDownbrginfo;
import rwcjom.awit.com.rwcjo_m.bean.CJDownface;
import rwcjom.awit.com.rwcjo_m.bean.CJDownfaceinfo;
import rwcjom.awit.com.rwcjo_m.bean.CJDownpntinfo;
import rwcjom.awit.com.rwcjo_m.bean.CJDownsectsite;
import rwcjom.awit.com.rwcjo_m.dao.BrgInfo;
import rwcjom.awit.com.rwcjo_m.dao.FaceInfo;
import rwcjom.awit.com.rwcjo_m.dao.FaceNews;
import rwcjom.awit.com.rwcjo_m.dao.PntInfo;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;
import rwcjom.awit.com.rwcjo_m.event.DataSyncFragmentEvent;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownbrginfoImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownfaceImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownfaceinfoImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownpntinfoImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownsectsiteImpl;
import rwcjom.awit.com.rwcjo_m.service.BrgInfoService;
import rwcjom.awit.com.rwcjo_m.service.FaceInfoService;
import rwcjom.awit.com.rwcjo_m.service.FaceNewsService;
import rwcjom.awit.com.rwcjo_m.service.PntInfoService;
import rwcjom.awit.com.rwcjo_m.service.SecNewsService;
import rwcjom.awit.com.rwcjo_m.service.SiteNewsService;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataSyncFragment extends Fragment {
    private final String TAG="DataSyncFragment";
    private Context context;
    private View view;
    private String randomCode;
    private String username;

    private TextView loginedUsername;
    private CircularProgressButton dl_all_btn;

    private SecNewsService secNewsService;
    private SiteNewsService siteNewsService;
    private FaceNewsService faceNewsService;
    private FaceInfoService faceInfoService;
    private BrgInfoService brgInfoService;
    private PntInfoService pntInfoService;

    public DataSyncFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
        secNewsService=SecNewsService.getInstance(context);
        siteNewsService=SiteNewsService.getInstance(context);
        faceNewsService=FaceNewsService.getInstance(context);
        faceInfoService=FaceInfoService.getInstance(context);
        brgInfoService=BrgInfoService.getInstance(context);
        pntInfoService=PntInfoService.getInstance(context);
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
        view=inflater.inflate(R.layout.fragment_data_sync, container, false);
        loginedUsername=(TextView)view.findViewById(R.id.logined_username);
        loginedUsername.setText(username + "（" + randomCode + "）");

        dl_all_btn = (CircularProgressButton) view.findViewById(R.id.data_sync_dl_all);
        dl_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_all_btn.setEnabled(false);
                downloadSectionSite();
            }

        });
        return view;
    }
    //处理事件
    public void onEventMainThread(DataSyncFragmentEvent event) {
        CircularProgressButton btn=(CircularProgressButton)view.findViewById(event.getId());
        btn.setIndeterminateProgressMode(true);
        btn.setProgress(event.getCircularProgressButtonProgress());
    }
    //下载标段和工点信息
    private void downloadSectionSite(){
        Tasks.executeInBackground(context, new BackgroundWork<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInBackground() throws Exception {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_section, 50));//进度条
                Map<String, Object> result = new HashMap<String, Object>();//存放ID;
                //CommonTools.showProgressDialog(MainActivity.this, "正在登录……");
                List<SiteNews> sitelist_all = new ArrayList<SiteNews>();
                if (randomCode.length() != 0) {
                    result.put("randomCode", randomCode);
                    CJDownsectsiteImpl mCJDownsectsiteImpl = new CJDownsectsiteImpl();
                    for (int i = 0; i < 3; i++) {
                        CJDownsectsite thismCJDownsectsite = mCJDownsectsiteImpl.getCJDownsectsite("0", i + "", randomCode);//调用接口

                        if (thismCJDownsectsite.getFlag() == 0) {
                            //有工点信息
                            result.put("section", thismCJDownsectsite.getSecObj());
                            secNewsService.saveSecNews(thismCJDownsectsite.getSecObj());
                            List<SiteNews> sitelist = thismCJDownsectsite.getSitelist();//单个工点类型下的工点列表
                            sitelist_all.addAll(sitelist);//将所有类型的工点放入总list
                            for (int j = 0; j < sitelist.size(); j++) {
                                SiteNews siteNews = sitelist.get(j);
                                siteNews.setSitetype("" + i);
                                siteNews.setF_sectionid(thismCJDownsectsite.getSecObj().getSectid());
                                siteNewsService.saveSiteNews(siteNews);
                            }
                            //开始保存数据

                        } else {
                            Log.i(TAG, "下载工点信息：" + thismCJDownsectsite.getMsg());
                        }

                    }
                    result.put("sites", sitelist_all);
                }
                return result;
            }

        }, new Completion<Map<String, Object>>() {
            @Override
            public void onSuccess(Context context, Map<String, Object> result) {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_section, 100));
                downloadFace(result);//开始下载断面信息
            }

            @Override
            public void onError(Context context, Exception e) {
                //showError(e);
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_section, -1));
            }
        });
    }

    //下载断面信息（包括断面详情） lastResult包含标段、工点列表
    private void downloadFace(Map<String,Object> lastResult){
        final Map<String,Object> readyResult=lastResult;
        Tasks.executeInBackground(context, new BackgroundWork<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInBackground() throws Exception {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_face, 50));//进度条
                Map<String, Object> face_retult = readyResult;//存放ID;
                List<SiteNews> siteList = (List<SiteNews>) face_retult.get("sites");//获取标段和断面
                List<FaceNews> faceNews_all=new ArrayList<FaceNews>();
                for (int i = 0; i < siteList.size(); i++) {
                    CJDownfaceImpl mCJDownfaceImpl = new CJDownfaceImpl();
                    String siteid=siteList.get(i).getSiteid();
                    CJDownface mCJDownface = mCJDownfaceImpl.getCJDownface(siteid, ValueConfig.FACE_START_DATE, ValueConfig.FACE_END_DATE, "" + readyResult.get("randomCode"));
                    List<FaceNews> faceNewsList=mCJDownface.getFacelist();//单个工点下的断面列表
                    faceNews_all.addAll(faceNewsList);//将所有断面基础信息放入总list
                    for (int j = 0; j <faceNewsList.size(); j++) {
                        FaceNews faceNews=faceNewsList.get(j);
                        faceNews.setF_siteid(siteid);
                        faceNewsService.saveFaceNews(faceNews);//保存当前断面的基础信息
                        //以下开始查询并保存断面的详细信息
                        CJDownfaceinfoImpl cjDownfaceinfoImpl=new CJDownfaceinfoImpl();
                        CJDownfaceinfo cjDownfaceinfo= cjDownfaceinfoImpl.getCJDownfaceinfo(siteid, faceNews.getFaceId(), "" + readyResult.get("randomCode"));
                        FaceInfo faceInfo=cjDownfaceinfo.getFaceinfoObj();
                        if (faceInfo!=null){
                            faceInfo.setF_siteid(siteid);
                            faceInfoService.saveFaceInfo(faceInfo);
                        }

                        ////以下开始查询并保存梁体的详细信息
                        CJDownbrginfoImpl mCJDownbrginfoImpl=new CJDownbrginfoImpl();
                        CJDownbrginfo cjDownbrginfo=mCJDownbrginfoImpl.getCJDownbrginfo(siteid, faceNews.getFaceId(), "" + readyResult.get("randomCode"));
                        BrgInfo brgInfo=cjDownbrginfo.getBrgInfoObj();
                        if (brgInfo!=null){
                            brgInfo.setF_siteid(siteid);
                            brgInfoService.saveBrgInfo(brgInfo);
                        }


                    }
                }
                readyResult.put("faceNewses",faceNews_all);
                face_retult.putAll(readyResult);
                return face_retult;
            }

        }, new Completion<Map<String, Object>>() {
            @Override
            public void onSuccess(Context context, Map<String, Object> result) {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_face, 100));
                downloadPntInfo(result);
            }

            @Override
            public void onError(Context context, Exception e) {
                //showError(e);
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_face, -1));
            }
        });
    }


    //下载测点信息 lastResult包含标段、工点列表、断面基础信息列表
    private void downloadPntInfo(Map<String,Object> lastResult){
        final Map<String,Object> readyResult=lastResult;
        Tasks.executeInBackground(context, new BackgroundWork<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInBackground() throws Exception {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_testPoint, 50));//进度条
                Map<String, Object> pnt_retult = readyResult;//存放ID;

                List<FaceNews> faceNews_all=(List<FaceNews>)pnt_retult.get("faceNewses");
                for (int i = 0; i < faceNews_all.size(); i++) {
                    String faceid=faceNews_all.get(i).getFaceId();
                    for (int j = 1; j < 3; j++) {//1 在测，2 停测
                        CJDownpntinfoImpl mCJDownpntinfoImpl=new CJDownpntinfoImpl();
                        CJDownpntinfo mCJDownpntinfo= mCJDownpntinfoImpl.getCJDownputinfo(faceid, j+"", "" + readyResult.get("randomCode"));
                        List<PntInfo> pntInfoList=mCJDownpntinfo.getPntInfoList();
                        for (int k = 0; k < pntInfoList.size(); k++) {
                            PntInfo mPntInfo=pntInfoList.get(k);
                            mPntInfo.setF_faceid(faceid);
                            pntInfoService.savePntInfoLists(mCJDownpntinfo.getPntInfoList());
                        }

                    }
                }

                return pnt_retult;
            }

        }, new Completion<Map<String, Object>>() {
            @Override
            public void onSuccess(Context context, Map<String, Object> result) {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_testPoint, 100));
            }

            @Override
            public void onError(Context context, Exception e) {
                //showError(e);
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_testPoint, -1));
            }
        });
    }

}
