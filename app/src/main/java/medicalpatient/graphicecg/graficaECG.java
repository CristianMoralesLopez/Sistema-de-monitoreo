package medicalpatient.graphicecg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cristian.sistemademonitoreo.R;

import org.achartengine.GraphicalView;

public class graficaECG extends AppCompatActivity {


    private static GraphicalView view;
    private LineGraph line = new LineGraph();
    private static Thread thread;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_ecg);

        thread = new Thread(){
          public void run(){

              for(int i =0 ; i<20;i++) {
                  try {
                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }

                  double x = i;

                  line.addCoordenada(x,x);

                  view.repaint();

              }

          }
        };

        thread.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        view = line.getView(this);
        setContentView(view);
    }






}
