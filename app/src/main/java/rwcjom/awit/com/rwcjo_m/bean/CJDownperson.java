package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

import rwcjom.awit.com.rwcjo_m.dao.PersonInfo;

/**
 * 下载司镜(内业)人员接口
 * @author Administrator
 *
 */
public class CJDownperson {
	private List<PersonInfo> personInfoList;
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

	public List<PersonInfo> getPersonInfoList() {
		return personInfoList;
	}

	public void setPersonInfoList(List<PersonInfo> personInfoList) {
		this.personInfoList = personInfoList;
	}
}
