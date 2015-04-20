package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownfaceinfo;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownfaceinfoInterface;


public class CJDownfaceinfoImpl implements CJDownfaceinfoInterface {
	private String TAG="CJDownfaceinfoImpl";
	private String result;
	@Override
	public void getCJDownfaceinfo(String siteid, String faceid,
			String randomcode) {
				try {
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
					if(pubUtil.downfaceinfos.size()>0){
						pubUtil.downfaceinfos.clear();
					}
					// 获取返回的结果
					Log.i("CJDownfaceinfoLength", object.getPropertyCount()+"");
					if(object.getPropertyCount()==3){
						if(object.getProperty(0).toString().equals("-1")){
							pubUtil.exception.setExceptionMsg("siteid有误");
						}else if(object.getProperty(1).toString().equals("-1")){
							pubUtil.exception.setExceptionMsg("faceid有误");
						}else{
							pubUtil.exception.setExceptionMsg("randomcode有误");
						}
						Log.i("exception", pubUtil.exception.getExceptionMsg());
					}else if(object.getPropertyCount()==14){
						CJDownfaceinfo downfaceinfo=new CJDownfaceinfo();
						downfaceinfo.setFaceid(object.getProperty(object.getPropertyCount()-14).toString());
						downfaceinfo.setJointflag(object.getProperty(object.getPropertyCount()-13).toString());
						downfaceinfo.setStructtype(object.getProperty(object.getPropertyCount()-12).toString());
						downfaceinfo.setStructname(object.getProperty(object.getPropertyCount()-11).toString());
						downfaceinfo.setStructbase(object.getProperty(object.getPropertyCount()-10).toString());
						downfaceinfo.setDesignatt(object.getProperty(object.getPropertyCount()-9).toString());
						downfaceinfo.setPiernum(object.getProperty(object.getPropertyCount()-8).toString());
						downfaceinfo.setDkname(object.getProperty(object.getPropertyCount()-7).toString());
						downfaceinfo.setDkilo(object.getProperty(object.getPropertyCount()-6).toString());
						downfaceinfo.setDchain(object.getProperty(object.getPropertyCount()-5).toString());
						downfaceinfo.setRkname(object.getProperty(object.getPropertyCount()-4).toString());
						downfaceinfo.setRkilo(object.getProperty(object.getPropertyCount()-3).toString());
						downfaceinfo.setRchain(object.getProperty(object.getPropertyCount()-2).toString());
						downfaceinfo.setRemark(object.getProperty(object.getPropertyCount()-1).toString());
						pubUtil.downfaceinfos.add(downfaceinfo);
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
					pubUtil.exception.setExceptionMsg("网络异常");
			}
	}

}
