package rwcjom.awit.com.rwcjo_m.interfaces;

import android.content.Context;

import com.mor.dataacquisition.struct.BClass;

import rwcjom.awit.com.rwcjo_m.bean.CJUpOriginal;

/**
 * 原始观测数据上传API接口
 * @author Administrator
 *
 */
public interface CJUpOriginalInterface {
	public CJUpOriginal getCJUpOriginal(BClass[] blist,
			String equipbrand,String instrumodel,String serialnum,String sjid,String temperature,
			String barometric,String weather,String benchmarkids,String mtype,
			String mdate,String linecode,String account,
						String pwd,Context context);
}
