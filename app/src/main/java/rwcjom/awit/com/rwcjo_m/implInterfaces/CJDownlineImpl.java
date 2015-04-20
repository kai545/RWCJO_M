package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownline;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownlineInterface;
import rwcjom.awit.com.rwcjo_m.bean.Bw;


public class CJDownlineImpl implements CJDownlineInterface {
	private String TAG="CJDownlineImpl";
	private String result;
		@Override
		public void getCJDownline(String sectid, String startdate, String enddate,
				String randomcode) {
//			// 命名空间
//			String nameSpace = "http://www.xfiretest.com/";
//
//			// 调用的方法名称
//			String methodName = "CJDownline";
//			// EndPoint
//			String endPoint = ValueConfig.ENDPOINT_STRING;
//			// SOAP Action
//			String soapAction = "http://www.xfiretest.com/CJDownline";
//
//			// 指定WebService的命名空间和调用的方法名
//			SoapObject rpc = new SoapObject(nameSpace, methodName);
//			// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
//			Log.i("randomcode",randomcode);
//			rpc.addProperty("sectid", sectid);
//			rpc.addProperty("startdate", startdate);
//			rpc.addProperty("enddate", enddate);
//			rpc.addProperty("randomcode", randomcode);
//			// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
//			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//					SoapEnvelope.VER10);
//
//			envelope.bodyOut = rpc;
//			// 设置是否调用的是dotNet开发的WebService
//			envelope.dotNet = true;
//			// 等价于envelope.bodyOut = rpc;
//			envelope.setOutputSoapObject(rpc);
//
//			HttpTransportSE transport = new HttpTransportSE(endPoint);
//			try {
//				// 调用WebService
//				transport.call(soapAction, envelope);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			SoapObject object = null;
			JSONArray jsonLine;
			JSONObject jsonObj;

			try {
				Log.i(TAG, randomcode);
				String methodNameString="CJDownline";
				Map<String,String> paramsvalue=new LinkedHashMap<>();
				paramsvalue.put("sectid",sectid);
				paramsvalue.put("startdate",startdate);
				paramsvalue.put("enddate",enddate);
				paramsvalue.put("randomcode",randomcode);
				//object=CommonTools.getObject(methodNameString,paramsvalue);
				SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
				object=(SoapObject)envelope.bodyIn;
				Log.i(TAG,object.toString());
				if(object ==null){
					Log.i(TAG, "Object is null");
				}
				if(pubUtil.downlines.size()>0){
					pubUtil.downlines.clear();
				}
				for(int i =0;i<object.getPropertyCount();i++){
					result = object.getProperty(i).toString();
					jsonLine=new JSONArray(result);
					Log.i("res", jsonLine.get(0).toString());
					if(jsonLine.length()>1){
						Log.i("len", jsonLine.length()+"");
						for(int j=0;j<jsonLine.length();j++){
							CJDownline downline=new CJDownline();
							String data=jsonLine.get(j).toString();
							Log.i("data", data);
							jsonObj=new JSONObject(data);
							downline.setLc(jsonObj.getString("lc"));
							downline.setLn(jsonObj.getString("ln"));
							Log.i("LC",downline.getLc());
							Log.i("LN",downline.getLn());
							List<Bw> bwlist=new ArrayList<Bw>();
							String bws=jsonObj.getString("bw");
							Log.i("BW",bws);
							JSONArray bwArray=new JSONArray(bws);
							JSONObject bwobj=null;
							for(int k=0;k<bwArray.length();k++){
								Log.i("测试BW", "come  on");
								Bw bw=new Bw();
								String datastr=bwArray.getString(k).toString();
								bwobj=new JSONObject(datastr);
								bw.setId(bwobj.getString("id"));
								bw.setOd(bwobj.getString("od"));
								bw.setTy(bwobj.getString("ty"));
								Log.i("bw_id",bw.getId());
								Log.i("bw_od",bw.getOd());
								Log.i("bw_ty",bw.getTy());
								bwlist.add(bw);
							}
							downline.setBw(bwlist);
							pubUtil.downlines.add(downline);
						}
					}
				}
			} catch(NullPointerException e){
				e.printStackTrace();
				Log.i(TAG, "空指针异常");
				pubUtil.exception.setExceptionMsg("空指针异常");
			} catch (Exception e) {
				pubUtil.exception.setFlag(-5);
				Log.i("exception", pubUtil.exception.getFlag()+"");
				if(pubUtil.exception.getFlag()==-1){
					pubUtil.exception.setExceptionMsg("sectid有误");
					Log.i("exception","1");
				}else if(pubUtil.exception.getFlag()==-2){
					pubUtil.exception.setExceptionMsg("startdate有误");
					Log.i("exception", "2");
				}else if(pubUtil.exception.getFlag()==-3){
					pubUtil.exception.setExceptionMsg("enddate有误");
					Log.i("exception", "3");
				}else if(pubUtil.exception.getFlag()==-4){
					pubUtil.exception.setExceptionMsg("randomcode有误");
					Log.i("exception", "4");
				}else if(pubUtil.exception.getFlag()==-5){
					pubUtil.exception.setExceptionMsg("无测量水准路线");
					Log.i("exception","5");
				}else{
					pubUtil.exception.setExceptionMsg("其他错误");
					Log.i("exception","6");
				}
			}
		}
	}

