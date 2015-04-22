package rwcjom.awit.com.rwcjo_m;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rwcjom.awit.com.rwcjo_m.bean.CJDownbrginfo;
import rwcjom.awit.com.rwcjo_m.bean.CJDownface;
import rwcjom.awit.com.rwcjo_m.bean.CJDownfaceinfo;

import rwcjom.awit.com.rwcjo_m.bean.CJDownsectsite;
import rwcjom.awit.com.rwcjo_m.dao.BrgInfo;
import rwcjom.awit.com.rwcjo_m.dao.FaceInfo;
import rwcjom.awit.com.rwcjo_m.dao.FaceNews;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.dao.PntInfo;
import rwcjom.awit.com.rwcjo_m.dao.SecNews;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownbrginfoImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownfaceImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownfaceinfoImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownpntinfoImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.CJDownsectsiteImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.getPublicKeyImpl;
import rwcjom.awit.com.rwcjo_m.implInterfaces.verifyAppUserImpl;
import rwcjom.awit.com.rwcjo_m.bean.CJDownpntinfo;
/**
 * Created by Administrator on 15-4-22.
 */
public class TestInterfaceActivity extends Activity{
    private String publicKey;
    private String randomcode;
    private List<CJDownsectsite> downsectsiteList;
    private CJDownsectsite downsectsiteObj;
    private List<SiteNews> sitelist;
    private SiteNews siteObj;
    private SecNews sectObj;
    private List<CJDownface> downfaceList;
    private CJDownface downfaceObj;
    private List<FaceNews> facelist;
    private List<CJDownfaceinfo> downfaceinfoList;
    private CJDownfaceinfo downfaceinfoObj;
    private FaceInfo faceinfoObj;
    private List<CJDownbrginfo> downbrginfoList;
    private CJDownbrginfo downbrginfoObj;
    private BrgInfo brginfoObj;
    private List<CJDownpntinfo> downpntinfoList;
    private CJDownpntinfo downpntinfoObj;
    private List<PntInfo> pntInfoList;
    private PntInfo pntInfoObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求获取公钥接口
                getPublicKeyImpl getPublicKey=new getPublicKeyImpl();
                publicKey=getPublicKey.getPublicKey("liuyang2","password");
                Log.i("getPublicKeyImpl", pubUtil.getPubKey.getPublicKey());

                //登录验证接口
                verifyAppUserImpl verifyAppUser=new verifyAppUserImpl();
                randomcode=verifyAppUser.getVerifyAppUser("liuyang2","password","password",publicKey);
                Log.i("verifyAppUserImpl",randomcode);

                //下载标段和工点信息接口
                /*CJDownsectsiteImpl downsectsiteImpl=new CJDownsectsiteImpl();
                downsectsiteList=new ArrayList<CJDownsectsite>();
                downsectsiteList=downsectsiteImpl.getCJDownsectsite("0","2",randomcode);
                Log.i("CJDownsectsiteImpl",downsectsiteList.size()+"");
                for(int i=0;i<downsectsiteList.size();i++){
                    downsectsiteObj=new CJDownsectsite();
                    downsectsiteObj=downsectsiteList.get(i);
                    sitelist=new ArrayList<SiteNews>();
                    sitelist=downsectsiteObj.getSitelist();
                    if(sitelist==null){
                        sectObj=new SecNews();
                        sectObj=downsectsiteObj.getSecObj();
                        Log.i("sectid",sectObj.getSectid());
                        Log.i("sectcode",sectObj.getSectcode());
                        Log.i("sectname",sectObj.getSectname());
                    }else{
                        for(int j=0;j<sitelist.size();j++){
                            siteObj=new SiteNews();
                            siteObj=sitelist.get(j);
                            Log.i("siteid",siteObj.getSiteid());
                            Log.i("sitecode",siteObj.getSitecode());
                            Log.i("sitename",siteObj.getSitename());
                            Log.i("startsite",siteObj.getStartsite());
                            Log.i("endsite",siteObj.getEndsite());
                        }
                    }*/

