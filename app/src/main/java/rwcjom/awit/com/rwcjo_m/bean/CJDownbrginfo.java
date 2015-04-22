package rwcjom.awit.com.rwcjo_m.bean;

import rwcjom.awit.com.rwcjo_m.dao.BrgInfo;

/**
 * 下载梁体徐变详细信息接口
 * @author Administrator
 *
 */
public class CJDownbrginfo {
	private BrgInfo brginfoObj;
	private String msg;
	private Integer Flag;

	public BrgInfo getBrginfoObj() {
		return brginfoObj;
	}

	public void setBrginfoObj(BrgInfo brginfoObj) {
		this.brginfoObj = brginfoObj;
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
