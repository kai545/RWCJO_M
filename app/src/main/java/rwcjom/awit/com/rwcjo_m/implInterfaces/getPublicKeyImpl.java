package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.getPublicKeyInterface;


public class getPublicKeyImpl implements getPublicKeyInterface {
	private final String TAG="getPublicKeyImpl";
	private static String result="-100";
	@Override
	public String getPublicKey(String account, String mac) {
				try {
					Log.i(TAG,"登录实现");
					String methodNameString="getPublicKey";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("account",account);
					paramsvalue.put("mac",mac);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.bodyIn;
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					// 获取返回的结果
					result = object.getProperty(0).toString();
					if(result !=null){
						Log.i(TAG, "result不为空！");
						Log.i(TAG,result+"success");
					}
					if (result.equals("-1")) {
						pubUtil.exception.setExceptionMsg("account有误");
						Log.i(TAG, result + pubUtil.exception.getExceptionMsg());
					} else if (result.equals("-2")) {
						pubUtil.exception.setExceptionMsg("mac有误");
					} else {
						pubUtil.getPubKey.setPublicKey(result);
						Log.i("存进去的结果：", pubUtil.getPubKey.getPublicKey());
					}
				}catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					pubUtil.exception.setExceptionMsg("造型异常");
				} catch(NullPointerException e){
					Log.i(TAG,"空指针异常");
					result="-300";
					e.printStackTrace();
					pubUtil.exception.setExceptionMsg("空指针异常");
				}catch (Exception e) {
					Log.i(TAG, "出现异常：" + e.getMessage());
					e.printStackTrace();
					pubUtil.exception.setExceptionMsg("网络异常");
				}
				return result;
			}
	
}
