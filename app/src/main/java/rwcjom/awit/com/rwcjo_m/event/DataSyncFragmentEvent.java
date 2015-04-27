package rwcjom.awit.com.rwcjo_m.event;


/**
 * Created by Fantasy on 15/4/21.
 */
public class DataSyncFragmentEvent {
    private int id;
    private int circularProgressButtonProgress;
    public DataSyncFragmentEvent(int id,int circularProgressButtonProgress){
        this.id=id;
        this.circularProgressButtonProgress=circularProgressButtonProgress;
    }

    public int getCircularProgressButtonProgress() {
        return circularProgressButtonProgress;
    }

    public int getId() {
        return id;
    }
}
