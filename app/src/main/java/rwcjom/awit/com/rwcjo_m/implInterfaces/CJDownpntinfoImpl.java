package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import rwcjom.awit.com.rwcjo_m.bean.CJDownpntinfo;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownpntinfoInterface;


public class CJDownpntinfoImpl implements CJDownpntinfoInterface {
	private String result;
	@Override
	public void getCJDownputinfo(String faceid, String objstate,
			String randomcode) {
		// 命名空间
				String nameSpace = ValueConfig.NAMESPACE_STRING;

				// 调用的方法名称
				String methodName = "CJDownpntinfo";
				// EndPoint
				String endPoint = ValueConfig.ENDPOINT_STRING;
				// SOAP Action
				String soapAction =ValueConfig.NAMESPACE_STRING+"CJDownpntinfo";

				// 指定WebService的命名空间和调用的方法名
				SoapObject rpc = new SoapObject(nameSpace, methodName);
				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
				Log.i("randomcode",randomcode);
				rpc.addProperty("siteid", objstate);
				rpc.addProperty("faceid", faceid);
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
				try {
					// 获取返回的数据
					SoapObject object = (SoapObject) envelope.bodyIn;
					pubUtil.downpntinfos.clear();
					JSONArray jsonpnt;
					JSONObject jsonObj;
					// 获取返回的结果
					for(int i=0;i<object.getPropertyCount();i++){
						 result = object.getProperty(i).toString();
						 jsonpnt=new JSONArray(result);
						 if(jsonpnt.length()>1){
							 for(int j=0;j<jsonpnt.length();j++){
								 String data=jsonpnt.get(j).toString();
								 jsonObj=new JSONObject(data);
								 CJDownpntinfo downpntinfo=new CJDownpntinfo();
								 downpntinfo.setPointid(jsonObj.getString("pointid"));
								 downpntinfo.setPointnum(jsonObj.getString("pointnum"));
								 downpntinfo.setDesignvalue(jsonObj.getString("designvalue"));
								 downpntinfo.setDesignremark(jsonObj.getString("designremark"));
								 downpntinfo.setInbuiltdate(jsonObj.getString("inbuiltdate"));
								 downpntinfo.setSeatcode(jsonObj.getString("seatcode"));
								 downpntinfo.setRemark(jsonObj.getString("remark"));
								 downpntinfo.setPointcode(jsonObj.getString("pointcode"));
								 downpntinfo.setName(jsonObj.getString("name"));
								 pubUtil.downpntinfos.add(downpntinfo);
							 }
						 }else{
							 String exStr=jsonpnt.get(0).toString();
							 jsonObj=new JSONObject(exStr);
							 Log.i("res", result);
							 if(jsonObj.getString("faceid").equals("-1")){
								 pubUtil.exception.setExceptionMsg("faceid有误");
							 }else if(jsonObj.getString("objstate").equals("-1")){
								 pubUtil.exception.setExceptionMsg("objstate有误");
							 }else if(jsonObj.getString("randomcode").equals("-1")){
								 pubUtil.exception.setExceptionMsg("enddate有误");
							 }else{
								 pubUtil.exception.setExceptionMsg("无相应的测点信息");
							 }
							 Log.i("exception", pubUtil.exception.getExceptionMsg());
						 }
						
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

}
