package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownsectsite;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.bean.sect;
import rwcjom.awit.com.rwcjo_m.bean.siteObject;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownsectsiteInterface;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;



public class CJDownsectsiteImpl implements CJDownsectsiteInterface {
	private List<CJDownsectsite> cjdownsectsitelist=new ArrayList<CJDownsectsite>();
	private sect sectObj;
	private List<siteObject> sitelist;
	private siteObject siteobj;
	private List<String> putlist=new ArrayList<String>();
	private final String TAG="CJDownsectsiteImpl";
	private String result="";
	private String[] secStr;
	@Override
	public List<CJDownsectsite> getCJDownsectsite(String sectid, String sitetype,
			String randomcode) {
				try {
					Log.i(TAG,randomcode);
					String methodNameString="CJDownsectsite";
					Map<String,String> paramsvalue=new LinkedHashMap<String,String>();
					paramsvalue.put("sectid", sectid);
					paramsvalue.put("sitetype", sitetype);
					paramsvalue.put("randomcode", randomcode);
					SoapSerializationEnvelope envelope=CommonTools.getEnvelope(methodNameString,paramsvalue);
					SoapObject object=(SoapObject)envelope.getResponse();
					if(object ==null){
						Log.i(TAG, "Object is null");
					}
					Log.i(TAG,pubUtil.downsectsites.size()+"");
					if(pubUtil.downsectsites.size()>0){
						pubUtil.downsectsites.clear();
					}
					// 获取返回的结果
					Log.i(TAG,object.getPropertyCount()+"");
					for(int i =0;i<object.getPropertyCount();i++){
						CJDownsectsite downsectsite=new CJDownsectsite();
						result = object.getProperty(i).toString();
						Log.i("result", result);
						secStr=result.split(ValueConfig.SPLIT_CHAR);
						if(secStr.length==4){
							downsectsite.setFlag(-1);
							if(secStr[0].equals("-1")){
								downsectsite.setMsg("sectid有误");
							}else if(secStr[1].equals("-1")){
								downsectsite.setMsg("sitetype有误");
							}else if(secStr[2].equals("-1")){
								downsectsite.setMsg("randomcode有误");
							}else{
								downsectsite.setMsg("该标段下无相应的工点");
							}
							Log.i("exception",downsectsite.getMsg());
						}else if(secStr.length==3){
							downsectsite.setFlag(0);
							sectObj=new sect();
							sectObj.setSectid(secStr[0]);
							sectObj.setSectcode(secStr[1]);
							sectObj.setSectname(secStr[2]);
							downsectsite.setSectObj(sectObj);
							cjdownsectsitelist.add(downsectsite);
						}else if(secStr.length==5){
							sitelist=new ArrayList<siteObject>();
							siteobj=new siteObject();
							siteobj.setSiteid(secStr[0]);
							siteobj.setSitecode(secStr[1]);
							siteobj.setSitename(secStr[2]);
							siteobj.setStartsite(secStr[3]);
							siteobj.setEndsite(secStr[4]);
							sitelist.add(siteobj);
							downsectsite.setSitelist(sitelist);
							cjdownsectsitelist.add(downsectsite);
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
				}catch(NullPointerException e){
					e.printStackTrace();
					Log.i(TAG, "空指针异常");
					pubUtil.exception.setExceptionMsg("空指针异常");
				} catch (Exception e) {
					e.printStackTrace();
					pubUtil.exception.setExceptionMsg("网络异常");
				}
		return cjdownsectsitelist;
	}
	
}
