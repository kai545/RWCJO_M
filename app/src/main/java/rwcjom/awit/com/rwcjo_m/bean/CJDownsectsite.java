package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

/**
 * 下载标段和工点信息接口
 * @author Administrator
 *
 */
public class CJDownsectsite {
	private sect  sectObj;
	private List<siteObject> sitelist;
	private Integer Flag;
	private String msg;
	public sect getSectObj() {
		return sectObj;
	}

	public void setSectObj(sect sectObj) {
		this.sectObj = sectObj;
	}

	public List<siteObject> getSitelist() {
		return sitelist;
	}

	public void setSitelist(List<siteObject> sitelist) {
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
