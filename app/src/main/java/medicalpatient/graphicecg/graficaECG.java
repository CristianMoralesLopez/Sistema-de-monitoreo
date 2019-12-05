package medicalpatient.graphicecg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cristian.sistemademonitoreo.R;

import org.achartengine.GraphicalView;

import java.text.StringCharacterIterator;

import medicalpatient.Parametros.AgentPulso;
import medicalpatient.utils.ListDuo;
import utils.DefaultCallback;

public class graficaECG extends AppCompatActivity implements DefaultCallback {


    private static GraphicalView view;
    private LineGraph line = new LineGraph();
    private static Thread thread;
    private AgentPulso agentPulso;
    private ListDuo listDuo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_ecg);

        Bundle bundle = getIntent().getExtras();

        agentPulso = new AgentPulso();

        agentPulso.getDataMonitorDate((String) bundle.get("id"),this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        view = line.getView(this);
        setContentView(view);
    }


    @Override
    public void onFinishProcess(boolean hasSucceeded, Object result) {
        if(hasSucceeded){



            thread = new Thread(){
                public void run(){
                    listDuo = agentPulso.getTakes();



                    for(int i =0; i< listDuo.size();i++){

                        String valorX = listDuo.get(i).getValue1();
                        System.out.println("VALOR X" + valorX);

                      valorX =  valorX.replaceAll(":","");

                        if(valorX.charAt(0)=='0'){
                            valorX = valorX.substring(1,valorX.length()-2);
                        }

                        String valorY = listDuo.get(i).getValue2();


                        double x = Integer.parseInt(valorX);
                        double y = Integer.parseInt(valorY);

                        System.out.println(valorX);
                        System.out.println(valorY);

                        line.addCoordenada(x,y);
                    }


                     view.repaint();

                }
            };

            thread.start();


        }

    }
}
