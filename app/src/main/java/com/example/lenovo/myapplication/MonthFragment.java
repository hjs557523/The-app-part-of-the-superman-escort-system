package com.example.lenovo.myapplication;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2018/6/10.
 */

public class MonthFragment extends Fragment {

    private LinearLayout lc_history_temperature, lc_history_weight, lc_history_heartbeat,
            lc_history_bloodpressure, lc_history_bloodfat;
    private GraphicalView lChart1, lChart2, lChart3, lChart4, lChart5;
    private ProgressDialog loginProgress;
    //    private double[] temperature2;
    private String[] datetime2;
    private String[] temperature1;
    private String[] weight1;
    private String[] heartbeat1;
    private String[] systolicPressure1;
    private String[] diastolicPressure1;
    private String[] bloodFat1;
    private String[] datetime;
    private String[] day;
    private String month;
    private int number2;





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);

        Bundle bundle3 = getArguments();//从activity传过来的Bundle
        if (bundle3 != null) {
            number2 = bundle3.getInt("n");
            temperature1=  bundle3.getStringArray("temperatureA2");
            weight1=bundle3.getStringArray("weightA2");
            heartbeat1= bundle3.getStringArray("heartbeatA2");
            systolicPressure1= bundle3.getStringArray("systolicPressureA2");
            diastolicPressure1 = bundle3.getStringArray("diastolicPressureA2");
            bloodFat1= bundle3.getStringArray("bloodFatA2");
            day=bundle3.getStringArray("day");
            month=bundle3.getString("month");
        }
        double[] temperature2=new double[number2];
        double[] weight2=new double[number2];
        double[] heartbeat2=new double[number2];
        double[] systolicPressure2=new double[number2];
        double[] diastolicPressure2=new double[number2];
        double[] bloodFat2=new double[number2];
        for (int i = 0; i < number2; i++) {
            temperature2[i] = Double.valueOf(temperature1[i]);
        }

        for (int j= 0; j < number2; j++) {
            weight2[j] = Double.valueOf(weight1[j]);
        }
        for (int q= 0; q < number2; q++) {
            heartbeat2[q] = Double.valueOf(heartbeat1[q]);
        }
        for (int z = 0; z < number2; z++) {
            systolicPressure2[z] = Double.valueOf(systolicPressure1[z]);
        }
        for (int o = 0; o < number2; o++) {
            diastolicPressure2[o] = Double.valueOf(diastolicPressure1[o]);
        }
        for (int g = 0; g < number2; g++) {
            bloodFat2[g] = Double.valueOf(bloodFat1[g]);
        }

        TextView tx1 = (TextView) view.findViewById(R.id.tx1);
        TextView tx2 = (TextView) view.findViewById(R.id.tx2);
        TextView tx3 = (TextView) view.findViewById(R.id.tx3);
        TextView tx4 = (TextView) view.findViewById(R.id.tx4);
        TextView tx5 = (TextView) view.findViewById(R.id.tx5);

        String[] titles1 = new String[]{"体温"};
        String[] titles2 = new String[]{"体重"};
        String[] titles3 = new String[]{"心率"};
        String[] titles4 = new String[]{"血脂"};
        String[] titles = new String[]{"舒张压", "收缩压"};
//
        double [] n=new double[number2];
        for(int j = 0; j <number2; j++)
        {
            n[j]=j+1;
        }

        List<double[]> x= new ArrayList<double[]>();

        for (int i = 0; i < titles.length; i++) {
            x.add(n);
        }

        List<double[]> x1 = new ArrayList<double[]>();
        for (int i = 0; i < titles1.length; i++) {
            x1.add(n);
        }

        List<double[]> bloodpressure = new ArrayList<double[]>();
        bloodpressure.add(systolicPressure2);
        bloodpressure.add(diastolicPressure2);

        List<double[]> temperature = new ArrayList<double[]>();
        temperature.add(temperature2);
