package rwcjom.awit.com.rwcjo_m.util;

import android.content.Context;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Fantasy on 15/4/10.
 */
public class CommonTools {

    private static Toast mToast;

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
}
