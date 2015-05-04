package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.util.Log;

import com.mor.dataacquisition.net.DataCallBack;
import com.mor.dataacquisition.net.dataCallBacks.CJUpResultDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;

/**
 * Created by Administrator on 15-5-4.
 */
public class CJUpResultExtends extends CJUpResultDataCallBack{
    private DataCallBack dataCallBack;
    public CJUpResultExtends(DataCallBack dataCallBack){
        this.dataCallBack=dataCallBack;
    }

    @Override
    public void processData(CJResutResult data) {
        super.processData(data);
        Log.i("upresult result",data.returnCode+"");
        dataCallBack.processData(data);
    }
}
