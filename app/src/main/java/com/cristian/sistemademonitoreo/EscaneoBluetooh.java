package com.cristian.sistemademonitoreo;

import android.Manifest;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class EscaneoBluetooh extends AppCompatActivity {


    /* VARIABLE PARA ESTABLECER EL TIEMPO DE ESCANEO DE DISPOSITIVOS*/

    private static final long SCAN_PERIOD = 1000;

    private static final UUID HEART_RATE = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
    private static final UUID HEART_RATE_MEASURE = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    private static final UUID HEART_RATE_MEASURE_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private static final UUID HEART_RATE_MEASURE_CONTROLPOINT = UUID.fromString("00002a39-0000-1000-8000-00805f9b34fb");


    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;



    private ListView listView;
    private List<BluetoothDevice> mDevices;
    private LeDeviceListAdapter leDeviceListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private boolean mScanning;
    private Handler handler;
    private Button btnPulso;
    private TextView lblPulso;
    private BluetoothGatt dev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaneo_bluetooh);
        listView = findViewById(R.id.bluetoothdevice);
        leDeviceListAdapter = new LeDeviceListAdapter(this);
        btnPulso = findViewById(R.id.btnpulso);
        lblPulso = findViewById(R.id.lblPulso);
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

                dev= device.connectGatt(getApplicationContext(), true, callback);


            }
        });


        btnPulso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              UUID uuid = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");

              dev.discoverServices();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //BluetoothGattService service = dev.getService(uuid);

                BluetoothGattService serviceHeart = dev.getService(HEART_RATE);

                BluetoothGattCharacteristic characteristicHeart = serviceHeart.getCharacteristic(HEART_RATE_MEASURE);

                dev.setCharacteristicNotification(characteristicHeart,true);

                BluetoothGattDescriptor descriptor = characteristicHeart.getDescriptor(HEART_RATE_MEASURE_DESCRIPTOR);

                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);

                dev.writeDescriptor(descriptor);



                List<BluetoothGattDescriptor> lista = characteristicHeart.getDescriptors();

                String acumulado = "";

                for (int i = 0; i<lista.size();i++){
                    acumulado += lista.get(i).getUuid().toString()+"\n";
                }

              if (lista!= null){
                  lblPulso.setText(""+ acumulado);

                  System.out.print(acumulado);

                  dev.readCharacteristic(characteristicHeart);
             }


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

    private BluetoothGattCallback callback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);


            System.out.println("1 DESCUBRI SERVICIOS");

            dev.discoverServices();
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);

            System.out.println("2 HABILITE NOTIFICACIONES");
            BluetoothGattCharacteristic characteristic = dev.getService(HEART_RATE).getCharacteristic(HEART_RATE_MEASURE);

            dev.setCharacteristicNotification(characteristic,true);

            BluetoothGattDescriptor descriptor = dev.getService(HEART_RATE).getCharacteristic(HEART_RATE_MEASURE).getDescriptor(HEART_RATE_MEASURE_DESCRIPTOR);

            descriptor.setValue(
                    BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            gatt.writeDescriptor(descriptor);


        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);


            final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
            final byte[] data = characteristic.getValue();
            int length = data.length;

            final char[] out = new char[length * 3 - 1];
            for(int j = 0; j < length; j++) {
                int v = data[j] & 0xFF;
                out[j * 3] = HEX_ARRAY[v >>> 4];
                out[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
                if(j != length - 1)
                    out[j * 3 + 2] = '-';
            }
            String nuevo =  new String(out);





            System.out.println(nuevo);




        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);

            System.out.println("llego alas notificaciones");

            System.out.println(characteristic.getValue());

        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);


            BluetoothGattCharacteristic characteristic = dev.getService(HEART_RATE).getCharacteristic(HEART_RATE_MEASURE_CONTROLPOINT);

            System.out.println("3 HABILITAR CONTROL POINT");

            characteristic.setValue(new byte[]{1, 1});
            gatt.writeCharacteristic(characteristic);

        }
    };








}
