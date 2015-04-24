package rwcjom.awit.com.rwcjo_m.bean;


import java.util.List;

import rwcjom.awit.com.rwcjo_m.dao.FaceInfo;

/**
 * 下载沉降观测断面详细信息接口
 * 
 * @author Administrator
 * 
 */
public class CJDownfaceinfo {
	private List<FaceInfo> faceinfolist;
	private String msg;
	private Integer Flag;

	public List<FaceInfo> getFaceinfolist() {
		return faceinfolist;
	}

	public void setFaceinfolist(List<FaceInfo> faceinfolist) {
		this.faceinfolist = faceinfolist;
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
