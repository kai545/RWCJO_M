package rwcjom.awit.com.rwcjo_m.interfaces;

import android.content.Context;

import com.mor.dataacquisition.struct.AClass;

/**
 * 上传测点成果API接口
 * @author Administrator
 *
 */
public interface CJUpResultInterface {
	public Integer getCJUpResult(AClass[] alist,String sdata,
			String sjid,String nyid,String remark,String account,String pwd,Context context);
}
