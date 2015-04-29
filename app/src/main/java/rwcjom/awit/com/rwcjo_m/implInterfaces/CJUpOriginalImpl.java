package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.content.Context;
import android.util.Log;

import com.mor.dataacquisition.DataAcquisition;
import com.mor.dataacquisition.net.dataCallBacks.CJUpOriginalDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;
import com.mor.dataacquisition.struct.BClass;

import rwcjom.awit.com.rwcjo_m.bean.CJUpOriginal;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJUpOriginalInterface;


public class CJUpOriginalImpl implements CJUpOriginalInterface {
//	private CJUpOriginal cjupOriginalObj;
	private int result;

	@Override
	public Integer getCJUpOriginal(BClass[] blist, String equipbrand,
										String instrumodel, String serialnum, String sjid,
										String temperature, String barometric, String weather, String benchmarkids,
										String mtype, String mdate, String linecode, String account,
										String pwd, Context context) {
//		cjupOriginalObj = new CJUpOriginal();

		DataAcquisition.getInstance().CJUpOriginal(blist, equipbrand,
				instrumodel, serialnum, sjid, temperature, barometric, weather, benchmarkids, mtype,
				mdate, linecode, account, pwd,
				context, new CJUpOriginalDataCallBack() {
					@Override
					public void processData(CJResutResult data) {
						super.processData(data);
						result = data.returnCode;
						Log.i("进入org方法", result + "");
//						cjupOriginalObj.setFlag(0);
//						cjupOriginalObj.setResult(result);
						if (result != 0) {
							//cjupOriginalObj.setFlag(-1);
							switch (result) {
								case -1:
									pubUtil.exception.setExceptionMsg("其他错误");
									break;
								case -2:
									pubUtil.exception.setExceptionMsg("equipbrand有误");
									break;
								case -3:
									pubUtil.exception.setExceptionMsg("instrumodel有误");
									break;
								case -4:
									pubUtil.exception.setExceptionMsg("serialnum有误");
									break;
								case -5:
									pubUtil.exception.setExceptionMsg("sjid有误");
									break;
								case -6:
									pubUtil.exception.setExceptionMsg("temperature有误");
									break;
								case -7:
									pubUtil.exception.setExceptionMsg("barometric有误");
									break;
								case -8:
									pubUtil.exception.setExceptionMsg("weather有误");
									break;
								case -9:
									pubUtil.exception.setExceptionMsg("benchmarkids有误");
									break;
								case -10:
									pubUtil.exception.setExceptionMsg("mtype有误");
									break;
								case -11:
									pubUtil.exception.setExceptionMsg("mdate有误");
									break;
								case -12:
									pubUtil.exception.setExceptionMsg("linecode有误");
									break;
								case -13:
									pubUtil.exception.setExceptionMsg("account有误");
									break;
								case -14:
									pubUtil.exception.setExceptionMsg("pwd有误");
									break;
								case -15:
									pubUtil.exception.setExceptionMsg("网络异常");
									break;
							}
							Log.i("原始数据上传：", pubUtil.exception.getExceptionMsg());
							Log.i("orgResult",result+"org");
						}
					}
				});
		return result;
	}
}
