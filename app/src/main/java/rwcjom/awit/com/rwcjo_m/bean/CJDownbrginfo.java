package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.dao.BrgInfo;

/**
 * 下载梁体徐变详细信息接口
 * @author Administrator
 *
 */
public class CJDownbrginfo {
	private List<BrgInfo> brgInfoList;
	private String msg;
	private Integer Flag;

	public List<BrgInfo> getBrgInfoList() {
		return brgInfoList;
	}

	public void setBrgInfoList(List<BrgInfo> brgInfoList) {
		this.brgInfoList = brgInfoList;
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
