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
	private FaceInfo faceinfoObj;
	private String msg;
	private Integer Flag;

	public FaceInfo getFaceinfoObj() {
		return faceinfoObj;
	}

	public void setFaceinfoObj(FaceInfo faceinfoObj) {
		this.faceinfoObj = faceinfoObj;
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
