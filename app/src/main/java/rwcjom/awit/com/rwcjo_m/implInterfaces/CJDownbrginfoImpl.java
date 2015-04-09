package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import rwcjom.awit.com.rwcjo_m.bean.CJDownbrginfo;
import rwcjom.awit.com.rwcjo_m.bean.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownbrginfoInterface;

public class CJDownbrginfoImpl implements CJDownbrginfoInterface {
	private String result;
	@Override
	public void getCJDownbrginfo(String siteid, String faceid, String randomcode) {
		// 命名空间
				String nameSpace = ValueConfig.NAMESPACE_STRING;

				// 调用的方法名称
				String methodName = "CJDownbrginfo";
				// EndPoint
				String endPoint = ValueConfig.ENDPOINT_STRING;
				// SOAP Action
				String soapAction =ValueConfig.NAMESPACE_STRING+"CJDownbrginfo";

				// 指定WebService的命名空间和调用的方法名
				SoapObject rpc = new SoapObject(nameSpace, methodName);
				// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
				Log.i("randomcode",randomcode);
				rpc.addProperty("siteid", siteid);
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
					SoapObject object = (SoapObject) envelope.getResponse();
					pubUtil.downbrginfos.clear();
					Log.i("objectLength", object.getPropertyCount()+"");
					if(object.getPropertyCount()==3){
						if(object.getProperty(0).toString().equals("-1")){
							pubUtil.exception.setExceptionMsg("siteid有误");
						}else if(object.getProperty(1).toString().equals("-1")){
							pubUtil.exception.setExceptionMsg("faceid有误");
						}else{
							pubUtil.exception.setExceptionMsg("randomcode有误");
						}
						Log.i("exception", pubUtil.exception.getExceptionMsg());
					}else{
						CJDownbrginfo downbrginfo=new CJDownbrginfo();
						downbrginfo.setFaceid(object.getProperty(object.getPropertyCount()-6).toString());
						downbrginfo.setStructname(object.getProperty(object.getPropertyCount()-5).toString());
						downbrginfo.setPiernum(object.getProperty(object.getPropertyCount()-4).toString());
						downbrginfo.setBeamspan(object.getProperty(object.getPropertyCount()-3).toString());
						downbrginfo.setBeamtype(object.getProperty(object.getPropertyCount()-2).toString());
						downbrginfo.setRemark(object.getProperty(object.getPropertyCount()-1).toString());
						pubUtil.downbrginfos.add(downbrginfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

}
