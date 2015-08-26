package rwcjom.awit.com.rwcjo_m.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.util.ArrayList;
import java.util.List;

import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;
import rwcjom.awit.com.rwcjo_m.R;
import rwcjom.awit.com.rwcjo_m.adapter.BluetoothDevicesAdapter;
import rwcjom.awit.com.rwcjo_m.util.CommonTools;

/**
 * Created by Fantasy on 15/7/9.
 */
public class BluetoothActivity extends AppCompatActivity {
    public static final int ENABLE_BT__REQUEST = 1;

    private SmoothBluetooth mSmoothBluetooth;

    private MaterialDialog progressDialog;

    private Button mScanButton;

    private TextView mStateTv;

    private TextView mDeviceTv;

    private Button mPairedButton;

    private Button mDisconnectButton;

    private LinearLayout mConnectionLayout;

    private EditText mMessageInput;

    private Button mSendButton;

    private CheckBox mCRLFBox;

    private List<Integer> mBuffer = new ArrayList<>();
    private List<String> mResponseBuffer = new ArrayList<>();

    private ArrayAdapter<String> mResponsesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bluetooth);

        mSmoothBluetooth = new SmoothBluetooth(this);

        mSmoothBluetooth.setListener(mListener);

        ListView responseListView = (ListView) findViewById(R.id.responses);
        mResponsesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mResponseBuffer);
        responseListView.setAdapter(mResponsesAdapter);

        mCRLFBox = (CheckBox) findViewById(R.id.carrage);
        mMessageInput = (EditText) findViewById(R.id.message);

        mSendButton = (Button) findViewById(R.id.send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSmoothBluetooth.send(mMessageInput.getText().toString(), mCRLFBox.isChecked());
                mMessageInput.setText("");
            }
        });


        mDisconnectButton = (Button) findViewById(R.id.disconnect);
        mDisconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSmoothBluetooth.disconnect();
                mResponseBuffer.clear();
                mResponsesAdapter.notifyDataSetChanged();
            }
        });

        mConnectionLayout = (LinearLayout) findViewById(R.id.connection);

        mScanButton = (Button) findViewById(R.id.scan);
        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSmoothBluetooth.doDiscovery();
            }
        });

        mPairedButton = (Button) findViewById(R.id.paired);
        mPairedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSmoothBluetooth.tryConnection();
            }
        });
        mStateTv = (TextView) findViewById(R.id.state);
        mStateTv.setText("Disconnected");
        mDeviceTv = (TextView) findViewById(R.id.device);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmoothBluetooth.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENABLE_BT__REQUEST) {
            if (resultCode == RESULT_OK) {
                mSmoothBluetooth.tryConnection();
            }
        }
    }

    private SmoothBluetooth.Listener mListener = new SmoothBluetooth.Listener() {
        @Override
        public void onBluetoothNotSupported() {
            Toast.makeText(BluetoothActivity.this, "Bluetooth not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onBluetoothNotEnabled() {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, ENABLE_BT__REQUEST);
        }

        @Override
        public void onConnecting(Device device) {
            mStateTv.setText("Connecting to");
            mDeviceTv.setText(device.getName());
        }

        @Override
        public void onConnected(Device device) {
            mStateTv.setText("Connected to");
            mDeviceTv.setText(device.getName());
            mConnectionLayout.setVisibility(View.GONE);
            mDisconnectButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void onDisconnected() {
            mStateTv.setText("Disconnected");
            mDeviceTv.setText("");
            mDisconnectButton.setVisibility(View.GONE);
            mConnectionLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onConnectionFailed(Device device) {
            mStateTv.setText("Disconnected");
            mDeviceTv.setText("");
            Toast.makeText(BluetoothActivity.this, "Failed to connect to " + device.getName(), Toast.LENGTH_SHORT).show();
            if (device.isPaired()) {
                mSmoothBluetooth.doDiscovery();
            }
        }

        @Override
        public void onDiscoveryStarted() {
//            Toast.makeText(BluetoothActivity.this, "Searching", Toast.LENGTH_SHORT).show();
            progressDialog=CommonTools.showProgressDialog(BluetoothActivity.this,"正在查找设备...");
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        public void onDiscoveryFinished() {
            progressDialog.dismiss();
        }

        @Override
        public void onNoDevicesFound() {
            Toast.makeText(BluetoothActivity.this, "No device found", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDevicesFound(final List<Device> deviceList,
                                   final SmoothBluetooth.ConnectionCallback connectionCallback) {

            final MaterialDialog dialog = new MaterialDialog.Builder(BluetoothActivity.this)
                    .theme(Theme.LIGHT)
                    .title("蓝牙设备列表")
                    .titleColor(R.color.syscolor)
                    .adapter((ListAdapter) new BluetoothDevicesAdapter(BluetoothActivity.this, deviceList), new MaterialDialog.ListCallback() {
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
                StringBuilder sb = new StringBuilder();
                for (int integer : mBuffer) {
                    sb.append((char) integer);
                }
                mBuffer.clear();
                mResponseBuffer.add(0, sb.toString());
                CommonTools.showToast(BluetoothActivity.this, sb.toString());
                mResponsesAdapter.notifyDataSetChanged();
            }
        }
    };
}
