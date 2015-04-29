package rwcjom.awit.com.rwcjo_m.implInterfaces;

import android.content.Context;
import android.util.Log;

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
        result=DataAcquisition.getInstance().CjUpRecord(bffb,bfpcode,bfpl,bfpvalue,context);
        Log.i(TAG,result+"1");
        if(result==0){
            cjupRecordObj.setFlag(0);
            cjupRecordObj.setResult(result);
        }else if(result==1){
            cjupRecordObj.setFlag(-1);
            cjupRecordObj.setMsg("bffb有误");
        }else if(result==2){
            cjupRecordObj.setFlag(-1);
            cjupRecordObj.setMsg("bfpcode有误");
        }else if(result==3){
            cjupRecordObj.setFlag(-1);
            cjupRecordObj.setMsg("bfpl有误");
        }else if(result==4){
            cjupRecordObj.setFlag(-1);
            cjupRecordObj.setMsg("bfpvalue有误");
        }else if(result==-1){
            cjupRecordObj.setFlag(-1);
            cjupRecordObj.setMsg("其他错误");
        }
        return cjupRecordObj;
    }
}
