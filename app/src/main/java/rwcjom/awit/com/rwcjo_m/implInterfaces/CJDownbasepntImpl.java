package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownbasepnt;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownbasepntInterface;


public class CJDownbasepntImpl implements CJDownbasepntInterface {
	private String TAG="CJDownbasepntImpl";
	private String result;
	@Override
	public void getCJDownbasepnt(String sectid, String randomcode) {
				try {
					Log.i(TAG,randomcode);
					String methodNameString="CJDownbasepnt";
					Map<String,String> paramsvalue=new LinkedHashMap<>();
					paramsvalue.put("sectid",sectid);
					paramsvalue.put("randomcode",randomcode);
					//SoapObject object=CommonTools.getObject(methodNameString,paramsvalue);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					if(pubUtil.downbasepnts.size()>0){
						pubUtil.downbasepnts.clear();
					}
					String[] resStr;
					// 获取返回的结果
					for(int i=0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						Log.i("result", result);
						resStr=result.split(ValueConfig.SPLIT_CHAR);
						if(resStr.length==3){
							if(resStr[0].equals("-1")){
								pubUtil.exception.setExceptionMsg("sectid有误");
							}else if(resStr[1].equals("-1")){
									pubUtil.exception.setExceptionMsg("randomcode有误");
							}else{
									pubUtil.exception.setExceptionMsg("该标段无工作基点");
							}
							Log.i("exception", pubUtil.exception.getExceptionMsg());
							}else if(resStr.length==6){
								 CJDownbasepnt downbasepnt=new CJDownbasepnt();
								 downbasepnt.setSiteid(resStr[0]);
								 downbasepnt.setSitename(resStr[1]);
								 downbasepnt.setSitecode(resStr[2]);
								 downbasepnt.setSitehigh(resStr[3]);
								 downbasepnt.setSitenum(resStr[4]);
								 downbasepnt.setSitevar(resStr[5]);
								 pubUtil.downbasepnts.add(downbasepnt);
							}
					}
				}catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					pubUtil.exception.setExceptionMsg("造型异常");
				}catch(ArrayIndexOutOfBoundsException e){
					Log.i(TAG,"数组下标越界");
					e.printStackTrace();
					pubUtil.exception.setExceptionMsg("下标越界");
				} catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					pubUtil.exception.setExceptionMsg("空指针异常");
				}catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "网络异常");
					pubUtil.exception.setExceptionMsg("网络异常");
				}
	}

}
