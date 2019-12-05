package medicalpatient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cristian.sistemademonitoreo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.achartengine.GraphicalView;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import medicalpatient.graphicecg.LineGraph;
import medicalpatient.model.LocalDataBase;

public class Electrocardiograma extends AppCompatActivity {

    private static GraphicalView view;
    private LineGraph line = new LineGraph();
    private Button btnIniciarElectro;
    private Button btnDetenerElectro;
    private Button btnFirebase;
    private ArrayList<Double> valoresElectro;
    private Boolean banderaElectro;
    private DatagramSocket datagramSocket;
    private Context me;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrocardiograma);


        LinearLayout linearLayout = findViewById(R.id.graficaECG);
        view = line.getView(this);
        linearLayout.addView(view);

        firebaseDatabase = FirebaseDatabase.getInstance();


        me = this;

        btnIniciarElectro = findViewById(R.id.btnIniciarElectro);
        btnDetenerElectro = findViewById(R.id.btnDetenerElectro);
        btnFirebase = findViewById(R.id.btnBsse);
        btnDetenerElectro.setEnabled(false);
        banderaElectro = true;
        valoresElectro = new ArrayList<>();

        try {
            datagramSocket = new DatagramSocket(55555, InetAddress.getByName("192.168.1.61"));
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
                btnFirebase.setEnabled(false);
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
                btnFirebase.setEnabled(true);


            }
        });


        btnFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendario = Calendar.getInstance();

                int intHora, intMinutos, intSegundos,intAño,intMes,intDia;

                intHora =calendario.get(Calendar.HOUR_OF_DAY);
                intMinutos = calendario.get(Calendar.MINUTE);
                intSegundos = calendario.get(Calendar.SECOND);
                intAño = calendario.get(Calendar.YEAR) ;
                intMes = calendario.get(Calendar.MONTH) + 1;
                intDia = calendario.get(Calendar.DAY_OF_MONTH);






                String segundos, minutos,hora;

                if (intHora>0 && intHora<10){
                    hora= "0"+intHora;
                }else{

                    hora= ""+intHora;
                }

                if (intMinutos>0 && intMinutos<10){
                    minutos= "0"+intMinutos;
                }else{

                    minutos= ""+intMinutos;
                }
                if (intSegundos>0 && intSegundos<10){
                    segundos= "0"+intSegundos;
                }else{

                    segundos= ""+intSegundos;
                }


                String tiempo = (hora + ":" + minutos + ":" + segundos);

                DatabaseReference databaseReference = firebaseDatabase.getReference();
                DatabaseReference databaseReference1 = firebaseDatabase.getReference();

                databaseReference.child("pacientes").child(LocalDataBase.getInstance(null).getUser().getId()).child("monitoreo").child("ecg").child(""+intAño).child(""+intMes)
                        .child(""+intDia).child(tiempo).setValue(valoresElectro);
                databaseReference.child("pacientes").child(LocalDataBase.getInstance(null).getUser().getId()).child("monitoreo").
                        child("ecg").child("tomas").child("0").child("fecha").setValue(""+intAño+"/"+intMes+"/"+intDia+"/"+tiempo);

            }
        });


    }


    private class SimpleTask extends AsyncTask<String, Integer, Void> {


        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(String... params) {

            String acumulado = "";

            try {

                double valores=0;
                while (banderaElectro){


                    byte[] bufer = new byte[4];


                // Construimos el DatagramPacket para recibir peticiones
                DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);

                // Leemos una petición del DatagramSocket

                datagramSocket.receive(peticion);

                String valor = new String(peticion.getData());
                valor = valor.replaceAll("\\u0000", "");



                double y = Integer.parseInt(valor);
                    valoresElectro.add(y);

                System.out.println(valor + "-"+ valores + "," + y );



                line.addCoordenada(valores,y);

                view.repaint();
                /*    ((AppCompatActivity)me).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/

                //

                    valores++;
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




