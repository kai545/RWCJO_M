package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.content.Context;
import android.util.Log;

import com.mor.dataacquisition.DataAcquisition;
import com.mor.dataacquisition.net.dataCallBacks.CJUpRecordInitDataCallBack;
import com.mor.dataacquisition.net.parsedData.CJResutResult;

import rwcjom.awit.com.rwcjo_m.bean.CJUpRecordInit;
import rwcjom.awit.com.rwcjo_m.interfaces.CJUpRecordInitInterface;

/**
 * Created by Administrator on 15-4-28.
 */
public class CJUpRecordInitImpl implements CJUpRecordInitInterface {
    private String TAG="CJUpRecordInitImpl";
    private CJUpRecordInit cjupRecordInitObj;
    private Integer result;
    @Override
    public CJUpRecordInit getCJUpRecordInit(String account, Context context,CJUpRecordInitDataCallBack callBack) {
        cjupRecordInitObj=new CJUpRecordInit();
        Log.i("come on","jajajaaj");
        DataAcquisition.getInstance().CjUpRecordInit(account,context,new CJUpRecordInitDataCallBack(){
            @Override
            public void processData(CJResutResult data) {
                super.processData(data);
                result=data.returnCode;
                Log.i("result:",result+"");
                if(result==0){
                    cjupRecordInitObj.setResult(result);
                    cjupRecordInitObj.setFlag(0);
                }else if(result==-1){
                    cjupRecordInitObj.setFlag(-1);
                    cjupRecordInitObj.setMsg("其他错误");
                }else if(result==-2){
                    cjupRecordInitObj.setFlag(-1);
                    cjupRecordInitObj.setMsg("account有误");
                }
                Log.i("init:",cjupRecordInitObj.getResult()+"");
            }
        });
        return cjupRecordInitObj;
    }
}
