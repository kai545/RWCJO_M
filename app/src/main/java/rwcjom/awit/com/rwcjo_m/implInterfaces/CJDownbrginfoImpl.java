package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownbrginfo;
import rwcjom.awit.com.rwcjo_m.dao.BrgInfo;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownbrginfoInterface;

public class CJDownbrginfoImpl implements CJDownbrginfoInterface {
	private String TAG="CJDownbrginfoImpl";
	private List<CJDownbrginfo> downbrginfoList;
	private CJDownbrginfo downbrginfoObj;
	private BrgInfo brginfoObj;
	@Override
	public List<CJDownbrginfo> getCJDownbrginfo(String siteid, String faceid, String randomcode) {
				try {
					downbrginfoList=new ArrayList<CJDownbrginfo>();
					Log.i(TAG,randomcode);
					String methodNameString="CJDownbrginfo";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("siteid",siteid);
					paramsvalue.put("faceid",faceid);
					paramsvalue.put("randomcode",randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					Log.i("CJDownbrginfoLength", object.getPropertyCount() + "");
					if(object.getPropertyCount()==3){
						downbrginfoObj=new CJDownbrginfo();
						downbrginfoObj.setFlag(-1);
						if(object.getProperty(0).toString().equals("-1")){
							downbrginfoObj.setMsg("siteid有误");
						}else if(object.getProperty(1).toString().equals("-1")){
							downbrginfoObj.setMsg("faceid有误");
						}else{
							downbrginfoObj.setMsg("randomcode有误");
						}
						Log.i("exception", downbrginfoObj.getMsg());
					}else if(object.getPropertyCount()==6){
						downbrginfoObj=new CJDownbrginfo();
						downbrginfoObj.setFlag(0);
						brginfoObj=new BrgInfo();
						brginfoObj.setFaceid(object.getProperty(object.getPropertyCount() - 6).toString());
						brginfoObj.setStructname(object.getProperty(object.getPropertyCount() - 5).toString());
						brginfoObj.setPiernum(object.getProperty(object.getPropertyCount() - 4).toString());
						brginfoObj.setBeamspan(object.getProperty(object.getPropertyCount() - 3).toString());
						brginfoObj.setBeamtype(object.getProperty(object.getPropertyCount() - 2).toString());
						brginfoObj.setRemark(object.getProperty(object.getPropertyCount() - 1).toString());
						downbrginfoObj.setBrginfoObj(brginfoObj);
						downbrginfoList.add(downbrginfoObj);
					}
				}catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					downbrginfoObj.setFlag(-2);
					downbrginfoObj.setMsg("造型异常");
				}catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					downbrginfoObj.setFlag(-2);
					downbrginfoObj.setMsg("空指针异常");
				} catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "网络异常");
					downbrginfoObj.setFlag(-2);
					downbrginfoObj.setMsg("网络异常");
				}
		return downbrginfoList;
	}

}
