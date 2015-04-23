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

import rwcjom.awit.com.rwcjo_m.bean.CJDownline;
import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownlineInterface;

public class CJDownlineImpl implements CJDownlineInterface {
	private String TAG="CJDownlineImpl";
	private CJDownline downlineObj;
	private List<BwInfo> bwInfoList;
	private BwInfo bwInfoObj;
	private Line lineObj;
	private String result;
		@Override
		public CJDownline getCJDownline(String sectid, String startdate, String enddate,
				String randomcode) {
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
				SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
				object=(SoapObject)envelope.bodyIn;
				Log.i(TAG,object.toString());
				if(object ==null){
					Log.i(TAG, "Object is null");
				}
				for(int i =0;i<object.getPropertyCount();i++){
					downlineObj=new CJDownline();
					result = object.getProperty(i).toString();
					jsonLine=new JSONArray(result);
					Log.i("res", jsonLine.get(0).toString());
					if(jsonLine.length()>1){
						Log.i("len", jsonLine.length()+"");
						for(int j=0;j<jsonLine.length();j++){
							lineObj=new Line();
							String data=jsonLine.get(j).toString();
							jsonObj=new JSONObject(data);
							lineObj.setLc(jsonObj.getString("lc"));
							lineObj.setLn(jsonObj.getString("ln"));
							Log.i("LC", lineObj.getLc());
							Log.i("LN", lineObj.getLn());
							bwInfoList=new ArrayList<BwInfo>();
							String bws=jsonObj.getString("bw");
							JSONArray bwArray=new JSONArray(bws);
							JSONObject bwobj=null;
							for(int k=0;k<bwArray.length();k++){
								bwInfoObj=new BwInfo();
								String datastr=bwArray.getString(k).toString();
								bwobj=new JSONObject(datastr);
								bwInfoObj.setId(bwobj.getString("id"));
								bwInfoObj.setOd(bwobj.getString("od"));
								bwInfoObj.setTy(bwobj.getString("ty"));
								bwInfoList.add(bwInfoObj);
							}
							downlineObj.setBw(bwInfoList);
							downlineObj.setLineObj(lineObj);
						}
					}else{
						String data=jsonLine.get(0).toString();
						jsonObj=new JSONObject(data);
						if(jsonObj.getString("flag").equals("-1")){
							downlineObj.setFlag(-1);
							downlineObj.setMsg("sectid有误");
							Log.i("exception","1");
						}else if(jsonObj.getString("flag").equals("-2")){
							downlineObj.setFlag(-1);
							downlineObj.setMsg("startdate有误");
							Log.i("exception", "2");
						}else if(jsonObj.getString("flag").equals("-3")){
							downlineObj.setFlag(-1);
							downlineObj.setMsg("enddate有误");
							Log.i("exception", "3");
						}else if(jsonObj.getString("flag").equals("-4")){
							downlineObj.setFlag(-1);
							downlineObj.setMsg("randomcode有误");
							Log.i("exception", "4");
						}else if(jsonObj.getString("flag").equals("-5")){
							downlineObj.setFlag(-1);
							downlineObj.setMsg("无测量水准路线");
							Log.i("exception","5");
						}else{
							downlineObj.setFlag(-1);
							downlineObj.setMsg("其他错误");
							Log.i("exception","6");
						}
					}
				}
			}catch(ClassCastException e){
				e.printStackTrace();
				Log.i(TAG, "造型异常");
				downlineObj.setFlag(-2);
				downlineObj.setMsg("造型异常");
			} catch(NullPointerException e){
				e.printStackTrace();
				Log.i(TAG, "空指针异常");
				downlineObj.setFlag(-2);
				downlineObj.setMsg("空指针异常");
			} catch (Exception e) {
				e.printStackTrace();
				Log.i(TAG,"网络异常");
				downlineObj.setFlag(-2);
				downlineObj.setMsg("网络异常");
			}
			return downlineObj;
		}
	}

