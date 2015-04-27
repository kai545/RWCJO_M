package rwcjom.awit.com.rwcjo_m.interfaces;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.bean.CJDownperson;

/**
 * 下载司镜(内业)人员接口
 * @author Administrator
 *
 */
public interface CJDownpersonInterface {
	public CJDownperson getCJDownperson(String sectid,String ptype,String randomcode);
}
