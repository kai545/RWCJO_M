package rwcjom.awit.com.rwcjo_m.bean;

import java.util.List;

/**
 * 下载测量水准线路信息接口
 * @author Administrator
 *
 */
public class CJDownline {
	public String lc;
	public String ln;
	public List<Bw> bw;
	public String getLc() {
		return lc;
	}
	public void setLc(String lc) {
		this.lc = lc;
	}
	public String getLn() {
		return ln;
	}
	public void setLn(String ln) {
		this.ln = ln;
	}
	public List<Bw> getBw() {
		return bw;
	}
	public void setBw(List<Bw> bw) {
		this.bw = bw;
	}
}
