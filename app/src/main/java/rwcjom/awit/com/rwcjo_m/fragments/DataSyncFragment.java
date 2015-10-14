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
import rwcjom.awit.com.rwcjo_m.bean.CJDownbasepnt;
import rwcjom.awit.com.rwcjo_m.bean.CJDownface;
import rwcjom.awit.com.rwcjo_m.bean.CJDownfaceinfo;
import rwcjom.awit.com.rwcjo_m.bean.CJDownline;
import rwcjom.awit.com.rwcjo_m.bean.CJDownperson;
import rwcjom.awit.com.rwcjo_m.bean.CJDownpntinfo;
import rwcjom.awit.com.rwcjo_m.bean.CJDownsectsite;
import rwcjom.awit.com.rwcjo_m.dao.BasePntInfo;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.FaceInfo;
import rwcjom.awit.com.rwcjo_m.dao.FaceNews;
import rwcjom.awit.com.rwcjo_m.dao.Line;
import rwcjom.awit.com.rwcjo_m.dao.PersonInfo;
import rwcjom.awit.com.rwcjo_m.dao.PntInfo;
import rwcjom.awit.com.rwcjo_m.dao.SecNews;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;
import rwcjom.awit.com.rwcjo_m.event.DataSyncFragmentEvent;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownbasepntImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownfaceImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownfaceinfoImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownlineImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownpersonImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownpntinfoImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownsectsiteImpl;
import rwcjom.awit.com.rwcjo_m.service.BasePntInfoService;
import rwcjom.awit.com.rwcjo_m.service.BrgInfoService;
import rwcjom.awit.com.rwcjo_m.service.BwInfoService;
import rwcjom.awit.com.rwcjo_m.service.FaceInfoService;
import rwcjom.awit.com.rwcjo_m.service.FaceNewsService;
import rwcjom.awit.com.rwcjo_m.service.LineService;
import rwcjom.awit.com.rwcjo_m.service.PersonInfoService;
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
    private CircularProgressButton dl_all_btn,delete_all_btn;

    private SecNewsService secNewsService;
    private SiteNewsService siteNewsService;
    private FaceNewsService faceNewsService;
    private FaceInfoService faceInfoService;
    private BrgInfoService brgInfoService;
    private PntInfoService pntInfoService;
    private PersonInfoService personInfoService;
    private BasePntInfoService basePntInfoService;
    private LineService lineService;
    private BwInfoService bwInfoService;

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
        personInfoService=PersonInfoService.getInstance(context);
        basePntInfoService=BasePntInfoService.getInstance(context);
        lineService=LineService.getInstance(context);
        bwInfoService=BwInfoService.getInstance(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new MainActivityEvent("数据同步"));
        EventBus.getDefault().register(this);//注册
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
                //dl_all_btn.setEnabled(false);
                downloadSectionSite();
            }

        });

        delete_all_btn = (CircularProgressButton) view.findViewById(R.id.data_sync_delete_all);
        delete_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllLocalData();
            }

        });


        return view;
    }
    //处理事件
    public void onEventMainThread(DataSyncFragmentEvent event) {
        CircularProgressButton btn=(CircularProgressButton)view.findViewById(event.getId());
        btn.setIndeterminateProgressMode(true);//(true)就是根据你的进度可以设置现在的进度值。
        btn.setProgress(event.getCircularProgressButtonProgress());
    }


    /**
     * 清除所有数据
     *
     */
    private void deleteAllLocalData(){
        Tasks.executeInBackground(context, new BackgroundWork<String>() {
            @Override
            public String doInBackground() throws Exception {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_delete_all, 50));//进度条

                secNewsService.deleteAll();
                siteNewsService.deleteAll();
                faceNewsService.deleteAll();
                faceInfoService.deleteAll();
                brgInfoService.deleteAll();
                pntInfoService.deleteAll();
                personInfoService.deleteAll();
                basePntInfoService.deleteAll();
                lineService.deleteAll();
                bwInfoService.deleteAll();
                return "0";
            }

        }, new Completion<String>() {
            @Override
            public void onSuccess(Context context, String result) {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_delete_all, 100));
            }

            @Override
            public void onError(Context context, Exception e) {
                //showError(e);
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_delete_all, -1));
            }
        });
    }




    /**
     * 一键全部下载
     * //下载标段和工点信息
     */
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
                            result.put("section", thismCJDownsectsite.getSecObj());//存放secNews对象到map中
                            secNewsService.saveSecNews(thismCJDownsectsite.getSecObj());//插入信息到secNews表中
                            List<SiteNews> sitelist = thismCJDownsectsite.getSitelist();//单个工点类型下的工点列表
                            sitelist_all.addAll(sitelist);//将所有类型的工点放入总list
                            for (int j = 0; j < sitelist.size(); j++) {
                                SiteNews siteNews = sitelist.get(j);
                                siteNews.setSitetype("" + i);
                                siteNews.setF_sectionid(thismCJDownsectsite.getSecObj().getSectid());

                                siteNewsService.saveSiteNews(siteNews);//插入信息到SiteNews表中
                            }
                            //开始保存数据

                        } else {
                            Log.i(TAG, "下载工点信息：" + thismCJDownsectsite.getMsg());
                        }

                    }
                    result.put("sites", sitelist_all);//存放集合到map中
                }
                return result;
            }

        }, new Completion<Map<String, Object>>() {
            @Override
            public void onSuccess(Context context, Map<String, Object> result) {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_section, 100));
                downloadFace(result);//开始下载断面信息
                downloadPerson(result);//开始下载人员信息

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
                Map<String, Object> face_retult = readyResult;
                List<SiteNews> siteList = (List<SiteNews>) face_retult.get("sites");//获取标段和工点（List<SiteNews>）
                List<FaceNews> faceNews_all = new ArrayList<FaceNews>();
                for (int i = 0; i < siteList.size(); i++) {
                    CJDownfaceImpl mCJDownfaceImpl = new CJDownfaceImpl();
                    String siteid = siteList.get(i).getSiteid();
                    CJDownface mCJDownface = mCJDownfaceImpl.getCJDownface(siteid, ValueConfig.FACE_START_DATE, ValueConfig.FACE_END_DATE, "" + readyResult.get("randomCode"));
                    List<FaceNews> faceNewsList = mCJDownface.getFacelist();//单个工点下的断面列表
                    faceNews_all.addAll(faceNewsList);//将所有断面基础信息放入总list
                    for (int j = 0; j < faceNewsList.size(); j++) {
                        FaceNews faceNews = faceNewsList.get(j);
                        faceNews.setF_siteid(siteid);
                        faceNewsService.saveFaceNews(faceNews);//保存当前断面的基础信息
                        //以下开始查询并保存断面的详细信息
                        CJDownfaceinfoImpl cjDownfaceinfoImpl = new CJDownfaceinfoImpl();
                        CJDownfaceinfo cjDownfaceinfo = cjDownfaceinfoImpl.getCJDownfaceinfo(siteid, faceNews.getFaceId(), "" + readyResult.get("randomCode"));
                        FaceInfo faceInfo = cjDownfaceinfo.getFaceinfoObj();
                        if (faceInfo != null) {
                            faceInfo.setF_siteid(siteid);
                            faceInfoService.saveFaceInfo(faceInfo);
                        }

                        ////以下开始查询并保存梁体的详细信息（暂停使用）
                        /*CJDownbrginfoImpl mCJDownbrginfoImpl = new CJDownbrginfoImpl();
                        CJDownbrginfo cjDownbrginfo = mCJDownbrginfoImpl.getCJDownbrginfo(siteid, faceNews.getFaceId(), "" + readyResult.get("randomCode"));
                        BrgInfo brgInfo = cjDownbrginfo.getBrgInfoObj();
                        if (brgInfo != null) {
                            brgInfo.setF_siteid(siteid);
                            brgInfoService.saveBrgInfo(brgInfo);
                        }*/


                    }
                }
                readyResult.put("faceNewses", faceNews_all);//将集合保存到map中（list<FaceNews>）
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


    /**
     * 下载测点信息 lastResult包含标段、工点列表、断面基础信息列表
     */

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
                        CJDownpntinfo mCJDownpntinfo= mCJDownpntinfoImpl.getCJDownputinfo(faceid, j + "", "" + readyResult.get("randomCode"));
                        List<PntInfo> pntInfoList=mCJDownpntinfo.getPntInfoList();
                        for (int k = 0; k < pntInfoList.size(); k++) {
                            PntInfo mPntInfo=pntInfoList.get(k);
                            mPntInfo.setObjstate(""+j);
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

    //下载人员信息 lastResult包含标段、工点列表
    private void downloadPerson(Map<String,Object> lastResult){
        final Map<String,Object> readyResult=lastResult;
        Tasks.executeInBackground(context, new BackgroundWork<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInBackground() throws Exception {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_person, 50));//进度条
                Map<String, Object> person_retult = readyResult;//存放ID;
                SecNews section = (SecNews) person_retult.get("section");//获取标段和工点


                    String sectid=section.getSectid();
                    for (int j = 1; j < 3; j++) {//1 在测，2 停测
                        CJDownpersonImpl mCJDownpersonImpl=new CJDownpersonImpl();
                        CJDownperson mCJDownperson= mCJDownpersonImpl.getCJDownperson(sectid,j+"","" + readyResult.get("randomCode"));
                        List<PersonInfo> personInfoList=mCJDownperson.getPersonInfoList();
                        for (int k = 0; k < personInfoList.size(); k++) {
                            PersonInfo mPersonInfo=personInfoList.get(k);
                            mPersonInfo.setF_sectid(sectid);
                            mPersonInfo.setPtype(""+j);//保存人员类型
                            personInfoService.savePersonInfo(mPersonInfo);
                        }

                    }


                return person_retult;
            }

        }, new Completion<Map<String, Object>>() {
            @Override
            public void onSuccess(Context context, Map<String, Object> result) {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_person, 100));
                downloadBasePnt(result);//开始下载基点信息

            }

            @Override
            public void onError(Context context, Exception e) {
                //showError(e);
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_person, -1));
            }
        });
    }

    //下载基点信息 lastResult包含标段、工点列表
    private void downloadBasePnt(Map<String,Object> lastResult){
        final Map<String,Object> readyResult=lastResult;
        Tasks.executeInBackground(context, new BackgroundWork<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInBackground() throws Exception {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_basePoint, 50));//进度条
                Map<String, Object> basepnt_retult = readyResult;//存放ID;
                SecNews section = (SecNews) basepnt_retult.get("section");//获取标段和工点


                String sectid=section.getSectid();

                CJDownbasepntImpl mCJDownbasepntImpl = new CJDownbasepntImpl();
                CJDownbasepnt mCJDownbasepnt = mCJDownbasepntImpl.getCJDownbasepnt(sectid, "" + readyResult.get("randomCode"));
                List<BasePntInfo> basePntInfoList = mCJDownbasepnt.getBasePntInfoList();
                for (int k = 0; k < basePntInfoList.size(); k++) {
                    BasePntInfo mBasePntInfo = basePntInfoList.get(k);
                    mBasePntInfo.setF_sectid(sectid);
                    basePntInfoService.saveBasePntInfo(mBasePntInfo);
                }
                return basepnt_retult;
            }

        }, new Completion<Map<String, Object>>() {
            @Override
            public void onSuccess(Context context, Map<String, Object> result) {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_basePoint, 100));
                downloadLine(result);//开始下载水准线路信息
            }

            @Override
            public void onError(Context context, Exception e) {
                //showError(e);
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_basePoint, -1));
            }
        });
    }


    /**
     * 下载水准线路信息 lastResult包含标段、工点列表
     */

    private void downloadLine(Map<String,Object> lastResult){
        final Map<String,Object> readyResult=lastResult;
        Tasks.executeInBackground(context, new BackgroundWork<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInBackground() throws Exception {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_line, 50));//进度条
                Map<String, Object> line_retult = readyResult;//存放ID;
                SecNews section = (SecNews) line_retult.get("section");//获取标段和工点

                //清除BW数据
                bwInfoService.deleteAll();

                String sectid=section.getSectid();
                CJDownlineImpl mCJDownlineImpl=new CJDownlineImpl();
                List<CJDownline> mCJDownlineList= mCJDownlineImpl.getCJDownline(sectid, ValueConfig.FACE_START_DATE, ValueConfig.FACE_END_DATE, "" + readyResult.get("randomCode"));
                for (int i = 0; i <mCJDownlineList.size() ; i++) {
                    CJDownline mCJDownline=mCJDownlineList.get(i);
                    Line line=mCJDownline.getLineObj();
                    if (line!=null){
                        line.setF_sectid(sectid);
                        lineService.saveLine(line);
                    }

                    List<BwInfo> bwinfoList=mCJDownline.getBw();
                    for (int j = 0; j < bwinfoList.size(); j++) {
                        BwInfo bwinfo=bwinfoList.get(j);
                        if (bwinfo!=null){
                            bwinfo.setF_lc(line.getLc());
                            bwInfoService.saveBwInfo(bwinfo);
                        }
                    }
                }


                return line_retult;
            }

        }, new Completion<Map<String, Object>>() {
            @Override
            public void onSuccess(Context context, Map<String, Object> result) {
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_line, 100));
            }

            @Override
            public void onError(Context context, Exception e) {
                //showError(e);
                EventBus.getDefault().post(new DataSyncFragmentEvent(R.id.data_sync_dl_line, -1));
            }
        });
    }
}
