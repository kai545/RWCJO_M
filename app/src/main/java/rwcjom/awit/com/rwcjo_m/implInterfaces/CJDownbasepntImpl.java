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

import rwcjom.awit.com.rwcjo_m.bean.CJDownbasepnt;
import rwcjom.awit.com.rwcjo_m.dao.BasePntInfo;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownbasepntInterface;


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
						Log.i("result", result);
						resStr=result.split(ValueConfig.SPLIT_CHAR);
						if(resStr.length==3){
							downbasepntObj=new CJDownbasepnt();
							downbasepntObj.setFlag(-1);
							if(resStr[0].equals("-1")){
								downbasepntObj.setMsg("sectid有误");
							}else if(resStr[1].equals("-1")){
								downbasepntObj.setMsg("randomcode有误");
							}else{
								downbasepntObj.setMsg("该标段无工作基点");
							}
							Log.i("exception", downbasepntObj.getMsg());
							}else if(resStr.length==6){
								downbasepntObj=new CJDownbasepnt();
								downbasepntObj.setFlag(0);
								basePntInfoList=new ArrayList<BasePntInfo>();
							    basePntInfoObj=new BasePntInfo();
								basePntInfoObj.setSiteid(resStr[0]);
								basePntInfoObj.setSitename(resStr[1]);
								basePntInfoObj.setSitecode(resStr[2]);
								basePntInfoObj.setSitehigh(resStr[3]);
								basePntInfoObj.setSitenum(resStr[4]);
								basePntInfoObj.setSitevar(resStr[5]);
								basePntInfoList.add(basePntInfoObj);
							}
						downbasepntObj.setBasePntInfoList(basePntInfoList);
					}
				} catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					downbasepntObj.setFlag(-2);
					downbasepntObj.setMsg("造型异常");
				} catch (ArrayIndexOutOfBoundsException e) {
					Log.i(TAG,"数组下标越界");
					e.printStackTrace();
					downbasepntObj.setFlag(-2);
					downbasepntObj.setMsg("下标越界");
				} catch(NullPointerException e) {
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					downbasepntObj.setFlag(-2);
					downbasepntObj.setMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "网络异常");
					downbasepntObj.setFlag(-2);
					downbasepntObj.setMsg("网络异常");
				}
		return downbasepntObj;
	}

}
