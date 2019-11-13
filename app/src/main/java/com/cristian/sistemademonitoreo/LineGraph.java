package com.cristian.sistemademonitoreo;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class LineGraph {


    //version Cristian

    private GraphicalView mchart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRender = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeriesRenderer mCurrentRenderer;

    ////

    public LineGraph(){


        // version Cristian

        mCurrentSeries = new XYSeries("");
        mDataset.addSeries(mCurrentSeries);
        mCurrentRenderer = new XYSeriesRenderer();
        mCurrentRenderer.setLineWidth(4);
        mCurrentRenderer.setColor(Color.RED);
        mRender.setShowGrid(true);
        mRender.setGridColor(Color.BLACK);
        mRender.setMarginsColor(Color.GREEN);
        mRender.setXAxisMax(20);
        mRender.setXAxisMin(0);
        mRender.setYAxisMax(20);
        mRender.setYAxisMin(0);
        mRender.setPanEnabled(false);
        mRender.setLabelsTextSize(25);
        mRender.setMarginsColor(Color.WHITE);
        mRender.setYLabelsColor(0,Color.BLACK);
        mRender.setXLabelsColor(Color.BLACK);
        mRender.setXTitle("Tiempo [s]");
        mRender.setYTitle("Voltaje [mV]");
        mRender.setAxisTitleTextSize(50);
        mRender.setLabelsColor(Color.BLACK);
        mRender.setXLabels(20);
        mRender.setMargins(new int [] {80,80,80,80});
        mRender.addSeriesRenderer(mCurrentRenderer);


    }

    public GraphicalView getView(Context context)
    {
        mchart = ChartFactory.getCubeLineChartView(context, mDataset,mRender,0);
        return mchart;
    }


    public void addCoordenada(double x, double y){

        mCurrentSeries.add(x,y);
    }




}