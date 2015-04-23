package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.dao.BwInfo;
import rwcjom.awit.com.rwcjo_m.dao.Line;

/**
 * 下载测量水准线路信息接口
 * @author Administrator
 *
 */
public class CJDownline {
	private Line lineObj;
	private List<BwInfo> bw;
	private String msg;
	private Integer Flag;

	public List<BwInfo> getBw() {
		return bw;
	}

	public void setBw(List<BwInfo> bw) {
		this.bw = bw;
	}

	public Integer getFlag() {
		return Flag;
	}

	public void setFlag(Integer flag) {
		Flag = flag;
	}

	public Line getLineObj() {
		return lineObj;
	}

	public void setLineObj(Line lineObj) {
		this.lineObj = lineObj;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
