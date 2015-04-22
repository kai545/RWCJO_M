package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.dao.SecNews;
import rwcjom.awit.com.rwcjo_m.dao.SiteNews;


/**
 * 下载标段和工点信息接口
 * @author Administrator
 *
 */
public class CJDownsectsite {
	private SecNews secObj;
	private List<SiteNews> sitelist;
	private Integer Flag;
	private String msg;

	public SecNews getSecObj() {
		return secObj;
	}

	public void setSecObj(SecNews secObj) {
		this.secObj = secObj;
	}

	public List<SiteNews> getSitelist() {
		return sitelist;
	}

	public void setSitelist(List<SiteNews> sitelist) {
		this.sitelist = sitelist;
	}

	public Integer getFlag() {
		return Flag;
	}

	public void setFlag(Integer flag) {
		Flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
