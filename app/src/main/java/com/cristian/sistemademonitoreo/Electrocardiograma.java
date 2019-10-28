package com.cristian.sistemademonitoreo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Electrocardiograma extends AppCompatActivity {

    private TextView txtValores;
    private Button btnIniciarElectro;
    private Button btnDetenerElectro;
    private ArrayList<String> valoresElectro;
    private Boolean banderaElectro;
    private DatagramSocket datagramSocket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrocardiograma);

        txtValores = findViewById(R.id.valores);
        btnIniciarElectro = findViewById(R.id.btnIniciarElectro);
        btnDetenerElectro = findViewById(R.id.btnDetenerElectro);
        banderaElectro= true;
        valoresElectro = new ArrayList<>();




            btnIniciarElectro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                datagramSocket = new DatagramSocket(55555, InetAddress.getByName("192.168.0.100"));

                                byte[] bufer = new byte[10];

                                String acumulado = "";

                                while (true) {
                                    // Construimos el DatagramPacket para recibir peticiones
                                    DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);

                                    // Leemos una petición del DatagramSocket

                                    datagramSocket.receive(peticion);

                                    String valor = new String(peticion.getData());

                                    System.out.println(valor);

                                    acumulado += valor + "\n";

                                    valoresElectro.add(valor);

                                    txtValores.setText(acumulado);

                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });





                }
            });






    }
}
