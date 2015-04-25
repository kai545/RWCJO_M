package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownfaceinfo;
import rwcjom.awit.com.rwcjo_m.dao.FaceInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownfaceinfoInterface;


public class CJDownfaceinfoImpl implements CJDownfaceinfoInterface {
	private String TAG="CJDownfaceinfoImpl";
	private CJDownfaceinfo downfaceinfoObj;
	private List<FaceInfo> faceInfoList;
	private FaceInfo faceinfoObj;
	@Override
	public CJDownfaceinfo getCJDownfaceinfo(String siteid, String faceid,
			String randomcode) {
				try {
					downfaceinfoObj=new CJDownfaceinfo();
					faceInfoList=new ArrayList<FaceInfo>();
					downfaceinfoObj=new CJDownfaceinfo();
					Log.i(TAG,randomcode);
					String methodNameString="CJDownfaceinfo";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("siteid",siteid);
					paramsvalue.put("faceid",faceid);
					paramsvalue.put("randomcode",randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					// 获取返回的结果
					Log.i("CJDownfaceinfoLength", object.getPropertyCount() + "");
					if(object.getPropertyCount()==3){
						downfaceinfoObj.setFlag(-1);
						if(object.getProperty(0).toString().equals("-1")){
							downfaceinfoObj.setMsg("siteid有误");
						}else if(object.getProperty(1).toString().equals("-1")){
							downfaceinfoObj.setMsg("faceid有误");
						}else{
							downfaceinfoObj.setMsg("randomcode有误");
						}
					}else if(object.getPropertyCount()==14){
						downfaceinfoObj.setFlag(0);
						faceinfoObj=new FaceInfo();
						faceinfoObj.setFaceid(object.getProperty(object.getPropertyCount() - 14).toString());
						faceinfoObj.setJointflag(object.getProperty(object.getPropertyCount() - 13).toString());
						faceinfoObj.setStructtype(object.getProperty(object.getPropertyCount() - 12).toString());
						faceinfoObj.setStructname(object.getProperty(object.getPropertyCount() - 11).toString());
						faceinfoObj.setStructbase(object.getProperty(object.getPropertyCount() - 10).toString());
						faceinfoObj.setDesignatt(object.getProperty(object.getPropertyCount() - 9).toString());
						faceinfoObj.setPiernum(object.getProperty(object.getPropertyCount() - 8).toString());
						faceinfoObj.setDkname(object.getProperty(object.getPropertyCount() - 7).toString());
						faceinfoObj.setDkilo(object.getProperty(object.getPropertyCount() - 6).toString());
						faceinfoObj.setDchain(object.getProperty(object.getPropertyCount() - 5).toString());
						faceinfoObj.setRkname(object.getProperty(object.getPropertyCount() - 4).toString());
						faceinfoObj.setRkilo(object.getProperty(object.getPropertyCount() - 3).toString());
						faceinfoObj.setRchain(object.getProperty(object.getPropertyCount() - 2).toString());
						faceinfoObj.setRemark(object.getProperty(object.getPropertyCount() - 1).toString());
						faceInfoList.add(faceinfoObj);
					}
					downfaceinfoObj.setFaceinfolist(faceInfoList);
				}catch(ClassCastException e) {
					e.printStackTrace();
					downfaceinfoObj.setFlag(-2);
					downfaceinfoObj.setMsg("造型异常");
				}catch (IndexOutOfBoundsException e){
					e.printStackTrace();
					downfaceinfoObj.setFlag(-2);
					downfaceinfoObj.setMsg("下标越界");
				} catch(NullPointerException e) {
					e.printStackTrace();
					downfaceinfoObj.setFlag(-2);
					downfaceinfoObj.setMsg("空指针异常");
				} catch (Exception e) {
					e.printStackTrace();
					downfaceinfoObj.setFlag(-2);
					downfaceinfoObj.setMsg("网络异常");
			}
		return downfaceinfoObj;
	}

}
