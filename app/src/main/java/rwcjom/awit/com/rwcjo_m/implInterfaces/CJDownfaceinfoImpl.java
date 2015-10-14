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
					Log.i("CJDownfaceinfoLength", object.getPropertyCount() + "");//测试数据判断数据的长度
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
							String var=object.getProperty(i).toString();
							Log.i(TAG,var);//测试数据
							if(var.equals("anyType{}")){
								var=" ";
							}
							switch(i){
								case 0:faceinfoObj.setFaceid(var);break;
								case 1:faceinfoObj.setJointflag(var);break;
								case 2:faceinfoObj.setStructtype(var);break;
								case 3:faceinfoObj.setStructname(var);break;
								case 4:faceinfoObj.setStructbase(var);break;
								case 5:faceinfoObj.setDesignatt(var);break;
								case 6:faceinfoObj.setPiernum(var);break;
								case 7:faceinfoObj.setDkname(var);break;
								case 8:faceinfoObj.setDkilo(var);break;
								case 9:faceinfoObj.setDchain(var);break;
								case 10:faceinfoObj.setRkname(var);break;
								case 11:faceinfoObj.setRkilo(var);break;
								case 12:faceinfoObj.setRchain(var);break;
								case 13:faceinfoObj.setRemark(var);break;
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
