package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.content.Context;
import android.util.Log;

import com.mor.dataacquisition.DataAcquisition;
import com.mor.dataacquisition.net.dataCallBacks.CJUpRecordInitDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;

import rwcjom.awit.com.rwcjo_m.bean.CJUpRecordInit;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;
import rwcjom.awit.com.rwcjo_m.interfaces.CJUpRecordInitInterface;

/**
 * Created by Administrator on 15-4-28.
 */
public class CJUpRecordInitImpl implements CJUpRecordInitInterface {
    private String TAG="CJUpRecordInitImpl";
//    private CJUpRecordInit cjupRecordInitObj;
    private Integer initresult=-5;
    @Override
    public Integer getCJUpRecordInit(final String account,final Context context,final CJUpRecordInitDataCallBack callBack) {
        //cjupRecordInitObj=new CJUpRecordInit();
        Log.i("come on", "jajajaaj");
        DataAcquisition.getInstance().CjUpRecordInit(account, context, new CJUpRecordInitDataCallBack() {
            @Override
            public void processData(CJResutResult data) {
                Log.i("result:", initresult + "121");
                super.processData(data);
                initresult = data.returnCode;
                Log.i("result:", initresult + "");
                if (initresult == 0) {
                    Log.i("进来没？？？", "进来了 哈哈");
                    pubUtil.upRecordInit.setResult(initresult);
                    pubUtil.upRecordInit.setFlag(0);
                } else if (initresult == -1) {
                    pubUtil.upRecordInit.setFlag(-1);
                    pubUtil.upRecordInit.setMsg("其他错误");
                } else if (initresult == -2) {
                    pubUtil.upRecordInit.setFlag(-1);
                    pubUtil.upRecordInit.setMsg("account有误");
                }
                Log.i("init:", pubUtil.upRecordInit.getResult() + "");
            }
        });
        pubUtil.upRecordInit.setResult(initresult);
        Log.i("end test",pubUtil.upRecordInit.getResult()+"end");
 //       return  cjupRecordInitObj;
        return initresult;
    }
}
