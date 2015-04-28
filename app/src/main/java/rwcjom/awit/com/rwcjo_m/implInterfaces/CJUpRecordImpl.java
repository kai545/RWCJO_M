package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.content.Context;

import com.mor.dataacquisition.DataAcquisition;
import com.mor.dataacquisition.net.dataCallBacks.CJUpRecordInitDataCallBack;

import java.security.Principal;

import rwcjom.awit.com.rwcjo_m.bean.CJUpRecord;
import rwcjom.awit.com.rwcjo_m.interfaces.CJUpRecordInterface;

/**
 * Created by Administrator on 15-4-28.
 */
public class CJUpRecordImpl implements CJUpRecordInterface {
    private String TAG="CJUpRecordImpl";
    private CJUpRecord cjupRecordObj;
    private Integer result;
    @Override
    public CJUpRecord getCJUpRecord(String bffb, String bfpcode, String bfpl, String bfpvalue, Context context) {
        cjupRecordObj=new CJUpRecord();

        return cjupRecordObj;
    }
}
