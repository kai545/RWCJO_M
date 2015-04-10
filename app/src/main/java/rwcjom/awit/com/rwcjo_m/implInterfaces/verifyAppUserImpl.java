package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import ICT.utils.RSACoder;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.verifyAppUserInterface;


public class verifyAppUserImpl implements verifyAppUserInterface {
	private String result="";
	private String endPwd="";
	private String endKey="";
	private String preKey="AWITRWCJ";
	@Override
	public  String  getVerifyAppUser(String account, String pwd, String mac,
			String deskey) {
				// 命名空间
				String nameSpace = ValueConfig.NAMESPACE_STRING;
				// 调用的方法名称
				String methodName = "verifyAppUser";
				// EndPoint
				String endPoint = ValueConfig.ENDPOINT_STRING;
				// SOAP Action
				String soapAction = ValueConfig.NAMESPACE_STRING+"verifyAppUser";

				// 指定WebService的命名空间和调用的方法名
				SoapObject rpc = new SoapObject(nameSpace, methodName);
				/**
				 *密码和公钥加密
				 */
				Log.i("deskey", deskey);
				endPwd=RSACoder.encnryptDes(pwd,preKey);
				Log.i("endPwd", endPwd);
				endKey=RSACoder.encnryptRSA(preKey,deskey);
				Log.i("endKey", endKey);
				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
				rpc.addProperty("account", account);
				rpc.addProperty("pwd", endPwd);
				rpc.addProperty("mac", mac);
				rpc.addProperty("deskey", endKey);
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
					e.printStackTrace();
				}
				// 获取返回的数据
				SoapObject object = (SoapObject) envelope.bodyIn;
				try {
					// 获取返回的结果
					 result = object.getProperty(0).toString();
					 Log.i("验证用户", result);
					 if(result.length() == 13){
						 pubUtil.verifyApp.setRandomCode(result);
					 }else if(result.equals("-1")){
						 pubUtil.exception.setExceptionMsg("account错误");
					 }else if(result.equals("-2")){
						 pubUtil.exception.setExceptionMsg("pwd有误");
					 }else if(result.equals("-3")){
						 pubUtil.exception.setExceptionMsg("mac有误");
					 }else if(result.equals("-4")){
						 pubUtil.exception.setExceptionMsg("deskey有误");
					 }else{
						 pubUtil.exception.setExceptionMsg("账号过期"); 
					 }
				} catch (Exception e) {
					pubUtil.exception.setExceptionMsg("网络异常");
				}
				return result;
	}
	
}
