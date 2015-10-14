package rwcjom.awit.com.rwcjo_m.listener;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.adapter.BluetoothDevicesAdapter;
import rwcjom.awit.com.rwcjo_m.event.BluetoothDataEvent;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;

/**
 * Created by Fantasy on 15/8/26.
 */
public class MeasureBluetoothListener implements SmoothBluetooth.Listener {
    public final String TAG="MeasureBluetoothListener";
    public static final int ENABLE_BT__REQUEST = 1;
    private Activity activity;
    private SmoothBluetooth mSmoothBluetooth;
    private MaterialDialog progressDialog;
    private List<Integer> mBuffer = new ArrayList<>();
    private List<String> mResponseBuffer = new ArrayList<>();

    public MeasureBluetoothListener(Activity activity,SmoothBluetooth mSmoothBluetooth){
        this.activity=activity;
        this.mSmoothBluetooth=mSmoothBluetooth;
    }

    @Override
    public void onBluetoothNotSupported() {
        Toast.makeText(activity, "Bluetooth not found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBluetoothNotEnabled() {
        Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBluetooth, ENABLE_BT__REQUEST);
    }

    @Override
    public void onConnecting(Device device) {
        progressDialog=CommonTools.showProgressDialog(activity,"正在连接中...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onConnected(Device device) {
        progressDialog.cancel();
        CommonTools.showToast(activity,"蓝牙设备"+device.getName()+"连接成功！");
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(Device device) {

        Toast.makeText(activity, "Failed to connect to " + device.getName(), Toast.LENGTH_SHORT).show();
        if (device.isPaired()) {
            mSmoothBluetooth.doDiscovery();
        }
    }

    @Override
    public void onDiscoveryStarted() {
//            Toast.makeText(BluetoothActivity.this, "Searching", Toast.LENGTH_SHORT).show();
        progressDialog= CommonTools.showProgressDialog(activity, "正在查找设备...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDiscoveryFinished() {
        progressDialog.dismiss();
    }

    @Override
    public void onNoDevicesFound() {
        Toast.makeText(activity, "No device found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDevicesFound(final List<Device> deviceList,
                               final SmoothBluetooth.ConnectionCallback connectionCallback) {

        final MaterialDialog dialog = new MaterialDialog.Builder(activity)
                .theme(Theme.LIGHT)
                .title("蓝牙设备列表")
                .titleColor(R.color.syscolor)
                .adapter((ListAdapter) new BluetoothDevicesAdapter(activity, deviceList), new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        connectionCallback.connectTo(deviceList.get(i));
                        materialDialog.dismiss();
                    }
                })
                .build();

            /*ListView listView = dialog.getListView();
            if (listView != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        connectionCallback.connectTo(deviceList.get(position));
                        dialog.dismiss();
                    }

                });
            }*/

        dialog.show();

    }

    @Override
    public void onDataReceived(int data) {
        //CommonTools.showToast(BluetoothActivity.this,data+"");
        mBuffer.add(data);
        if (data == 13 && !mBuffer.isEmpty()) {
            //if (data == 0x0D && !mBuffer.isEmpty() && mBuffer.get(mBuffer.size()-2) == 0xA0) {
            StringBuilder sb = new StringBuilder();//存储本次的字符串
            for (int integer : mBuffer) {
                sb.append((char) integer);
            }
            mBuffer.clear();
            //mResponseBuffer.add(0, sb.toString());


            //发送数据
            EventBus.getDefault().post(new BluetoothDataEvent(sb.toString()));
        }
    }
}
