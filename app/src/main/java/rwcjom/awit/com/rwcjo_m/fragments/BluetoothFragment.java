package rwcjom.awit.com.rwcjo_m.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import de.greenrobot.event.EventBus;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.event.MainActivityEvent;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;

/**
 * Created by Fantasy on 15/4/29.
 */
public class BluetoothFragment extends Fragment {
    private static final String TAG = BluetoothFragment.class.getSimpleName();
    private static Context context;
    private BluetoothSPP bt;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new MainActivityEvent("通讯设置"));
        bt = new BluetoothSPP(context);
        if(!bt.isBluetoothAvailable()) {
            Log.i(TAG,"蓝牙无效");
            CommonTools.showToast(context, "蓝牙无效");
        }else{
            if(!bt.isBluetoothEnabled()) {
                Log.i(TAG,"蓝牙已禁用");
                CommonTools.showToast(context,"蓝牙无效");
            } else {
                Log.i(TAG,"蓝牙已启用");
                CommonTools.showToast(context,"蓝牙无效");

            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_index,null);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                //setup();
            } else {
                // Do something if user doesn't choose any device (Pressed back)
            }
        }
    }
}
