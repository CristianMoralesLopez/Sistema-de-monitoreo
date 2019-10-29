package com.cristian.sistemademonitoreo;

import android.content.Context;
import android.os.AsyncTask;
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
    private Context me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrocardiograma);

        txtValores = findViewById(R.id.valores);
        me = this;

        btnIniciarElectro = findViewById(R.id.btnIniciarElectro);
        btnDetenerElectro = findViewById(R.id.btnDetenerElectro);
        btnDetenerElectro.setEnabled(false);
        banderaElectro = true;
        valoresElectro = new ArrayList<>();
        try {
            datagramSocket = new DatagramSocket(55555, InetAddress.getByName("192.168.1.101"));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        btnIniciarElectro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banderaElectro = true;
                btnIniciarElectro.setEnabled(false);
                btnDetenerElectro.setEnabled(true);
                SimpleTask task = new SimpleTask();
                task.execute();
                task.cancel();
            }
        });


        btnDetenerElectro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banderaElectro= false;
                btnIniciarElectro.setEnabled(true);
                btnDetenerElectro.setEnabled(false);


            }
        });


    }


    private class SimpleTask extends AsyncTask<String, Integer, Void> {


        @Override
        protected Void doInBackground(String... params) {

            String acumulado = "";

            try {




                while (banderaElectro){
                    byte[] bufer = new byte[10];


                // Construimos el DatagramPacket para recibir peticiones
                DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);

                // Leemos una petición del DatagramSocket

                datagramSocket.receive(peticion);

                String valor = new String(peticion.getData());

                System.out.println(valor);

                acumulado += valor + "\n";

                valoresElectro.add(valor);
                final String acum = acumulado;

                    ((AppCompatActivity)me).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtValores.setText(acum);
                    }
                });

                //
            }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        public void cancel() {

            banderaElectro=true;
        }
    }

        }




