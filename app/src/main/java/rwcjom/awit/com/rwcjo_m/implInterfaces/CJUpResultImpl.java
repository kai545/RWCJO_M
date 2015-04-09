package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.content.Context;
import android.util.Log;

import com.mor.dataacquisition.DataAcquisition;
import com.mor.dataacquisition.net.dataCallBacks.CJUpResultDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;
import com.mor.dataacquisition.struct.AClass;

import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJUpResultInterface;

public class CJUpResultImpl implements CJUpResultInterface {
	private static int result=1;

	@Override
	public Integer getCJUpResult(AClass[] alist, String sdata, String sjid,
			String nyid, String remark, String account, String pwd,Context context) {
		/*调用jar包里面的方法，然后进行回调*/
		DataAcquisition.getInstance().CJUpResult(alist,sdata,sjid,nyid,remark,
				account,pwd,context,new CJUpResultDataCallBack(){

			@Override
			public void processData(CJResutResult data) {
				result=data.returnCode;
				Log.i("进入方法", "你妹啊，居然不出来"+result);	
				Log.i("进入方法",result+"");	
				if(result !=0){
					switch(result){
					case -1:
						pubUtil.exception.setExceptionMsg("其他错误");
					pubUtil.downReturnCode.setReturnCode(result);
						break;
					case -2:pubUtil.exception.setExceptionMsg("sdate有误");
					pubUtil.downReturnCode.setReturnCode(result);
						break;
					case -3:pubUtil.exception.setExceptionMsg("sjid有误");
					pubUtil.downReturnCode.setReturnCode(result);
					break;
					case -4:pubUtil.exception.setExceptionMsg("nyid有误");
					pubUtil.downReturnCode.setReturnCode(result);
					break;
					case -5: pubUtil.exception.setExceptionMsg("remark有误");
					pubUtil.downReturnCode.setReturnCode(result);
					break;
					case -6:pubUtil.exception.setExceptionMsg("account有误");
					pubUtil.downReturnCode.setReturnCode(result);
					break;
					case -7:pubUtil.exception.setExceptionMsg("pwd有误");
					pubUtil.downReturnCode.setReturnCode(result);
					break;
					case -15:pubUtil.exception.setExceptionMsg("网络异常");
					pubUtil.downReturnCode.setReturnCode(result);
					break;
					}
					Log.i("上传成果异常：",pubUtil.exception.getExceptionMsg());
					Log.i("上传结果：",result+"");
					Log.i("上传returncoede：",pubUtil.downReturnCode.getReturnCode()+"");
				}
			}
		});
		Log.i("result：",result+"");
		return result;
	}
	
}
