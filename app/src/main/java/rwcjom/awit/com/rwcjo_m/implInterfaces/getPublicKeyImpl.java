package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import rwcjom.awit.com.rwcjo_m.bean.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.getPublicKeyInterface;


public class getPublicKeyImpl implements getPublicKeyInterface {
	private String result;
	@Override
	public String getPublicKey(String account, String mac) {
		// 命名空间
				String nameSpace = ValueConfig.NAMESPACE_STRING;

				// 调用的方法名称
				String methodName = "getPublicKey";
				// EndPoint
				String endPoint = ValueConfig.ENDPOINT_STRING;
				// SOAP Action
				String soapAction = ValueConfig.NAMESPACE_STRING+"getPublicKey";

				// 指定WebService的命名空间和调用的方法名
				SoapObject rpc = new SoapObject(nameSpace, methodName);

				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
				rpc.addProperty("account", account);
				rpc.addProperty("mac", mac);

				// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER10);

				envelope.bodyOut = rpc;
				// 设置是否调用的是dotNet开发的WebService
				envelope.dotNet = true;
				// 等价于envelope.bodyOut = rpc;
				envelope.setOutputSoapObject(rpc);

				HttpTransportSE transport = new HttpTransportSE(endPoint);
				try {
					// 调用WebService
					transport.call(soapAction, envelope);
				} catch (Exception e) {
//					handler.sendEmptyMessage(3);
				}

				// 获取返回的数据
				SoapObject object = (SoapObject) envelope.bodyIn;
				try {
					// 获取返回的结果
					result = object.getProperty(0).toString();
					if (result.equals("-1")) {
						pubUtil.exception.setExceptionMsg("account有误");
					} else if (result.equals("-2")) {
						pubUtil.exception.setExceptionMsg("mac有误");
					} else {
						pubUtil.getPubKey.setPublicKey(result);
						Log.i("存进去的结果：", pubUtil.getPubKey.getPublicKey());
					}
				} catch (Exception e) {
					pubUtil.getPubKey.setPublicKey("网络异常");
				}
				return result;
			}
	
}