//
        List<double[]> weight = new ArrayList<double[]>();
        weight.add(weight2);

        List<double[]> heartbeat = new ArrayList<double[]>();
        heartbeat.add(heartbeat2);

        List<double[]> bloodfat = new ArrayList<double[]>();
        bloodfat.add(bloodFat2);

        int[] colors1 = new int[] {Color.CYAN};
        int[] colors = new int[] { Color.CYAN,Color.YELLOW };
        PointStyle[] styles1 = new PointStyle[] {PointStyle.DIAMOND};
        PointStyle[] styles = new PointStyle[] {PointStyle.DIAMOND, PointStyle.TRIANGLE};
        if(x.isEmpty()) {
            tx1.setTextColor(Color.RED);
            tx2.setTextColor(Color.RED);
            tx3.setTextColor(Color.RED);
            tx4.setTextColor(Color.RED);
            tx5.setTextColor(Color.RED);
            tx1.setText("没有数据，快去体检吧");
            tx2.setText("没有数据，快去体检吧");
            tx3.setText("没有数据，快去体检吧");
            tx4.setText("没有数据，快去体检吧");
            tx5.setText("没有数据，快去体检吧");
        }
        else {
        // 血压图
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);

        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }

        setChartSettings(renderer, "血压趋势", "日期", "血压", 0.5,number2+0.5,50, 200, Color.LTGRAY, Color.LTGRAY);
        // loginProgress.dismiss();
        renderer.setXLabels(0);
        renderer.setYLabels(10);

        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setPanEnabled(false);   //图表是否可以移动
        renderer.setZoomEnabled(true);   //图表是否可以缩放
        renderer.setLegendHeight(100);
        renderer.setMarginsColor(Color.DKGRAY);




            lChart4 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles,x, bloodpressure), renderer);
            lChart4.setBackgroundColor(Color.DKGRAY);
//体温图
        XYMultipleSeriesRenderer renderer2 = buildRenderer(colors1, styles1);
        int length2 = renderer2.getSeriesRendererCount();
        for (int i = 0; i < length2; i++) {
            ((XYSeriesRenderer) renderer2.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer2, "体温趋势", "日期", "体温",  0.5,number2+0.5,35, 41, Color.LTGRAY, Color.LTGRAY);
        renderer2.setXLabels(0);
        renderer2.setYLabels(10);
        renderer2.setShowGrid(true);
        renderer2.setXLabelsAlign(Paint.Align.RIGHT);
        renderer2.setYLabelsAlign(Paint.Align.RIGHT);

        renderer2.setPanEnabled(false);   //图表是否可以移动
        renderer2.setZoomEnabled(true);   //图表是否可以缩放
        renderer2.setLegendHeight(100);
            renderer2.setMarginsColor(Color.DKGRAY);
        lChart1 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles1, x1,temperature), renderer2);
        lChart1.setBackgroundColor(Color.DKGRAY);
//体重图
        XYMultipleSeriesRenderer renderer3 = buildRenderer(colors1, styles1);
        int length3 = renderer3.getSeriesRendererCount();
        for (int i = 0; i < length3; i++) {
            ((XYSeriesRenderer) renderer3.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer3, "体重趋势", "日期", "体重", 0.5,number2+0.5, 40, 300, Color.LTGRAY, Color.LTGRAY);
        renderer3.setXLabels(0);
        renderer3.setYLabels(5);
        renderer3.setShowGrid(true);
        renderer3.setXLabelsAlign(Paint.Align.RIGHT);
        renderer3.setYLabelsAlign(Paint.Align.RIGHT);

        renderer3.setPanEnabled(false);   //图表是否可以移动
        renderer3.setZoomEnabled(true);   //图表是否可以缩放
        renderer3.setLegendHeight(100);
            renderer3.setMarginsColor(Color.DKGRAY);
        lChart2 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles2, x1, weight), renderer3);
        lChart2.setBackgroundColor(Color.DKGRAY);
