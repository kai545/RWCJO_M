package rwcjom.awit.com.rwcjo_m.interfaces;

public interface HTTPResultListener {
	public void onStartRequest();
	public void onResult(Object obj);
	public void onFinish();
}
