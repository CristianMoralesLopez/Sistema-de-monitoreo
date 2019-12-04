package medicalpatient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cristian.sistemademonitoreo.R;

import medicalpatient.graphicecg.graficaECG;

public class MainActivity extends AppCompatActivity {


    private Button btnScan;
    private Button btnElectrocardiograma;
    private Button btnECG;
    private Button btnPulso;

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnescanear);
        btnElectrocardiograma = findViewById(R.id.btnElectro);
        btnECG = findViewById(R.id.btnECG);
        btnPulso = findViewById(R.id.btnActivityPulso);



        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
// displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,EscaneoBluetooh.class);

                startActivity(i);
            }
        });

        btnElectrocardiograma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Electrocardiograma.class);

                startActivity(i);
            }
        });


        btnECG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, graficaECG.class);
                startActivity(i);
            }
        });

        btnPulso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PulsoService.class);
                startActivity(i);
            }
        });


    }
}