//心率图
        XYMultipleSeriesRenderer renderer4 = buildRenderer(colors1, styles1);
        int length4 = renderer4 .getSeriesRendererCount();
        for (int i = 0; i < length4; i++) {
            ((XYSeriesRenderer) renderer4 .getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer4 , "心率趋势", "日期", "心率", 0.5,number2+0.5,50, 130, Color.LTGRAY, Color.LTGRAY);
        renderer4 .setXLabels(0);
        renderer4 .setYLabels(9);
        renderer4 .setShowGrid(true);
        renderer4 .setXLabelsAlign(Paint.Align.RIGHT);
        renderer4 .setYLabelsAlign(Paint.Align.RIGHT);

        renderer4.setPanEnabled(false);   //图表是否可以移动
        renderer4.setZoomEnabled(true);   //图表是否可以缩放
        renderer4.setLegendHeight(100);
            renderer4.setMarginsColor(Color.DKGRAY);
        lChart3 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles3,x1, heartbeat), renderer4 );
        lChart3.setBackgroundColor(Color.DKGRAY);
        //血脂图
        XYMultipleSeriesRenderer renderer5 = buildRenderer(colors1, styles1);
        int length5= renderer5.getSeriesRendererCount();
        for (int i = 0; i < length5; i++) {
            ((XYSeriesRenderer) renderer5.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer5, "血脂趋势", "日期", "血脂",0.5,number2+0.5,1, 10, Color.LTGRAY, Color.LTGRAY);
        renderer5.setXLabels(0);
        renderer5.setYLabels(10);
        renderer5.setShowGrid(true);
        renderer5.setXLabelsAlign(Paint.Align.RIGHT);
        renderer5.setYLabelsAlign(Paint.Align.RIGHT);

        renderer5.setPanEnabled(false);   //图表是否可以移动
        renderer5.setZoomEnabled(true);   //图表是否可以缩放
        renderer5.setLegendHeight(100);
            renderer5.setMarginsColor(Color.DKGRAY);
        lChart5 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles4,x1, bloodfat), renderer5);
        lChart5.setBackgroundColor(Color.DKGRAY);


        lc_history_temperature = (LinearLayout) view.findViewById(R.id.lc_history_temperature);
        lc_history_weight = (LinearLayout) view.findViewById(R.id.lc_history_weight);
        lc_history_heartbeat = (LinearLayout) view.findViewById(R.id.lc_history_heartbeat);
        lc_history_bloodpressure = (LinearLayout) view.findViewById(R.id.lc_history_bloodpressure);
        lc_history_bloodfat = (LinearLayout) view.findViewById(R.id.lc_history_bloodfat);




        lc_history_temperature.addView(lChart1);
        lc_history_weight.addView(lChart2);
        lc_history_heartbeat.addView(lChart3);
        lc_history_bloodpressure.addView(lChart4);
        lc_history_bloodfat.addView(lChart5);
}
        return view;


    }




    private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
        renderer.setAxisTitleTextSize(30);
        renderer.setChartTitleTextSize(30);
        renderer.setLabelsTextSize(30);
        renderer.setLegendTextSize(30);

        renderer.setPointSize(13f);
        renderer.setMargins(new int[] { 40, 50, 50, 20 });
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setLineWidth(3);
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle,double xMin,double xMax,double yMin, double yMax, int axesColor, int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
//      renderer.addXTextLabel(1, "第一周");
//       renderer.addXTextLabel(2, "第二周");
//       renderer.addXTextLabel(3, "第三周");
//       renderer.addXTextLabel(4, "第四周");
        for (int r = 0; r < number2; r++) {
            renderer.addXTextLabel(r + 1, month+"月"+day[r]+"日");
        }

    }

    private XYMultipleSeriesDataset buildDataset(String[] titles,List<double[]> xValues, List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        addXYSeries(dataset, titles,xValues,yValues, 0);
        return dataset;
    }

    private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,List<double[]> xValues, List<double[]> yValues, int scale) {
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k <seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);

        }
    }





}

