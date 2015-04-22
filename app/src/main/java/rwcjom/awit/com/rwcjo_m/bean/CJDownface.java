package rwcjom.awit.com.rwcjo_m.bean;


import java.util.List;
import rwcjom.awit.com.rwcjo_m.dao.FaceNews;

/**
 * 下载断面(梁体)基础信息接口
 * @author Administrator
 *
 */
public class CJDownface {
	private List<FaceNews> facelist;
	private String msg;
	private Integer Flag;

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

	public List<FaceNews> getFacelist() {
		return facelist;
	}

	public void setFacelist(List<FaceNews> facelist) {
		this.facelist = facelist;
	}
}
