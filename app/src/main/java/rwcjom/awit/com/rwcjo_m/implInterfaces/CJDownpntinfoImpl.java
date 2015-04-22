package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownpntinfo;
import rwcjom.awit.com.rwcjo_m.dao.PntInfo;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownpntinfoInterface;


public class CJDownpntinfoImpl implements CJDownpntinfoInterface {
	private String TAG="CJDownpntinfoImpl";
	private List<CJDownpntinfo> downpntinfoList;
	private CJDownpntinfo downpntinfoObj;
	private List<PntInfo> pntInfoList;
	private PntInfo pntInfoObj;
	private String result;
	@Override
	public List<CJDownpntinfo> getCJDownputinfo(String faceid, String objstate,
			String randomcode) {
				try {
					downpntinfoList=new ArrayList<CJDownpntinfo>();
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
									 downpntinfoObj=new CJDownpntinfo();
									 downpntinfoObj.setFlag(0);
									 pntInfoList=new ArrayList<PntInfo>();
									 pntInfoObj=new PntInfo();
									 pntInfoObj.setPointid(jsonObj.getString("pointid"));
									 pntInfoObj.setPointnum(jsonObj.getString("pointnum"));
									 pntInfoObj.setDesignvalue(jsonObj.getString("designvalue"));
									 pntInfoObj.setDesignremark(jsonObj.getString("designremark"));
									 pntInfoObj.setInbuiltdate(jsonObj.getString("inbuiltdate"));
									 pntInfoObj.setSeatcode(jsonObj.getString("seatcode"));
									 pntInfoObj.setRemark(jsonObj.getString("remark"));
									 pntInfoObj.setPointcode(jsonObj.getString("pointcode"));
									 pntInfoObj.setName(jsonObj.getString("name"));
									 pntInfoList.add(pntInfoObj);
									 downpntinfoObj.setPntInfoList(pntInfoList);
									 downpntinfoList.add(downpntinfoObj);
								 }else {
									 String exStr=jsonpnt.get(0).toString();
									 jsonObj=new JSONObject(exStr);
									 downpntinfoObj=new CJDownpntinfo();
									 downpntinfoObj.setFlag(-1);
									 Log.i("res", result);
									 if(jsonObj.getString("faceid").equals("-1")){
										 downpntinfoObj.setMsg("faceid有误");
									 }else if(jsonObj.getString("objstate").equals("-1")){
										 downpntinfoObj.setMsg("objstate有误");
									 }else if(jsonObj.getString("randomcode").equals("-1")){
										 downpntinfoObj.setMsg("enddate有误");
									 }else{
										 downpntinfoObj.setMsg("无相应的测点信息");
									 }
									 Log.i("exception", downpntinfoObj.getMsg());
								 }
							 }
					}
				} catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					downpntinfoObj.setFlag(-2);
					downpntinfoObj.setMsg("造型异常");
				} catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					downpntinfoObj.setFlag(-2);
					downpntinfoObj.setMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "网络异常");
					downpntinfoObj.setFlag(-2);
					downpntinfoObj.setMsg("网络异常");
				}
		return downpntinfoList;
	}

}
