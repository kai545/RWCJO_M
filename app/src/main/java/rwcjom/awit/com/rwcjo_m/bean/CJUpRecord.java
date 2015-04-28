package rwcjom.awit.com.rwcjo_m.bean;

/**
 * Created by Administrator on 15-4-28.
 */
public class CJUpRecord {
    private Integer result;
    private Integer Flag;
    private String  msg;

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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
