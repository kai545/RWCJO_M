package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownsectsite;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownsectsiteInterface;


public class CJDownsectsiteImpl implements CJDownsectsiteInterface {
	private final String TAG="CJDownsectsiteImpl";
	private String result="";
	private String[] secStr;
	@Override
	public void getCJDownsectsite(String sectid, String sitetype,
			String randomcode) {
//				// 命名空间
//				String nameSpace = ValueConfig.NAMESPACE_STRING;
//
//				// 调用的方法名称
//				String methodName = "CJDownsectsite";
//				// EndPoint
//				String endPoint = ValueConfig.ENDPOINT_STRING;
//				// SOAP Action
//				String soapAction = ValueConfig.NAMESPACE_STRING+"CJDownsectsite";
//
//				// 指定WebService的命名空间和调用的方法名
//				SoapObject rpc = new SoapObject(nameSpace, methodName);
//				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
//				Log.i("randomcode",randomcode);
//				rpc.addProperty("sectid", sectid);
//				rpc.addProperty("sitetype", sitetype);
//				rpc.addProperty("randomcode", randomcode);
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
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				// 获取返回的数据
//				SoapObject object = (SoapObject)  envelope.bodyIn;
//				Log.i("object", object+"");
				try {
					String methodNameString="CJDownsectsite";
					Map<String,String> paramsvalue=new HashMap<String,String>();
					paramsvalue.put("sectid",sectid);
					paramsvalue.put("sitetype",sitetype);
					paramsvalue.put("randomcode",randomcode);
					SoapObject object= CommonTools.getObject(methodNameString, paramsvalue);
					if(object !=null){
						Log.i(TAG, "Object is not null");
					}
					if(pubUtil.downsectsites.size()>0){
						pubUtil.downsectsites.clear();
					}
					// 获取返回的结果
					for(int i =0;i<object.getPropertyCount();i++){
						CJDownsectsite downsectsite=new CJDownsectsite();
						result = object.getProperty(i).toString();
						Log.i("result", result);
						secStr=result.split(ValueConfig.SPLIT_CHAR);
						if(secStr.length==4){
							if(secStr[0].equals("-1")){
								pubUtil.exception.setExceptionMsg("sectid有误");
							}else if(secStr[1].equals("-1")){
								pubUtil.exception.setExceptionMsg("sitetype有误");
							}else if(secStr[2].equals("-1")){
								pubUtil.exception.setExceptionMsg("randomcode有误");
							}else{
								pubUtil.exception.setExceptionMsg("该标段下无相应的工点");
							}
							Log.i("exception", pubUtil.exception.getExceptionMsg());
						}else if(secStr.length==3){
							downsectsite.setSectionId(secStr[0]);
							downsectsite.setSectionCode(secStr[1]);
							downsectsite.setSectionName(secStr[2]);
							pubUtil.downsectsites.add(downsectsite);
						}else{
							downsectsite.setSectionId(secStr[0]);
							downsectsite.setSectionCode(secStr[1]);
							downsectsite.setSectionName(secStr[2]);
							downsectsite.setEndSite(secStr[3]);
							downsectsite.setStartSite(secStr[4]);
							pubUtil.downsectsites.add(downsectsite);
						}
					}
					
				}catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					pubUtil.exception.setExceptionMsg("空指针异常");
				} catch (Exception e) {
					e.printStackTrace();
					pubUtil.exception.setExceptionMsg("网络异常");
				}
	}
	
}
