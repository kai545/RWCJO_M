package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import ICT.utils.RSACoder;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.verifyAppUserInterface;


public class verifyAppUserImpl implements verifyAppUserInterface {
	private static String TAG="verifyAppUserImpl";
	private static String result="";
	private static String endPwd="";
	private static String endKey="";
	private static String preKey="AWITRWCJ";
	@Override
	public  String  getVerifyAppUser(String account, String pwd, String mac,
			String deskey) {
//				// 命名空间
//				String nameSpace = ValueConfig.NAMESPACE_STRING;
//				// 调用的方法名称
//				String methodName = "verifyAppUser";
//				// EndPoint
//				String endPoint = ValueConfig.ENDPOINT_STRING;
//				// SOAP Action
//				String soapAction = ValueConfig.NAMESPACE_STRING+"verifyAppUser";
//
//				// 指定WebService的命名空间和调用的方法名
//				SoapObject rpc = new SoapObject(nameSpace, methodName);
//				/**
//				 *密码和公钥加密
//				 */
//				Log.i("deskey", deskey);
//				endPwd=RSACoder.encnryptDes(pwd,preKey);
//				Log.i("endPwd", endPwd);
//				endKey=RSACoder.encnryptRSA(preKey,deskey);
//				Log.i("endKey", endKey);
//				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
//				rpc.addProperty("account", account);
//				rpc.addProperty("pwd", endPwd);
//				rpc.addProperty("mac", mac);
//				rpc.addProperty("deskey", endKey);
//				// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
//				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//						SoapEnvelope.VER10);
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
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				// 获取返回的数据
//				SoapObject object = (SoapObject) envelope.bodyIn;
				try {
					String methodNameString="verifyAppUser";
					endPwd=RSACoder.encnryptDes(pwd,preKey);
					endKey=RSACoder.encnryptRSA(preKey, deskey);
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("account",account);
					paramsvalue.put("pwd",endPwd);
					paramsvalue.put("mac",mac);
					paramsvalue.put("deskey",endKey);
					//SoapObject object=CommonTools.getObject();
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.bodyIn;
					if(object ==null){
						Log.i(TAG,"object is null!");
					}
					// 获取返回的结果
					 result = object.getProperty(0).toString();
					 if(result != null){
						Log.i("验证用户", result);
					 }
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
				}catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					pubUtil.exception.setExceptionMsg("造型异常");
				} catch(NullPointerException e){
					Log.i(TAG,"空指针异常");
					e.printStackTrace();
					pubUtil.exception.setExceptionMsg("空指针异常");
				} catch (Exception e) {
					e.printStackTrace();
					pubUtil.exception.setExceptionMsg("网络异常");
				}
				return result;
	}
	
}
