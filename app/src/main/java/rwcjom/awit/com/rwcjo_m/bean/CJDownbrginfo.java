package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.dao.BrgInfo;

/**
 * 下载梁体徐变详细信息接口
 * @author Administrator
 *
 */
public class CJDownbrginfo {
	private BrgInfo brgInfoObj;
	private String msg;
	private Integer Flag;

	public BrgInfo getBrgInfoObj() {
		return brgInfoObj;
	}

	public void setBrgInfoObj(BrgInfo brgInfoObj) {
		this.brgInfoObj = brgInfoObj;
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
