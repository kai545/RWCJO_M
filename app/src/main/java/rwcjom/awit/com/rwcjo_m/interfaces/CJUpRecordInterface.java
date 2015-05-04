package rwcjom.awit.com.rwcjo_m.interfaces;

import android.content.Context;

import rwcjom.awit.com.rwcjo_m.bean.CJUpRecord;

/**
 * Created by Administrator on 15-4-28.
 */
public interface CJUpRecordInterface {
    public CJUpRecord getCJUpRecord(String bffb, String bfpcode, String bfpl, String bfpvalue, Context context);
}
