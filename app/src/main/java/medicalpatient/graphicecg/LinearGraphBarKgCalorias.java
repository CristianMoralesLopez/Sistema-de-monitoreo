package medicalpatient.graphicecg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class LinearGraphBarKgCalorias {



    private GraphicalView mchart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRender = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeries mCurrentSeries2;
    private XYSeriesRenderer mCurrentRenderer;



    public LinearGraphBarKgCalorias(){

        mCurrentSeries = new XYSeries("Meta Kg Calorias");
        mCurrentSeries2= new XYSeries("Kg Completadas");

        mDataset.addSeries(mCurrentSeries);
        mDataset.addSeries(mCurrentSeries2);

        mCurrentRenderer = new XYSeriesRenderer();
        mCurrentRenderer.setLineWidth(4);
        mCurrentRenderer.setColor(Color.RED);
        mCurrentRenderer.setDisplayChartValues(true);
        mCurrentRenderer.setChartValuesTextAlign(Paint.Align.RIGHT);
        mCurrentRenderer.setChartValuesTextSize(80);
        mCurrentRenderer.setChartValuesSpacing((float) 50);


        XYSeriesRenderer mCurrentRenderer2 = new XYSeriesRenderer();
        mCurrentRenderer2.setLineWidth(4);
        mCurrentRenderer2.setColor(Color.BLUE);
        mCurrentRenderer2.setDisplayChartValues(true);
        mCurrentRenderer2.setChartValuesTextAlign(Paint.Align.RIGHT);
        mCurrentRenderer2.setChartValuesTextSize(80);
        mCurrentRenderer2.setChartValuesSpacing((float) 50);



        mRender.setGridColor(Color.BLACK);
        mRender.setMarginsColor(Color.GREEN);
        mRender.setXAxisMax(3);
        mRender.setXAxisMin(0);
        mRender.setYAxisMax(40);
        mRender.setYAxisMin(0);
        mRender.setPanEnabled(false);
        mRender.setLabelsTextSize(25);
        mRender.setMarginsColor(Color.WHITE);
        mRender.setYLabelsColor(0,Color.BLACK);
        mRender.setXLabelsColor(Color.BLACK);
        mRender.setYTitle("Cantidad de Kgcalorias");
        mRender.setAxisTitleTextSize(50);
        mRender.setLabelsColor(Color.BLACK);
        mRender.setMargins(new int [] {80,80,80,80});
        mRender.addSeriesRenderer(mCurrentRenderer);
        mRender.addSeriesRenderer(mCurrentRenderer2);
        mRender.setBarSpacing(-0.9);
        mRender.setLegendTextSize((float) 80);

    }


    public GraphicalView getView(Context context)
    {
        mchart = ChartFactory.getBarChartView(context,mDataset,mRender, BarChart.Type.DEFAULT);
        return mchart;
    }


    public void addCoordenada(double x, double y){

        mCurrentSeries.add(x,y);

    }

    public void addCoordenada2(double x, double y){

        mCurrentSeries2.add(x,y);
    }

}
