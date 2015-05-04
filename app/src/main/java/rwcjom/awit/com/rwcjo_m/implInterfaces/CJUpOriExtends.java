package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import com.mor.dataacquisition.net.DataCallBack;
import com.mor.dataacquisition.net.dataCallBacks.CJUpOriginalDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;

/**
 * Created by Administrator on 15-5-4.
 */
public class CJUpOriExtends extends CJUpOriginalDataCallBack {
    private DataCallBack dataCallBack;
    public CJUpOriExtends(DataCallBack dataCallBack) {
        this.dataCallBack=dataCallBack;
    }

    @Override
    public void processData(CJResutResult data) {
        super.processData(data);
        Log.i("original result",data.returnCode+"");
        dataCallBack.processData(data);
    }
}
