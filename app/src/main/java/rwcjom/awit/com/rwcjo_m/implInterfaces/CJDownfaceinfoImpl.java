package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import rwcjom.awit.com.rwcjo_m.bean.CJDownfaceinfo;
import rwcjom.awit.com.rwcjo_m.bean.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownfaceinfoInterface;


public class CJDownfaceinfoImpl implements CJDownfaceinfoInterface {
	private String result;
	@Override
	public void getCJDownfaceinfo(String siteid, String faceid,
			String randomcode) {
		// 命名空间
				String nameSpace = ValueConfig.NAMESPACE_STRING;

				// 调用的方法名称
				String methodName = "CJDownfaceinfo";
				// EndPoint
				String endPoint = ValueConfig.ENDPOINT_STRING;
				// SOAP Action
				String soapAction = ValueConfig.NAMESPACE_STRING+"CJDownfaceinfo";

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
					pubUtil.downfaceinfos.clear();
					// 获取返回的结果
					Log.i("ObjectLength", object.getPropertyCount()+"");
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
						CJDownfaceinfo downfaceinfo=new CJDownfaceinfo();
						downfaceinfo.setFaceid(object.getProperty(object.getPropertyCount()-14).toString());
						downfaceinfo.setJointflag(object.getProperty(object.getPropertyCount()-13).toString());
						downfaceinfo.setStructtype(object.getProperty(object.getPropertyCount()-12).toString());
						downfaceinfo.setStructname(object.getProperty(object.getPropertyCount()-11).toString());
						downfaceinfo.setStructbase(object.getProperty(object.getPropertyCount()-10).toString());
						downfaceinfo.setDesignatt(object.getProperty(object.getPropertyCount()-9).toString());
						downfaceinfo.setPiernum(object.getProperty(object.getPropertyCount()-8).toString());
						downfaceinfo.setDkname(object.getProperty(object.getPropertyCount()-7).toString());
						downfaceinfo.setDkilo(object.getProperty(object.getPropertyCount()-6).toString());
						downfaceinfo.setDchain(object.getProperty(object.getPropertyCount()-5).toString());
						downfaceinfo.setRkname(object.getProperty(object.getPropertyCount()-4).toString());
						downfaceinfo.setRkilo(object.getProperty(object.getPropertyCount()-3).toString());
						downfaceinfo.setRchain(object.getProperty(object.getPropertyCount()-2).toString());
						downfaceinfo.setRemark(object.getProperty(object.getPropertyCount()-1).toString());
						pubUtil.downfaceinfos.add(downfaceinfo);
					}	
				} catch (Exception e) {
					e.printStackTrace();
			}
	}

}
