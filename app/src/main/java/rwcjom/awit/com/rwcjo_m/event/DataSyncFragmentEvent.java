package rwcjom.awit.com.rwcjo_m.event;


/**
 * Created by Fantasy on 15/4/21.
 */
public class DataSyncFragmentEvent {
    private int circularProgressButtonProgress;
    public DataSyncFragmentEvent(int circularProgressButtonProgress){
        this.circularProgressButtonProgress=circularProgressButtonProgress;
    }

    public int getCircularProgressButtonProgress() {
        return circularProgressButtonProgress;
    }
}
