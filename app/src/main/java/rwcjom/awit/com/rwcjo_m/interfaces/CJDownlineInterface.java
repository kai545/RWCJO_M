package rwcjom.awit.com.rwcjo_m.interfaces;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.bean.CJDownline;


/**
 * 下载测量水准线路信息接口
 * @author Administrator
 *
 */
public interface CJDownlineInterface {
	public List<CJDownline> getCJDownline(String sectid,String startdate,String enddate,String randomcode);
}
