package medicalpatient.graphicecg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cristian.sistemademonitoreo.R;

import org.achartengine.GraphicalView;

import java.util.ArrayList;

import medicalpatient.Parametros.AgentPulso;
import medicalpatient.model.MonitorTake;
import medicalpatient.utils.DefaultCallback2;

public class GraficaMetas extends AppCompatActivity implements DefaultCallback2 {

    // Componentes actividad principal

    private TextView lblPasosAsignados;
    private TextView lblPasosLogrados;
    private TextView lblPasosFaltantes;

    private TextView lblKgCaloriasasignada;
    private TextView lblKgCaloriasLogradas;
    private TextView lblKgcaloriasFaltantes;

    private int intPasosAsignadas;
    private int intPasosLogrados;
    private int intPasosFaltantes;

    private int intKgcaloriasAsignadas;
    private int intKgcaloriasLogradas;
    private int intKgcaloriasFaltantes;

    private static GraphicalView viewPasos;
    private LineGraphBarPasos linePasos;
    private static GraphicalView viewKgCalorias;
    private LinearGraphBarKgCalorias lineKgCalorias;

    // componentes Resumen Actividad Fisica

    private static GraphicalView view;
    private static GraphicalView view1;
    private LineGraph line = new LineGraph();
    private LineGraph line1 = new LineGraph();
    private AgentPulso agentPulso;
    private TextView lblPulso1;
    private TextView lblPulso2;
    private TextView lblHoraInicio1;
    private TextView lblHoraInicio2;
    private TextView lblHoraFin1;
    private TextView lblHoraFin2;
    private TextView lblduracion1;
    private TextView lblduracion2;
    private TextView lblPulsoMinimo1;
    private TextView lblPulsoMinimo2;
    private TextView lblPulsoMaximo1;
    private TextView lblPulsoMaximo2;
    private TextView lblPasos;
    private TextView lblKgCalorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_metas);

        // inicializacion componentes interfaz locales

        lblPasosAsignados = findViewById(R.id.lblpasosAsignadas);
        lblPasosLogrados = findViewById(R.id.lblPasosLogradas);
        lblPasosFaltantes = findViewById(R.id.lblpasosFaltantes);

        lblKgCaloriasasignada = findViewById(R.id.lblKgcaloriasAsignada);
        lblKgCaloriasLogradas = findViewById(R.id.lblKgcaloriasLogradas);
        lblKgcaloriasFaltantes = findViewById(R.id.lblKgcaloriasFaltantes);

        lineKgCalorias = new LinearGraphBarKgCalorias();
        linePasos = new LineGraphBarPasos();

        LinearLayout linearLayout = findViewById(R.id.layoutKgcalorias);
        viewKgCalorias = lineKgCalorias.getView(this);
        linearLayout.addView(viewKgCalorias);


        LinearLayout linearLayout2 = findViewById(R.id.layoutPasos);
        viewPasos = linePasos.getView(this);
        linearLayout2.addView(viewPasos);

        // iniciacion componentes Resumen Actividad



        LinearLayout linearLayout3 = findViewById(R.id.TgraficaActividad);
        view = line.getView(this);
        linearLayout3.addView(view);


        LinearLayout linearLayout4 = findViewById(R.id.TgraficaActividad2);
        view1 = line1.getView(this);
        linearLayout4.addView(view1);

        lblPasos = findViewById(R.id.TlblPasos1);
        lblPulso1 = findViewById(R.id.TlblPromedioPulso1);
        lblPulso2 = findViewById(R.id.TlblPromedioPulso2);
        lblHoraInicio1 = findViewById(R.id.TlblHoraInicio1);
        lblHoraInicio2 = findViewById(R.id.TlblHoraInicio2);
        lblHoraFin1= findViewById(R.id.TlblHoraFin1);
        lblHoraFin2 = findViewById(R.id.TlblHoraFin2);
        lblduracion1 = findViewById(R.id.TlblDuracion1);
        lblduracion2 = findViewById(R.id.TlblDuracion2);
        lblPulsoMinimo1 = findViewById(R.id.TlblPulsoMinimo1);
        lblPulsoMinimo2 = findViewById(R.id.TlblPulsoMinimo2);
        lblPulsoMaximo1 = findViewById(R.id.TlblPulsoMaximo1);
        lblPulsoMaximo2 = findViewById(R.id.TlblPulsoMaximo2);
        lblKgCalorias = findViewById(R.id.Tlblkgcalorias);

        agentPulso = new AgentPulso();

        agentPulso.getDataMonitorDateHome("0",this);
        agentPulso.getMetas(this);
    }


    @Override
    public void onFinishProcess(boolean hasSucceeded, Object result) {

        if(hasSucceeded){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    inicializarGrafica();
                }
            });


        }
    }

    @Override
    public void onFinishProcess2(boolean hasSucceeded, final Object result) {

        if(hasSucceeded){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    inicializarMetas(result);
                }
            });


        }

    }

    private void inicializarMetas(Object result) {


        String [] datosMetas = (String []) result;

        intPasosAsignadas = Integer.parseInt(datosMetas[0]);
        intPasosLogrados = Integer.parseInt(datosMetas[1]);
        intKgcaloriasAsignadas = Integer.parseInt(datosMetas[2]);
        intKgcaloriasLogradas = Integer.parseInt(datosMetas[3]);
        intPasosFaltantes = intPasosAsignadas - intPasosLogrados;
        intKgcaloriasFaltantes = intKgcaloriasAsignadas -intKgcaloriasLogradas;

        lblPasosAsignados.setText(datosMetas[0]);
        lblPasosLogrados.setText(datosMetas[1]);
        lblPasosFaltantes.setText(""+intPasosFaltantes);

        lblKgCaloriasasignada.setText(datosMetas[2]);
        lblKgCaloriasLogradas.setText(datosMetas[3]);
        lblKgcaloriasFaltantes.setText("" + intPasosFaltantes);

        linePasos.addCoordenada(1,intPasosAsignadas);
        linePasos.addCoordenada2(2, intPasosLogrados);

        lineKgCalorias.addCoordenada(1,intKgcaloriasAsignadas);
        lineKgCalorias.addCoordenada2(2, intKgcaloriasLogradas);

        viewPasos.repaint();
        viewKgCalorias.repaint();

    }


    public void inicializarGrafica (){

        MonitorTake rutina = agentPulso.take;

        lblduracion1.setText(rutina.getDuration());
        lblduracion2.setText(rutina.getDuration());
        lblHoraInicio1.setText(rutina.getTime_start());
        lblHoraInicio2.setText(rutina.getTime_pos_start());
        lblHoraFin1.setText(rutina.getTime_finish());
        lblHoraFin2.setText(rutina.getTime_pos_finish());
        lblPulso1.setText(rutina.getPulsoPromedio());
        lblPulso2.setText(rutina.getPulsoPromedio1());
        lblPulsoMaximo1.setText(rutina.getPulsoMaximo());
        lblPulsoMaximo2.setText(rutina.getPulsoMaximo1());
        lblPulsoMinimo1.setText(rutina.getPulsoMinimo());
        lblPulsoMinimo2.setText(rutina.getPulsoMinimo1());
        lblPasos.setText(rutina.getPasos());
        lblKgCalorias.setText(rutina.getKgCalorias());


        ArrayList<Integer> valores1 = rutina.getTakes_1();
        ArrayList <Integer> valores2 = rutina.getTakes_2();

        for (int i = 0; i< valores1.size();i++){

            line.addCoordenada(i,valores1.get(i));

        }

        view.repaint();

        for (int i = 0; i< valores2.size();i++){

            line1.addCoordenada(i,valores2.get(i));
        }

        view1.repaint();

    }
}
