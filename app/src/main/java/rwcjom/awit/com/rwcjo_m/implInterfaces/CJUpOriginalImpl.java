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
	private int result=-6;
	private CJUpOriginal upOriginalObj;
	@Override
	public CJUpOriginal getCJUpOriginal(BClass[] blist, String equipbrand,
										String instrumodel, String serialnum, String sjid,
										String temperature, String barometric, String weather, String benchmarkids,
										String mtype, String mdate, String linecode, String account,
										String pwd, Context context,CJUpOriginalDataCallBack callBack) {
		Log.i("come on", "baby");
		DataAcquisition.getInstance().CJUpOriginal(blist, equipbrand,
				instrumodel, serialnum, sjid, temperature, barometric, weather, benchmarkids, mtype,
				mdate, linecode, account, pwd,
				context, new CJUpOriginalDataCallBack() {
					@Override
					public void processData(CJResutResult data) {
						super.processData(data);
						upOriginalObj = new CJUpOriginal();
						Log.i("进入org方法", result + "121");
						result = data.returnCode;
						upOriginalObj.setFlag(0);
						upOriginalObj.setResult(result);
						Log.i("进入org方法", result + "");
						if (result != 0) {
							upOriginalObj.setFlag(-1);
							Log.i("进来没？？？", "进来了 哈哈");
							switch (result) {
								case -1:
									upOriginalObj.setMsg("其他错误");
									break;
								case -2:
									upOriginalObj.setMsg("equipbrand有误");
									break;
								case -3:
									upOriginalObj.setMsg("instrumodel有误");
									break;
								case -4:
									upOriginalObj.setMsg("serialnum有误");
									break;
								case -5:
									upOriginalObj.setMsg("sjid有误");
									break;
								case -6:
									upOriginalObj.setMsg("temperature有误");
									break;
								case -7:
									upOriginalObj.setMsg("barometric有误");
									break;
								case -8:
									upOriginalObj.setMsg("weather有误");
									break;
								case -9:
									upOriginalObj.setMsg("benchmarkids有误");
									break;
								case -10:
									upOriginalObj.setMsg("mtype有误");
									break;
								case -11:
									upOriginalObj.setMsg("mdate有误");
									break;
								case -12:
									upOriginalObj.setMsg("linecode有误");
									break;
								case -13:
									upOriginalObj.setMsg("account有误");
									break;
								case -14:
									upOriginalObj.setMsg("pwd有误");
									break;
								case -15:
									upOriginalObj.setMsg("网络异常");
									break;
							}
							Log.i("原始数据上传：", upOriginalObj.getMsg());
							Log.i("orgResult", result + "org");
						}
					}
				});
		return upOriginalObj;
	}
}
