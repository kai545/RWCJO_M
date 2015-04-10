package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import rwcjom.awit.com.rwcjo_m.bean.CJDownface;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownfaceInterface;


public class CJDownfaceImpl implements CJDownfaceInterface {
	private String result;
	@Override
	public void getCJDownface(String siteid, String startdate, String enddate,
			String randomcode) {
		// 命名空间
				String nameSpace = ValueConfig.NAMESPACE_STRING;

				// 调用的方法名称
				String methodName = "CJDownface";
				// EndPoint
				String endPoint = ValueConfig.ENDPOINT_STRING;
				// SOAP Action
				String soapAction = ValueConfig.NAMESPACE_STRING+"CJDownface";

				// 指定WebService的命名空间和调用的方法名
				SoapObject rpc = new SoapObject(nameSpace, methodName);
				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
				Log.i("randomcode",randomcode);
				rpc.addProperty("siteid", siteid);
				rpc.addProperty("startdate", startdate);
				rpc.addProperty("enddate", enddate);
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

				// 获取返回的数据
				SoapObject object = null;
				try {
					object = (SoapObject) envelope.getResponse();
					Log.i("object", object+"");
					pubUtil.downfaces.clear();
				} catch (SoapFault e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] faceStr;
				CJDownface downface=new CJDownface();
				try {
					// 获取返回的结果
					for(int i =0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						Log.i("result", result);
						//faceStr=result.split("#");
						faceStr=result.split(ValueConfig.SPLIT_CHAR);
						Log.i("faceStr", faceStr.length+"");
						if(faceStr.length==5){
							if(faceStr[0].equals("-1")){
								pubUtil.exception.setExceptionMsg("siteid有误");
							}else if(faceStr[1].equals("-1")){
								pubUtil.exception.setExceptionMsg("startdate有误");
							}else if(faceStr[2].equals("-1")){
								pubUtil.exception.setExceptionMsg("enddate有误");
							}else if(faceStr[3].equals("-1")){
								pubUtil.exception.setExceptionMsg("randomcode有误");
							}else {
								pubUtil.exception.setExceptionMsg("该标段下无相应的工点");
							}
							Log.i("exception", pubUtil.exception.getExceptionMsg());
						}else{
								downface.setFaceId(faceStr[0]);
								downface.setFaceCode(faceStr[1]);
								downface.setFaceName(faceStr[2]);
								pubUtil.downfaces.add(downface);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
}
