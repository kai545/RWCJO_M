package rwcjom.awit.com.rwcjo_m.interfaces;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.bean.CJDownfaceinfo;

/**
 * 下载沉降观测断面详细信息接口
 * @author Administrator
 *
 */
public interface CJDownfaceinfoInterface {
	public CJDownfaceinfo getCJDownfaceinfo(String siteid,String faceid,String randomcode);
}
