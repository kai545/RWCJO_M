package rwcjom.awit.com.rwcjo_m.interfaces;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.bean.CJDownface;

/**
 * 下载断面(梁体)基础信息接口
 * @author Administrator
 *
 */
public interface CJDownfaceInterface {
	public List<CJDownface> getCJDownface(String siteid,String startdate,String enddate,String randomcode);
}
