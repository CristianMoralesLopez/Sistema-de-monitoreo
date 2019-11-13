package com.cristian.sistemademonitoreo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

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
