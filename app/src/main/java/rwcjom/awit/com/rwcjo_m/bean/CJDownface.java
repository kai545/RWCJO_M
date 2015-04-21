package rwcjom.awit.com.rwcjo_m.bean;
/**
 * 下载断面(梁体)基础信息接口
 * @author Administrator
 *
 */
public class CJDownface {
	private FaceNews faceObj;
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

	public FaceNews getFaceObj() {
		return faceObj;
	}

	public void setFaceObj(FaceNews faceObj) {
		this.faceObj = faceObj;
	}
}
