package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownface;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownfaceInterface;


public class CJDownfaceImpl implements CJDownfaceInterface {
	private String TAG="CJDownfaceImpl";
	private String result;
	@Override
	public void getCJDownface(String siteid, String startdate, String enddate,
			String randomcode) {
				String[] faceStr;
				try {
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
					if(pubUtil.downfaces.size()>0){
						pubUtil.downfaces.clear();
					}
					// 获取返回的结果
					for(int i =0;i<object.getPropertyCount();i++){
						result = object.getProperty(i).toString();
						Log.i("result", result);
						faceStr=result.split(ValueConfig.SPLIT_CHAR);
						if(faceStr.length==5){
							if(faceStr[0].equals("-1")){
								pubUtil.exception.setExceptionMsg("siteid有误");
							}else if(faceStr[1].equals("-1")){
								pubUtil.exception.setExceptionMsg("startdate有误");
							}else if(faceStr[2].equals("-1")){
								pubUtil.exception.setExceptionMsg("enddate有误");
							}else if(faceStr[3].equals("-1")){
								pubUtil.exception.setExceptionMsg("randomcode有误");
							}else {
								pubUtil.exception.setExceptionMsg("该标段下无相应的工点");
							}
							Log.i("exception", pubUtil.exception.getExceptionMsg());
						}else if (faceStr.length==3){
								CJDownface downface=new CJDownface();
								downface.setFaceId(faceStr[0]);
								downface.setFaceCode(faceStr[1]);
								downface.setFaceName(faceStr[2]);
								pubUtil.downfaces.add(downface);
						}
					}
				} catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					pubUtil.exception.setExceptionMsg("造型异常");
				}catch(ArrayIndexOutOfBoundsException e){
					Log.i(TAG,"数组下标越界");
					e.printStackTrace();
					pubUtil.exception.setExceptionMsg("下标越界");
				}catch(NullPointerException e){
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
