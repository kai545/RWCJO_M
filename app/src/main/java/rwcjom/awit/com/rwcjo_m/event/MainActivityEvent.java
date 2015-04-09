package rwcjom.awit.com.rwcjo_m.event;

/**
 * Created by Fantasy on 15/4/9.
 */
public class MainActivityEvent {
    private String title;
    private boolean progressBarState=false;

    public MainActivityEvent(String title){
        this.title=title;
    }

    public MainActivityEvent(boolean progressBarState){
        this.progressBarState=progressBarState;
    }


    public boolean isProgressBarState() {
        return progressBarState;
    }

    public String getTitle() {
        return title;
    }
}
