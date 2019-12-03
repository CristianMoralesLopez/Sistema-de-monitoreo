package medicalpatient;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.cristian.sistemademonitoreo.R;

import java.util.List;

import static android.widget.Toast.makeText;

public class EscaneoBluetooh extends AppCompatActivity {


    /* VARIABLE PARA ESTABLECER EL TIEMPO DE ESCANEO DE DISPOSITIVOS*/

    private static final long SCAN_PERIOD = 1000;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;


    private ListView listView;
    private List<BluetoothDevice> mDevices;
    private LeDeviceListAdapter leDeviceListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private boolean mScanning;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaneo_bluetooh);
        listView = findViewById(R.id.bluetoothdevice);
        leDeviceListAdapter = new LeDeviceListAdapter(this);
        listView.setAdapter(leDeviceListAdapter);
        handler = new Handler();
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }

        scanLeDevice(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                BluetoothDevice device = (BluetoothDevice) leDeviceListAdapter.getItem(i);

                BluetoothGatt  dev= device.connectGatt(getApplicationContext(), true, new BluetoothGattCallback() {
                    @Override
                    public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                        super.onPhyUpdate(gatt, txPhy, rxPhy, status);
                    }
                });


            }
        });




    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothAdapter.stopLeScan(leScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            bluetoothAdapter.startLeScan(leScanCallback);
        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(leScanCallback);
        }
            }


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback leScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi,
                                     byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(!leDeviceListAdapter.contains(device) && device.getName()!=null) {


                                leDeviceListAdapter.addDevice(device);
                                leDeviceListAdapter.notifyDataSetChanged();
                                System.out.println(leDeviceListAdapter.getCount());
                                System.out.println(device.getAddress());

                            }
                        }
                    });
                }
            };








}
