package com.example.hellomap;
import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class Graph extends MainActivity {
    private Context context;
    XYMultipleSeriesDataset dataset;
    XYMultipleSeriesRenderer renderer;
    int greater;
    public static final int MAX_NB_VALUES_PER_SERIE = 1000;
    public static boolean ClickEnabled = true;
    public Graph(Context context) {
        this.context = context;
    }

    public void initData(ArrayList<Double> x,ArrayList<Double> y,ArrayList<Double> z){      
        XYSeries seriesX = new XYSeries("X");
        for(int i =0 ; i< x.size();i++){
            seriesX.add (i,x.get(i));
        }
        XYSeries seriesY = new XYSeries("Y");
        for(int i =0 ; i< y.size();i++){
            seriesY.add( i,y.get(i));
        }
        XYSeries seriesZ = new XYSeries("Z");
        for(int i =0 ; i< z.size();i++){
            seriesZ.add( i,z.get(i));
        }
        greater = x.size();
        if(y.size() > greater){
            greater = y.size();
        }else if(z.size() > greater){
            greater = z.size();
        }
        dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(seriesX);
        dataset.addSeries(seriesY);
        dataset.addSeries(seriesZ);
        renderer = new XYMultipleSeriesRenderer();
    }

    public void setProperties(){

        //renderer.setClickEnabled(ClickEnabled);
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setMarginsColor(Color.WHITE);
        renderer.setApplyBackgroundColor(true);
        if(greater < 100){
            renderer.setXAxisMax(100);
        }else{
            renderer.setXAxisMin(greater-100);
            renderer.setXAxisMax(greater);
        }
        renderer.setChartTitle("Su-pravas Accelerometer data ");
        renderer.setShowGrid(true);

        renderer.setAxesColor(Color.BLACK);
        XYSeriesRenderer renderer1 = new XYSeriesRenderer();
        renderer1.setColor(Color.MAGENTA);
        renderer.addSeriesRenderer(renderer1);
        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setColor(Color.GREEN);
        renderer.addSeriesRenderer(renderer2);
        XYSeriesRenderer renderer3 = new XYSeriesRenderer();
        renderer3.setColor(Color.BLUE);
        renderer.addSeriesRenderer(renderer3);
        
    }
    
    public GraphicalView getGraph(){    
        return ChartFactory.getLineChartView(context, dataset, renderer);
        
        
    }
    
    public synchronized void removeFirst() 
    {
    	for(int i = 0; i < dataset.getSeriesCount(); i++)
    	{
            dataset.removeSeries(i);
        }
    }   

}
    
