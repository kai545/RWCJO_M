package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownbasepnt;
import rwcjom.awit.com.rwcjo_m.dao.BasePntInfo;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownbasepntInterface;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;


public class CJDownbasepntImpl implements CJDownbasepntInterface {
	private String TAG="CJDownbasepntImpl";
	private CJDownbasepnt downbasepntObj;
	private List<BasePntInfo> basePntInfoList;
	private BasePntInfo basePntInfoObj;
	private String result;
	@Override
	public CJDownbasepnt getCJDownbasepnt(String sectid, String randomcode) {
				try {
					downbasepntObj=new CJDownbasepnt();
					basePntInfoList=new ArrayList<BasePntInfo>();
					Log.i(TAG,randomcode);
					String methodNameString="CJDownbasepnt";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("sectid",sectid);
					paramsvalue.put("randomcode",randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					String[] resStr;
					// 获取返回的结果
					for(int i=0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						resStr=result.split(ValueConfig.SPLIT_CHAR);
						if(resStr.length==3){
							downbasepntObj.setFlag(-1);
							if(resStr[0].equals("-1")){
								downbasepntObj.setMsg("sectid有误");
							}else if(resStr[1].equals("-1")){
								downbasepntObj.setMsg("randomcode有误");
							}else{
								downbasepntObj.setMsg("该标段无工作基点");
							}
						}else if(resStr.length==6){
								downbasepntObj.setFlag(0);
							    basePntInfoObj=new BasePntInfo();
								basePntInfoObj.setBasepntid(resStr[0]);
								basePntInfoObj.setBasepntname(resStr[1]);
								basePntInfoObj.setBasepntcode(resStr[2]);
								basePntInfoObj.setBasepnthigh(resStr[3]);
								basePntInfoObj.setBasepntnum(resStr[4]);
								basePntInfoObj.setBasepntvar(resStr[5]);
								basePntInfoList.add(basePntInfoObj);
							}
						downbasepntObj.setBasePntInfoList(basePntInfoList);
					}
				} catch(ClassCastException e){
					e.printStackTrace();
					downbasepntObj.setFlag(-2);
					downbasepntObj.setMsg("造型异常");
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					downbasepntObj.setFlag(-2);
					downbasepntObj.setMsg("下标越界");
				} catch(NullPointerException e) {
					e.printStackTrace();
					downbasepntObj.setFlag(-2);
					downbasepntObj.setMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					downbasepntObj.setFlag(-2);
					downbasepntObj.setMsg("网络异常");
				}
		return downbasepntObj;
	}

}
