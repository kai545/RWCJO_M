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
	private FaceInfo faceinfoObj;
	@Override
	public CJDownfaceinfo getCJDownfaceinfo(String siteid, String faceid,
			String randomcode) {
				try {
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
						for(int i=0;i<object.getPropertyCount();i++){
							switch(i){
								case 0:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setFaceid("");
									}else{
										faceinfoObj.setFaceid(object.getProperty(i).toString());
									}
									break;
								case 1:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setJointflag("");
									}else{
										faceinfoObj.setJointflag(object.getProperty(i).toString());
									}
									break;
								case 2:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setStructtype("");
									}else{
										faceinfoObj.setStructtype(object.getProperty(i).toString());
									}
									break;
								case 3:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setStructname("");
									}else{
										faceinfoObj.setStructname(object.getProperty(i).toString());
									}
									break;
								case 4:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setStructbase("");
									}else{
										faceinfoObj.setStructbase(object.getProperty(i).toString());
									}
									break;
								case 5:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setDesignatt("");
									}else{
										faceinfoObj.setDesignatt(object.getProperty(i).toString());
									}
									break;
								case 6:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setPiernum("");
									}else{
										faceinfoObj.setPiernum(object.getProperty(i).toString());
									}
									break;
								case 7:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setDkname("");
									}else{
										faceinfoObj.setDkname(object.getProperty(i).toString());
									}
									break;
								case 8:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setDkilo("");
									}else{
										faceinfoObj.setDkilo(object.getProperty(i).toString());
									}
									break;
								case 9:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setDchain("");
									}else{
										faceinfoObj.setDchain(object.getProperty(i).toString());
									}
									break;
								case 10:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setRkname("");
									}else{
										faceinfoObj.setRkname(object.getProperty(i).toString());
									}
									break;
								case 11:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setRkilo("");
									}else{
										faceinfoObj.setRkilo(object.getProperty(i).toString());
									}
									break;
								case 12:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setRchain("");
									}else{
										faceinfoObj.setRchain(object.getProperty(i).toString());
									}
									break;
								case 13:
									if(object.getProperty(i).toString().equals("anyType{}")){
										faceinfoObj.setRemark("");
										Log.i(TAG,faceinfoObj.getRemark()+"kkkk");
									}else{
										faceinfoObj.setRemark(object.getProperty(i).toString());
									}
									break;
							}
							downfaceinfoObj.setFaceinfoObj(faceinfoObj);
						}
					}
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
