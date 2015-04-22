package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.dao.BasePntInfo;

/**
 * 下载工作基点信息接口
 * @author Administrator
 *
 */
public class CJDownbasepnt {
	private List<BasePntInfo> basePntInfoList;
	private Integer Flag;
	private String msg;

	public List<BasePntInfo> getBasePntInfoList() {
		return basePntInfoList;
	}

	public void setBasePntInfoList(List<BasePntInfo> basePntInfoList) {
		this.basePntInfoList = basePntInfoList;
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