                    //下载断面(梁体)基础信息接口
                    /*CJDownfaceImpl downfaceImpl=new CJDownfaceImpl();
                    downfaceList=new ArrayList<CJDownface>();
                    downfaceList=downfaceImpl.getCJDownface("8456","2015-01-01","2015-04-22",randomcode);
                    Log.i("downfaceListLength:",downfaceList.size()+"");
                    for( int k=0 ; k<downfaceList.size();k++){
                        downfaceObj=new CJDownface();
                        downfaceObj=downfaceList.get(k);
                        facelist=new ArrayList<FaceNews>();
                        facelist=downfaceObj.getFacelist();
                        for(int x=0;x<facelist.size();x++){
                            Log.i("faceid", facelist.get(x).getFaceid());
                            Log.i("facecode",facelist.get(x).getFacecode());
                            Log.i("facename",facelist.get(x).getFacename());
                        }

                    }*/


                    //下载沉降观测断面详细信息接口
                    /*CJDownfaceinfoImpl downfaceinfoImpl=new CJDownfaceinfoImpl();
                    downfaceinfoList=new ArrayList<CJDownfaceinfo>();
                    downfaceinfoList=downfaceinfoImpl.getCJDownfaceinfo("5055","54",randomcode);
                    downfaceinfoObj=new CJDownfaceinfo();
                    downfaceinfoObj=downfaceinfoList.get(0);
                    faceinfoObj=new FaceInfo();
                    faceinfoObj=downfaceinfoObj.getFaceinfoObj();
                        Log.i("faceid",faceinfoObj.getFaceid());
                        Log.i("jointflag",faceinfoObj.getJointflag());
                        Log.i("structtype",faceinfoObj.getStructtype());
                        Log.i("structname",faceinfoObj.getStructname());
                        Log.i("structbase",faceinfoObj.getStructbase());
                        Log.i("designatt",faceinfoObj.getDesignatt());
                        Log.i("piernum",faceinfoObj.getPiernum());
                        Log.i("dkname",faceinfoObj.getDkname());
                        Log.i("dkilo",faceinfoObj.getDkilo());
                        Log.i("dchain",faceinfoObj.getDchain());
                        Log.i("rkname",faceinfoObj.getRkname());
                        Log.i("rkilo",faceinfoObj.getRkilo());
                        Log.i("rchain",faceinfoObj.getRchain());
                        Log.i("remark",faceinfoObj.getRemark());*/

                //下载梁体徐变详细信息接口
                /*CJDownbrginfoImpl downbrginfoImpl=new CJDownbrginfoImpl();
                downbrginfoList=new ArrayList<CJDownbrginfo>();
                downbrginfoList=downbrginfoImpl.getCJDownbrginfo("8456","135",randomcode);
                downbrginfoObj=new CJDownbrginfo();
                downbrginfoObj=downbrginfoList.get(0);
                brginfoObj=new BrgInfo();
                brginfoObj=downbrginfoObj.getBrginfoObj();
                Log.i("faceid",brginfoObj.getFaceid());
                Log.i("structname",brginfoObj.getStructname());
                Log.i("piernum",brginfoObj.getPiernum());
                Log.i("beamspan",brginfoObj.getBeamspan());
                Log.i("beamtype",brginfoObj.getBeamtype());
                Log.i("remark",brginfoObj.getRemark());*/

                //下载沉降(徐变)观测点信息接口
                /*CJDownpntinfoImpl downpntinfoImpl=new CJDownpntinfoImpl();
                downpntinfoList=new ArrayList<CJDownpntinfo>();
                downpntinfoList=downpntinfoImpl.getCJDownputinfo("54","1",randomcode);
                downpntinfoObj=new CJDownpntinfo();
                for(int i=0;i<downfaceinfoList.size();i++){
                    downpntinfoObj=downpntinfoList.get(i);
                    pntInfoList=new ArrayList<PntInfo>();
                    pntInfoList=downpntinfoObj.getPntInfoList();
                    pntInfoObj=new PntInfo();
                    for(int j=0;j<pntInfoList.size();j++){
                        pntInfoObj=pntInfoList.get(j);
                        Log.i("pointid",pntInfoObj.getPointid());
                        Log.i("Pointnum",pntInfoObj.getPointnum());
                        Log.i("Designvalue",pntInfoObj.getDesignvalue());
                        Log.i("Designremark",pntInfoObj.getDesignremark());
                        Log.i("Inbuiltdate",pntInfoObj.getInbuiltdate());
                        Log.i("Seatcode",pntInfoObj.getSeatcode());
                        Log.i("Remark",pntInfoObj.getRemark());
                        Log.i("Pointcode",pntInfoObj.getPointcode());
                        Log.i("Name",pntInfoObj.getName());
                    }
                }*/

                }
        }).start();
    }
}
