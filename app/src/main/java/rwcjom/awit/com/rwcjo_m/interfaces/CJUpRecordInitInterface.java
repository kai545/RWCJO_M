package rwcjom.awit.com.rwcjo_m.interfaces;

import android.content.Context;

import com.mor.dataacquisition.net.dataCallBacks.CJUpRecordInitDataCallBack;
import com.mor.dataacquisition.struct.BClass;

import rwcjom.awit.com.rwcjo_m.bean.CJUpRecordInit;

/**
 * Created by Administrator on 15-4-28.
 */
public interface CJUpRecordInitInterface {
    public CJUpRecordInit getCJUpRecordInit(final String account,final Context context,final CJUpRecordInitDataCallBack callBack);
}
