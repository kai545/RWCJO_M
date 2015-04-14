package rwcjom.awit.com.rwcjo_m.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Map;
import java.util.Set;

import ICT.utils.RSACoder;

/**
 * Created by Fantasy on 15/4/10.
 */
public class CommonTools {

    private static Toast mToast;
    private static String preKey="AWITRWCJ";
    private static String endPwd="";
    private static String endKey="";
    private static String key="";
    private static String val="";
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

    public static SoapObject getObject(String methodNameString, Map<String ,String> paramsvalue){
        // 命名空间
        String nameSpace = ValueConfig.NAMESPACE_STRING;

        // 调用的方法名称
        String methodNames = methodNameString;
        // EndPoint
        String endPoint = ValueConfig.ENDPOINT_STRING;
        // SOAP Action
        String soapAction = ValueConfig.NAMESPACE_STRING+methodNameString;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodNames);
            java.util.Iterator iter = paramsvalue.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                key=entry.getKey().toString();
                val=entry.getValue().toString();
                Log.i("key",key);
                Log.i("Val", val);
                rpc.addProperty(key, val);
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
            if (envelope.getResponse()!=null)
            {
                Log.i("TAG", "Object is not null");
            }
        } catch (Exception e) {
//					handler.sendEmptyMessage(3);
        }
        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        Log.i("object",object.toString());
        return object;
    }

}
