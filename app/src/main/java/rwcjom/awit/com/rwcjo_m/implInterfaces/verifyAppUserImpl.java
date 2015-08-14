package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.LinkedHashMap;
import java.util.Map;

import ICT.utils.RSACoder;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.verifyAppUserInterface;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;


public class verifyAppUserImpl implements verifyAppUserInterface {
	private static String TAG="verifyAppUserImpl";
	private static String result="";
	private static String endPwd="";
	private static String endKey="";
	private static String preKey="AWITRWCJ";
	@Override
	public  String  getVerifyAppUser(String account, String pwd, String mac,
			String deskey) {
				try {
					String methodNameString="verifyAppUser";
					endPwd=RSACoder.encnryptDes(pwd,preKey);
					endKey=RSACoder.encnryptRSA(preKey, deskey);
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("account",account);
					paramsvalue.put("pwd",endPwd);
					paramsvalue.put("mac",mac);
					paramsvalue.put("deskey",endKey);
					Log.i(TAG,endPwd+":::"+endKey);
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
