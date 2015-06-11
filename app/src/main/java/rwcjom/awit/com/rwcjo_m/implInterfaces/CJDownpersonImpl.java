package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownperson;
import rwcjom.awit.com.rwcjo_m.dao.PersonInfo;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownpersonInterface;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;


public class CJDownpersonImpl implements CJDownpersonInterface {
	private String TAG="CJDownpersonImpl";
	private CJDownperson downpersonObj;
	private List<PersonInfo> personInfoList;
	private PersonInfo personInfoObj;
	private String result;
	@Override
	public CJDownperson getCJDownperson(String sectid, String ptype, String randomcode) {
				String[] resStr;
				try {
					downpersonObj=new CJDownperson();
					personInfoList=new ArrayList<PersonInfo>();
					Log.i(TAG,randomcode);
					String methodNameString="CJDownperson";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("sectid",sectid);
					paramsvalue.put("ptype",ptype);
					paramsvalue.put("randomcode",randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					Log.i(TAG,object.toString());
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					// 获取返回的结果
					for(int i=0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						Log.i(TAG,"result:"+result);
						resStr=result.split(ValueConfig.SPLIT_CHAR);
						if(resStr.length==4){
							downpersonObj.setFlag(-1);
							if(resStr[0].equals("-1")){
								downpersonObj.setMsg("sectid有误");
							}else if(resStr[1].equals("-1")){
								downpersonObj.setMsg("ptype有误");
							}else if(resStr[2].equals("-1")){
								downpersonObj.setMsg("randomcode有误");
							}else{
								downpersonObj.setMsg("该工点下无相应的类别人员");
							}
						}else {
							downpersonObj.setFlag(0);
							personInfoObj=new PersonInfo();
							personInfoObj.setUserid(resStr[0]);
							personInfoObj.setUsername(resStr[1]);
							personInfoObj.setUsertel(resStr[2]);
							personInfoList.add(personInfoObj);
						}
						downpersonObj.setPersonInfoList(personInfoList);
					}
					 
				} catch(ClassCastException e) {
					e.printStackTrace();
					downpersonObj.setFlag(-2);
					downpersonObj.setMsg("造型异常");
				}catch(ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					downpersonObj.setFlag(-2);
					downpersonObj.setMsg("下标越界");
				}catch (NullPointerException e) {
					e.printStackTrace();
					downpersonObj.setFlag(-2);
					downpersonObj.setMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					downpersonObj.setFlag(-2);
					downpersonObj.setMsg("网络异常");
				}
		return downpersonObj;
	}

}
