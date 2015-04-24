package rwcjom.awit.com.rwcjo_m.interfaces;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.bean.CJDownbrginfo;

/**
 * 下载梁体徐变详细信息接口
 * @author Administrator
 *
 */
public interface CJDownbrginfoInterface {
	public CJDownbrginfo getCJDownbrginfo(String siteid,String faceid,String randomcode);
}
