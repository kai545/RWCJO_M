package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownbrginfo;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownbrginfoInterface;

public class CJDownbrginfoImpl implements CJDownbrginfoInterface {
	private String TAG="CJDownbrginfoImpl";
	private String result;
	@Override
	public void getCJDownbrginfo(String siteid, String faceid, String randomcode) {
				try {
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
					if(pubUtil.downbrginfos.size()>0){
						pubUtil.downbrginfos.clear();
					}
					Log.i("CJDownbrginfoLength", object.getPropertyCount() + "");
					if(object.getPropertyCount()==3){
						if(object.getProperty(0).toString().equals("-1")){
							pubUtil.exception.setExceptionMsg("siteid有误");
						}else if(object.getProperty(1).toString().equals("-1")){
							pubUtil.exception.setExceptionMsg("faceid有误");
						}else{
							pubUtil.exception.setExceptionMsg("randomcode有误");
						}
						Log.i("exception", pubUtil.exception.getExceptionMsg());
					}else if(object.getPropertyCount()==6){
						CJDownbrginfo downbrginfo=new CJDownbrginfo();
						downbrginfo.setFaceid(object.getProperty(object.getPropertyCount()-6).toString());
						downbrginfo.setStructname(object.getProperty(object.getPropertyCount()-5).toString());
						downbrginfo.setPiernum(object.getProperty(object.getPropertyCount()-4).toString());
						downbrginfo.setBeamspan(object.getProperty(object.getPropertyCount()-3).toString());
						downbrginfo.setBeamtype(object.getProperty(object.getPropertyCount()-2).toString());
						downbrginfo.setRemark(object.getProperty(object.getPropertyCount()-1).toString());
						pubUtil.downbrginfos.add(downbrginfo);
					}
				}catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					pubUtil.exception.setExceptionMsg("造型异常");
				}catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					pubUtil.exception.setExceptionMsg("空指针异常");
				} catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "网络异常");
					pubUtil.exception.setExceptionMsg("网络异常");
				}
	}

}
