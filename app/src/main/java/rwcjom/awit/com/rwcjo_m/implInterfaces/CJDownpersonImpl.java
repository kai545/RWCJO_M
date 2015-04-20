package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownperson;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownpersonInterface;


public class CJDownpersonImpl implements CJDownpersonInterface {
	private String TAG="CJDownpersonImpl";
	private String result;
	@Override
	public void getCJDownperson(String sectid, String ptype, String randomcode) {
				String[] resStr;
				try {
					Log.i(TAG,randomcode);
					String methodNameString="CJDownperson";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("sectid",sectid);
					paramsvalue.put("ptype",ptype);
					paramsvalue.put("randomcode",randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					Log.i(TAG,object.toString());
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					if(pubUtil.downpersons.size()>0){
						pubUtil.downpersons.clear();
					}
					// 获取返回的结果
					for(int i=0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						Log.i("result", result);
						resStr=result.split(ValueConfig.SPLIT_CHAR);
						if(resStr.length==4){
							if(resStr[0].equals("-1")){
								pubUtil.exception.setExceptionMsg("sectid有误");
							}else if(resStr[1].equals("-1")){
								pubUtil.exception.setExceptionMsg("ptype有误");
							}else if(resStr[2].equals("-1")){
								pubUtil.exception.setExceptionMsg("randomcode有误");
							}else{
								pubUtil.exception.setExceptionMsg("该工点下无相应的类别人员");
							}
							Log.i("exception", pubUtil.exception.getExceptionMsg());
						}else {
							CJDownperson downperson=new CJDownperson();
							downperson.setUserid(resStr[0]);
							downperson.setUsername(resStr[1]);
							downperson.setUsertel(resStr[2]);
							pubUtil.downpersons.add(downperson);
						}
					}
					 
				} catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					pubUtil.exception.setExceptionMsg("造型异常");
				}catch(ArrayIndexOutOfBoundsException e){
					e.printStackTrace();
					Log.i(TAG, "数组下标越界");
					pubUtil.exception.setExceptionMsg("下标越界");
				}catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					pubUtil.exception.setExceptionMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "网络异常");
					pubUtil.exception.setExceptionMsg("网络异常");
				}
	}

}
