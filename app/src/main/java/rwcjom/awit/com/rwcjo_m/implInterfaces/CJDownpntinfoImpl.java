package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownpntinfo;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownpntinfoInterface;


public class CJDownpntinfoImpl implements CJDownpntinfoInterface {
	private String TAG="CJDownpntinfoImpl";
	private String result;
	@Override
	public void getCJDownputinfo(String faceid, String objstate,
			String randomcode) {
				try {
					Log.i(TAG,randomcode);
					String methodNameString="CJDownpntinfo";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("faceid",faceid);
					paramsvalue.put("objstate",objstate);
					paramsvalue.put("randomcode",randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.bodyIn;
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					if(pubUtil.downpntinfos.size()>0){
						pubUtil.downpntinfos.clear();
					}
					JSONArray jsonpnt;
					JSONObject jsonObj;
					// 获取返回的结果
					for(int i=0;i<object.getPropertyCount();i++){
						 result = object.getProperty(i).toString();
						 Log.i(TAG,result);
						 jsonpnt=new JSONArray(result);
							 for(int j=0;j<jsonpnt.length();j++){
								 String data=jsonpnt.get(j).toString();
								 jsonObj=new JSONObject(data);
								 if(!(jsonObj.getString("pointid").equals("0"))){
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
								 }else {
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
					}
				} catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					pubUtil.exception.setExceptionMsg("造型异常");
				} catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					pubUtil.exception.setExceptionMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "网络异常");
					pubUtil.exception.setExceptionMsg("网络异常");
				}
	}

}
