package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
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
//			    // 命名空间
//				 String nameSpace = ValueConfig.NAMESPACE_STRING;
//
//				// 调用的方法名称
//				String methodName = "getPublicKey";
//				// EndPoint
//				String endPoint = ValueConfig.ENDPOINT_STRING;
//				// SOAP Action
//				String soapAction = ValueConfig.NAMESPACE_STRING+"getPublicKey";
//
//				// 指定WebService的命名空间和调用的方法名
//				SoapObject rpc = new SoapObject(nameSpace, methodName);
//
//				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
//				rpc.addProperty("account", account);
//				rpc.addProperty("mac", mac);
//
//				// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
//				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//						SoapEnvelope.VER10);
//
//				envelope.bodyOut = rpc;
//				// 设置是否调用的是dotNet开发的WebService
//				envelope.dotNet = true;
//				// 等价于envelope.bodyOut = rpc;
//				envelope.setOutputSoapObject(rpc);
//
//				HttpTransportSE transport = new HttpTransportSE(endPoint);
//				try {
//					// 调用WebService
//					transport.call(soapAction, envelope);
//					if (envelope.getResponse()!=null)
//					{
//						Log.i(TAG, "Object is not null");
//					}
//				} catch (Exception e) {
////					handler.sendEmptyMessage(3);
//				}
//
//
//
//				// 获取返回的数据
//				SoapObject object = (SoapObject) envelope.bodyIn;
//				if (object==null){
//					Log.i(TAG, "Object is null");
//				}

				try {
					Log.i(TAG,"登录实现");
					String methodNameString="getPublicKey";
					Map<String,String> paramsvalue=new HashMap<String,String>();
					paramsvalue.put("account",account);
					paramsvalue.put("mac",mac);
					SoapObject object=CommonTools.getObject(methodNameString,paramsvalue);
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
