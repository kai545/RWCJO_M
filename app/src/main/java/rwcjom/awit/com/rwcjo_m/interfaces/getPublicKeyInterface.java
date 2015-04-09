package rwcjom.awit.com.rwcjo_m.interfaces;
/**
 * 请求服务器加密公钥接口
 * @author Administrator
 *
 */
public interface getPublicKeyInterface {
	
	public String getPublicKey(String account,String mac);
}
