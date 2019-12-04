package com.cristian.sistemademonitoreo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class PulsoService extends AppCompatActivity {

    private final static String IP = "192.168.0.103";
    private final static int PUERTO1 = 55555;
    private final static int PUERTO2 = 55556;


    private EditText txtPulso;
    private EditText txtEcg;
    private Button btnPdg;
    private DatagramSocket datagramSocketPulso;
    private DatagramSocket datagramSocketECG;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulso_service);

        txtPulso = findViewById(R.id.txtPulso);
        txtEcg = findViewById(R.id.txtECG);
        btnPdg = findViewById(R.id.btnPDG);

        try {
            datagramSocketPulso = new DatagramSocket(PUERTO1, InetAddress.getByName(IP));
            datagramSocketECG = new DatagramSocket(PUERTO2,InetAddress.getByName(IP));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        btnPdg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HiloPulso pulso = new HiloPulso();
                HiloECG ecg = new HiloECG();

                pulso.execute();
                ecg.execute();
            }
        });

    }


    private class HiloPulso extends AsyncTask<String, String, Integer>{
        public HiloPulso (){

        }

        @Override
        protected Integer doInBackground(String... strings) {

            try{
            byte [] buBytes = new byte [4];

            DatagramPacket datagramPacket = new DatagramPacket(buBytes,buBytes.length);

            datagramSocketPulso.receive(datagramPacket);

                String valor = new String(datagramPacket.getData());
                valor = valor.replaceAll("\\u0000", "");

                publishProgress(valor);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtPulso.setText(values[0]);
        }
    }
    private class HiloECG extends AsyncTask<String, String, Integer>{
        public HiloECG (){

        }

        @Override
        protected Integer doInBackground(String... strings) {

            try{
                byte [] buBytes = new byte [4];

                DatagramPacket datagramPacket = new DatagramPacket(buBytes,buBytes.length);

                datagramSocketECG.receive(datagramPacket);

                String valor = new String(datagramPacket.getData());
                valor = valor.replaceAll("\\u0000", "");

                publishProgress(valor);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtEcg.setText(values[0]);
        }
    }



}



