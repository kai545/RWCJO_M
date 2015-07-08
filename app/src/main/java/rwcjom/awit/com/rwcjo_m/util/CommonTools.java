package rwcjom.awit.com.rwcjo_m.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.bean.pubUtil;

/**
 * Created by Fantasy on 15/4/10.
 */
public class CommonTools {

    private static Toast mToast;
    private static String preKey="AWITRWCJ";
    private static String endPwd="";
    private static String endKey="";
    private static String val="";
    private static String TAG="CommonTools";
    public static void showToast(Context cxt,String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(cxt, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public  static void showProgressDialog(Context cxt,String msg){
        new MaterialDialog.Builder(cxt)
                .content(msg)
                .progress(true, 0)
                .show();
    }

    public static SoapSerializationEnvelope getEnvelope(String methodNameString, Map<String ,String> paramsvalue) {
        // 命名空间
        String nameSpace = ValueConfig.NAMESPACE_STRING;

        // 调用的方法名称
        String methodNames = methodNameString;
        // EndPoint
        String endPoint = ValueConfig.ENDPOINT_STRING;
        // SOAP Action
        String soapAction = ValueConfig.NAMESPACE_STRING + methodNameString;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodNames);
        for (Iterator it =  paramsvalue.keySet().iterator();it.hasNext();)
        {
            Object key = it.next();
            val=paramsvalue.get(key);
            Log.i("key", key.toString());
            Log.i("Val", val);
            rpc.addProperty(key.toString(),val);
        }
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null) {
                Log.i("TAG", "Object is not null");
            }
        } catch(SoapFault e){
            e.printStackTrace();
            Log.i(TAG, "soap异常");
            pubUtil.exception.setExceptionMsg("soap异常");
        }catch(ClassCastException e){
            e.printStackTrace();
            Log.i(TAG, "造型异常");
            pubUtil.exception.setExceptionMsg("造型异常");
        }catch (NullPointerException e) {
            e.printStackTrace();
            Log.i(TAG, "空指针异常");
            pubUtil.exception.setExceptionMsg("空指针异常");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "网络异常");
            pubUtil.exception.setExceptionMsg("网络异常");
        }
        return envelope;
    }

    public static String getDateWith(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public static void showStringMsgDialog(Context context,String msg){
        new MaterialDialog.Builder(context).theme(Theme.LIGHT)
                .title(R.string.login_dialog_title)
                .titleColor(R.color.syscolor)
                .neutralText(msg)
                .show();
    }
}
