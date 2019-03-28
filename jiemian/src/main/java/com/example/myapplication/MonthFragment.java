package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2018/6/10.
 */

public class MonthFragment extends Fragment {

private  LinearLayout   lc_history_temperature,lc_history_weight,lc_history_heartbeat ,
     lc_history_bloodpressure,lc_history_bloodfat;
    private GraphicalView lChart1, lChart2, lChart3, lChart4, lChart5;
private int [] colors;
    private  PointStyle[] styles;
    private  int length;
   private String[] titles;
   private List<double[]> x,data;
    private final String Temperature = "temperature";
    private final String Weight = "weight";//map中key的值
    private final String HeartBeat = "heartBeat";
    private final String BloodPressure = "bloodPressure";
    private final String BloodFat = "bloodFat";
    private List<Map<String, Object>> g1,g2,g3,g4,g5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_month, container, false);

       // return view;

 lc_history_temperature = (LinearLayout) view.findViewById(R.id.lc_history_temperature);
      lc_history_weight = (LinearLayout) view.findViewById(R.id.lc_history_weight);
      lc_history_heartbeat = (LinearLayout) view.findViewById(R.id.lc_history_heartbeat);
 lc_history_bloodpressure = (LinearLayout) view.findViewById(R.id.lc_history_bloodpressure);
    lc_history_bloodfat = (LinearLayout) view.findViewById(R.id.lc_history_bloodfat);



        String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos" };
        List<double[]> x = new ArrayList<double[]>();
        for (int i = 0; i < titles.length; i++) {
            x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
        }
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2, 13.9 });
        values.add(new double[] { 10, 10, 12, 15, 20, 24, 26, 26, 23, 18, 14, 11 });
        values.add(new double[] { 5, 5.3, 8, 12, 17, 22, 24.2, 24, 19, 15, 9, 6 });
        values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
        int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND, PointStyle.TRIANGLE, PointStyle.SQUARE };
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, "Average temperature", "Month", "Temperature", 0.5, 12.5, -10, 40, Color.LTGRAY, Color.LTGRAY);
        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);

        renderer.setZoomButtonsVisible(true);
        renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
        renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });




        lChart1 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles, x, values), renderer);
        lChart1.setBackgroundColor(Color.BLACK);

        lChart2 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles, x, values), renderer);
        lChart2.setBackgroundColor(Color.BLACK);

        lChart3 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles, x, values), renderer);
        lChart3.setBackgroundColor(Color.BLACK);

        lChart4 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles, x, values), renderer);
        lChart4.setBackgroundColor(Color.BLACK);

        lChart5 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), buildDataset(titles, x, values), renderer);
        lChart5.setBackgroundColor(Color.BLACK);
        lc_history_temperature.addView(lChart1);
        lc_history_weight.addView(lChart2);
        lc_history_heartbeat.addView(lChart3);
        lc_history_bloodpressure.addView(lChart4);
        lc_history_bloodfat.addView(lChart5);
        return view;
    }

    private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setPointSize(5f);
        renderer.setMargins(new int[] { 20, 30, 15, 20 });
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    private XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues, List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset, titles, xValues, yValues, 0);
        return dataset;
    }

    private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues, List<double[]> yValues, int scale) {
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
    }
}
