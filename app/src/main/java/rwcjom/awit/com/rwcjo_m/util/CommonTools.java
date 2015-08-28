package rwcjom.awit.com.rwcjo_m.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.math.BigDecimal;
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

    public  static MaterialDialog showProgressDialog(Context cxt,String msg){
        return new MaterialDialog.Builder(cxt).theme(Theme.LIGHT)
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

    public static void showStringMsgDialog(Context context,String msg,String yesString){
        new MaterialDialog.Builder(context).theme(Theme.LIGHT)
                .title(R.string.login_dialog_title)
                .titleColor(R.color.syscolor)
                .content(msg)
                .positiveText(yesString)
                .negativeText(R.string.createline_cancel_btn)
                .show();
    }

    public static int indexOfArray(String[] array,String value){
        if (array!=null && value!=null){
            for (int i = 0; i < array.length; i++) {
                if (value.equals(array[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }


    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2) {
        return div(v1,v2,10);
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String getZpntCode(Context context,String lc){
        SharedPreferences mSettinsSP=context.getSharedPreferences(ValueConfig.SHAREPREFERENCE_XML_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor sharePreferenceEditor=mSettinsSP.edit();
        int code=mSettinsSP.getInt(lc+"_zpntcode",-1);
        if (code==-1){//初次设转点
            code=1;
        }else{
            code++;
        }
        sharePreferenceEditor.putInt(lc+"_zpntcode",code);
        sharePreferenceEditor.commit();
        return "Z"+(100000+code);
    }
}
