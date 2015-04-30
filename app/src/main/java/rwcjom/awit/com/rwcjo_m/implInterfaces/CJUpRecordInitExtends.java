package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import com.mor.dataacquisition.net.dataCallBacks.CJUpRecordInitDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;

/**
 * Created by Administrator on 15-4-30.
 */
public class CJUpRecordInitExtends extends CJUpRecordInitDataCallBack{
    CJResutResult data1;
    public CJUpRecordInitExtends() {
        super();
    }

    @Override
    public void processData(CJResutResult data) {
        super.processData(data);
        data1=data;
        Log.i("result", data1.returnCode + "");
    }
}
