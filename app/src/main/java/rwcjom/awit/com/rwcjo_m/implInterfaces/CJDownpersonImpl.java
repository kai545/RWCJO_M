package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import rwcjom.awit.com.rwcjo_m.bean.CJDownperson;
import rwcjom.awit.com.rwcjo_m.bean.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownpersonInterface;


public class CJDownpersonImpl implements CJDownpersonInterface {
	private String result;
	@Override
	public void getCJDownperson(String sectid, String ptype, String randomcode) {
		// 命名空间
				String nameSpace = ValueConfig.NAMESPACE_STRING;

				// 调用的方法名称
				String methodName = "CJDownperson";
				// EndPoint
				String endPoint = ValueConfig.ENDPOINT_STRING;
				// SOAP Action
				String soapAction = ValueConfig.NAMESPACE_STRING+"CJDownperson";

				// 指定WebService的命名空间和调用的方法名
				SoapObject rpc = new SoapObject(nameSpace, methodName);
				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
				Log.i("randomcode",randomcode);
				rpc.addProperty("sectid", sectid);
				rpc.addProperty("ptype", ptype);
				rpc.addProperty("randomcode", randomcode);
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
				String[] resStr;
				try {
					// 获取返回的数据
					SoapObject object = (SoapObject) envelope.getResponse();
					pubUtil.downpersons.clear();
					// 获取返回的结果
					for(int i=0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						Log.i("result", result);
						//resStr=result.split("#");
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
						}else{
							CJDownperson downperson=new CJDownperson();
							downperson.setUserid(resStr[0]);
							downperson.setUsername(resStr[1]);
							downperson.setUsertel(resStr[2]);
							pubUtil.downpersons.add(downperson);
						}
						
					}
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

}
