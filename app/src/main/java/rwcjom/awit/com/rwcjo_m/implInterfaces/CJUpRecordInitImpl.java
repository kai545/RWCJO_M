package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.content.Context;
import android.util.Log;

import com.mor.dataacquisition.DataAcquisition;
import com.mor.dataacquisition.net.dataCallBacks.CJUpRecordInitDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;

import rwcjom.awit.com.rwcjo_m.bean.CJUpOriginal;
import rwcjom.awit.com.rwcjo_m.bean.CJUpRecordInit;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJUpRecordInitInterface;

/**
 * Created by Administrator on 15-4-28.
 */
public class CJUpRecordInitImpl implements CJUpRecordInitInterface {
    private String TAG="CJUpRecordInitImpl";
    private CJUpRecordInit upRecordInitObj;
    private Integer initresult=-5;
    @Override
    public CJUpRecordInit getCJUpRecordInit(final String account,final Context context,final CJUpRecordInitDataCallBack callBack) {
        DataAcquisition.getInstance().CjUpRecordInit(account, context, new CJUpRecordInitDataCallBack() {
            @Override
            public void processData(CJResutResult data) {
                upRecordInitObj=new CJUpRecordInit();
                Log.i("result:", initresult + "121");
                super.processData(data);
                initresult = data.returnCode;
                Log.i("result:", initresult + "");
                if (initresult == 0) {
                    Log.i("进来没？？？", "进来了 哈哈");
                    upRecordInitObj.setResult(initresult);
                    upRecordInitObj.setFlag(0);
                } else if (initresult == -1) {
                    upRecordInitObj.setFlag(-1);
                    upRecordInitObj.setMsg("其他错误");
                } else if (initresult == -2) {
                    upRecordInitObj.setFlag(-1);
                    upRecordInitObj.setMsg("account有误");
                }
                Log.i("init:", upRecordInitObj.getResult() + "");
            }
        });
//        upRecordInitObj.setResult(initresult);
//        Log.i("end test",initresult+"end");
        return upRecordInitObj;
    }
}
