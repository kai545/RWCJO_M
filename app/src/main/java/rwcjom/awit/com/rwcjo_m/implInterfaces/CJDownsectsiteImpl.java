package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.bean.CJDownsectsite;
import rwcjom.awit.com.rwcjo_m.dao.SecNews;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;
import rwcjom.awit.com.rwcjo_m.util.ValueConfig;
import rwcjom.awit.com.rwcjo_m.interfaces.CJDownsectsiteInterface;



public class CJDownsectsiteImpl implements CJDownsectsiteInterface {
	private final String TAG="CJDownsectsiteImpl";
	private CJDownsectsite downsectsite;
	private List<SiteNews> sitelist;
	private SecNews sectObj;
	private SiteNews siteobj;
	private String result="";
	private String[] resultStr;
	/**Flag=0:返回值正常；Flag=-1：返回值异常；Flag=-2：接口异常*/
	@Override
	public CJDownsectsite getCJDownsectsite(String sectid, String sitetype,
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
					// 获取返回的结果
					Log.i(TAG,object.getPropertyCount()+"");
					for(int i =0;i<object.getPropertyCount();i++){
						downsectsite=new CJDownsectsite();
						result = object.getProperty(i).toString();
						Log.i("result", result);
						resultStr=result.split(ValueConfig.SPLIT_CHAR);
						if(resultStr.length==4){
							downsectsite.setFlag(-1);
							if(resultStr[0].equals("-1")){
								downsectsite.setMsg("sectid有误");
							}else if(resultStr[1].equals("-1")){
								downsectsite.setMsg("sitetype有误");
							}else if(resultStr[2].equals("-1")){
								downsectsite.setMsg("randomcode有误");
							}else{
								downsectsite.setMsg("该标段下无相应的工点");
							}
						}else if(resultStr.length==3){
							downsectsite.setFlag(0);
							sectObj=new SecNews();
							sectObj.setSectid(resultStr[0]);
							sectObj.setSectcode(resultStr[1]);
							sectObj.setSectname(resultStr[2]);
							downsectsite.setSecObj(sectObj);
						}else if(resultStr.length==5){
							downsectsite.setFlag(0);
							sitelist=new ArrayList<SiteNews>();
							siteobj=new SiteNews();
							siteobj.setSiteid(resultStr[0]);
							siteobj.setSitecode(resultStr[1]);
							siteobj.setSitename(resultStr[2]);
							siteobj.setStartsite(resultStr[3]);
							siteobj.setEndsite(resultStr[4]);
							sitelist.add(siteobj);
							downsectsite.setSitelist(sitelist);
						}
					}
				}catch(ClassCastException e){
					e.printStackTrace();
					Log.i(TAG, "造型异常");
					downsectsite.setFlag(-2);
					downsectsite.setMsg("造型异常");
				}catch(ArrayIndexOutOfBoundsException e){
					Log.i(TAG,"数组下标越界");
					e.printStackTrace();
					downsectsite.setFlag(-2);
					downsectsite.setMsg("下标越界");
				}catch(NullPointerException e){
					e.printStackTrace();
					downsectsite.setFlag(-2);
					Log.i(TAG, "空指针异常");
					downsectsite.setMsg("空指针异常");
				} catch (Exception e) {
					e.printStackTrace();
					downsectsite.setFlag(-2);
					downsectsite.setMsg("网络异常");
				}
		return downsectsite;
	}
	
}
