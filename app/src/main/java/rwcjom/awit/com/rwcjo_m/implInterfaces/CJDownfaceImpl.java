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
	private CJDownface cjdownface;
	private List<FaceNews> facelist;
	private FaceNews faceObj;
	private String result;
	private String[] faceStr;
	@Override
	public CJDownface getCJDownface(String siteid, String startdate, String enddate,
			String randomcode) {
				try {
					cjdownface=new CJDownface();
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
					facelist=new ArrayList<FaceNews>();
					// 获取返回的结果
					for(int i =0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						Log.i(TAG,result);
						faceStr=result.split(ValueConfig.SPLIT_CHAR);
						Log.i(TAG,faceStr.length+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");//测试数据
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
								cjdownface.setMsg("该工点下无断面(梁体)");
							}
							Log.i("exception", cjdownface.getMsg());
						}else if (faceStr.length==3){
								cjdownface=new CJDownface();
								faceObj=new FaceNews();
								cjdownface.setFlag(0);
								faceObj.setFaceId(faceStr[0]);
								faceObj.setFaceCode(faceStr[1]);
								faceObj.setFaceName(faceStr[2]);
								facelist.add(faceObj);
						}
						cjdownface.setFacelist(facelist);
					}
				} catch(ClassCastException e){
					e.printStackTrace();
					cjdownface.setFlag(-2);
					cjdownface.setMsg("造型异常");
				}catch(ArrayIndexOutOfBoundsException e){
					e.printStackTrace();
					cjdownface.setFlag(-2);
					cjdownface.setMsg("下标越界");
				}catch(NullPointerException e){
					e.printStackTrace();
					cjdownface.setFlag(-2);
					cjdownface.setMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					cjdownface.setFlag(-2);
					cjdownface.setMsg("网络异常");
				}
		return cjdownface;
	}
	
}
