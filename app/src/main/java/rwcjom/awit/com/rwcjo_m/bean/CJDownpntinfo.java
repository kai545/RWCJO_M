package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.dao.PntInfo;

/**
 * 下载沉降(徐变)观测点信息接口
 * @author Administrator
 *
 */
public class CJDownpntinfo {
	private List<PntInfo> pntInfoList ;
	private String msg;
	private Integer Flag;

	public List<PntInfo> getPntInfoList() {
		return pntInfoList;
	}

	public void setPntInfoList(List<PntInfo> pntInfoList) {
		this.pntInfoList = pntInfoList;
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
