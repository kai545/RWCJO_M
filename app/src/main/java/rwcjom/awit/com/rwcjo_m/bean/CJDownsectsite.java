package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

/**
 * 下载标段和工点信息接口
 * @author Administrator
 *
 */
public class CJDownsectsite {
	private SectNews sectObj;
	private List<SiteNews> sitelist;
	private Integer Flag;
	private String msg;
	public SectNews getSectObj() {
		return sectObj;
	}

	public void setSectObj(SectNews sectObj) {
		this.sectObj = sectObj;
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
