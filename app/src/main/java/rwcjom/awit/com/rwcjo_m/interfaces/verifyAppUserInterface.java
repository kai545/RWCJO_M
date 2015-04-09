package rwcjom.awit.com.rwcjo_m.interfaces;
/**
 * 用户账号验证接口
 * @author Administrator
 *
 */
public interface verifyAppUserInterface {
	
	public String getVerifyAppUser(String account,String pwd,String mac,String deskey);
}
