package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownface;
import rwcjom.awit.com.rwcjo_m.dao.FaceNews;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownfaceInterface;


public class CJDownfaceImpl implements CJDownfaceInterface {
	private String TAG="CJDownfaceImpl";
	private List<CJDownface> cjdownfacelist;
	private CJDownface cjdownface;
	private List<FaceNews> facelist;
	private FaceNews faceObj;
	private String result;
	private String[] faceStr;
	@Override
	public List<CJDownface> getCJDownface(String siteid, String startdate, String enddate,
			String randomcode) {
				try {
					cjdownfacelist=new ArrayList<CJDownface>();
					Log.i(TAG,randomcode);
					String methodNameString="CJDownface";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("siteid",siteid);
					paramsvalue.put("startdate",startdate);
					paramsvalue.put("enddate",enddate);
					paramsvalue.put("randomcode",randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					// 获取返回的结果
					for(int i =0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						Log.i("result", result);
						faceStr=result.split(ValueConfig.SPLIT_CHAR);
						if(faceStr.length==5){
							cjdownface=new CJDownface();
							cjdownface.setFlag(-1);
							if(faceStr[0].equals("-1")){
								cjdownface.setMsg("siteid有误");
							}else if(faceStr[1].equals("-1")){
								cjdownface.setMsg("startdate有误");
							}else if(faceStr[2].equals("-1")){
								cjdownface.setMsg("enddate有误");
							}else if(faceStr[3].equals("-1")){
								cjdownface.setMsg("randomcode有误");
							}else {
								cjdownface.setMsg("该标段下无相应的工点");
							}
							Log.i("exception",cjdownface.getMsg());
						}else if (faceStr.length==3){
								cjdownface=new CJDownface();
								facelist=new ArrayList<FaceNews>();
								faceObj=new FaceNews();
								cjdownface.setFlag(0);
								faceObj.setFaceid(faceStr[0]);
								faceObj.setFacecode(faceStr[1]);
								faceObj.setFacename(faceStr[2]);
								facelist.add(faceObj);
								cjdownface.setFacelist(facelist);
								cjdownfacelist.add(cjdownface);
						}
					}
				} catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					cjdownface.setFlag(-2);
					cjdownface.setMsg("造型异常");
				}catch(ArrayIndexOutOfBoundsException e){
					Log.i(TAG, "数组下标越界");
					e.printStackTrace();
					cjdownface.setFlag(-2);
					cjdownface.setMsg("下标越界");
				}catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					cjdownface.setFlag(-2);
					cjdownface.setMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "网络异常");
					cjdownface.setFlag(-2);
					cjdownface.setMsg("网络异常");
				}
		return cjdownfacelist;
	}
	
}
