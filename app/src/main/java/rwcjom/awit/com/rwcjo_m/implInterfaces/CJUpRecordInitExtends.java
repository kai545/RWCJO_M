package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import com.mor.dataacquisition.net.DataCallBack;
import com.mor.dataacquisition.net.dataCallBacks.CJUpRecordInitDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;

/**
 * Created by Administrator on 15-4-30.
 */
public class CJUpRecordInitExtends extends CJUpRecordInitDataCallBack{
    private DataCallBack dataCallBack;
    public CJUpRecordInitExtends(DataCallBack dataCallBack) {
        this.dataCallBack=dataCallBack;
    }

    @Override
    public void processData(CJResutResult data) {
        super.processData(data);
        Log.i("result:",data.returnCode+"");
        dataCallBack.processData(data);
    }

}
