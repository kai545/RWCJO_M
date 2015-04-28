package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownbrginfo;
import rwcjom.awit.com.rwcjo_m.dao.BrgInfo;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownbrginfoInterface;

public class CJDownbrginfoImpl implements CJDownbrginfoInterface {
	private String TAG="CJDownbrginfoImpl";
	private CJDownbrginfo downbrginfoObj;
	private BrgInfo brginfoObj;
	@Override
	public CJDownbrginfo getCJDownbrginfo(String siteid, String faceid, String randomcode) {
				try {
					downbrginfoObj=new CJDownbrginfo();
					Log.i(TAG,randomcode);
					String methodNameString="CJDownbrginfo";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("siteid",siteid);
					paramsvalue.put("faceid",faceid);
					paramsvalue.put("randomcode",randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					if(object.getPropertyCount()==3){
						downbrginfoObj.setFlag(-1);
						if(object.getProperty(0).toString().equals("-1")){
							downbrginfoObj.setMsg("siteid有误");
						}else if(object.getProperty(1).toString().equals("-1")){
							downbrginfoObj.setMsg("faceid有误");
						}else{
							downbrginfoObj.setMsg("randomcode有误");
						}
					}else if(object.getPropertyCount()==6) {
						downbrginfoObj.setFlag(0);
						brginfoObj = new BrgInfo();
						for(int i=0;i<object.getPropertyCount();i++){
							String var=object.getProperty(i).toString();
							if(var.equals("anyType{}")){
								var=" ";
							}
							switch (i){
								case 0:brginfoObj.setFaceid(var);break;
								case 1:brginfoObj.setStructname(var);break;
								case 2:brginfoObj.setPiernum(var);break;
								case 3:brginfoObj.setBeamspan(var);break;
								case 4:brginfoObj.setBeamtype(var);break;
								case 5:brginfoObj.setRemark(var);break;
							}
							downbrginfoObj.setBrgInfoObj(brginfoObj);
						}
					}
				}catch(ClassCastException e){
					e.printStackTrace();
					downbrginfoObj.setFlag(-2);
					downbrginfoObj.setMsg("造型异常");
				}catch(NullPointerException e){
					e.printStackTrace();
					downbrginfoObj.setFlag(-2);
					downbrginfoObj.setMsg("空指针异常");
				} catch (Exception e) {
					e.printStackTrace();
					downbrginfoObj.setFlag(-2);
					downbrginfoObj.setMsg("网络异常");
				}
		return downbrginfoObj;
	}

}
