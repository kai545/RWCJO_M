package rwcjom.awit.com.rwcjo_m.interfaces;


import rwcjom.awit.com.rwcjo_m.bean.CJDownsectsite;


import java.util.List;

/**
 * 下载标段和工点信息接口
 * @author Administrator
 *
 */
public interface CJDownsectsiteInterface {
	public List<CJDownsectsite> getCJDownsectsite(String sectid,String sitetype,String randomcode);
}
