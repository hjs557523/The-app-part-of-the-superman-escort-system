package com.example.myapplication2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

public class MainActivity extends AppCompatActivity {

    private GraphicalView lChart1,lChart2,lChart3,lChart4,lChart5;
    private ChartDrawing lineChart1,lineChart2,lineChart3,lineChart4,lineChart5;
    private String[] mMonth = new String[] { "一月", "二月", "三月", "四月", "五月",
            "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"

    };
    private String[] mWeek = new String[] { "星期天", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"

    };
    private String[] mDay=new String[] { "星期天", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"

    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] income = { 1000, 3700, 3800, 2000, 2500, 6000, 1000, 8000, 9000,
                5000, 2000 };
        lineChart1 = new ChartDrawing("temperature", "Year 2018",
                "Test Chart", mMonth);
        lineChart1.set_XYSeries(income, "体温");
        lineChart1.set_XYMultipleSeriesRenderer_Style(lineChart1
                .set_XYSeriesRender_Style());

        lineChart2 = new ChartDrawing("weight", "Year 2018",
                "Test Chart", mMonth);
        lineChart2.set_XYSeries(income, "体重");
        lineChart2.set_XYMultipleSeriesRenderer_Style(lineChart2
                .set_XYSeriesRender_Style());

        lineChart3 = new ChartDrawing("heartbeat", "Year 2018",
                "Test Chart", mMonth);
        lineChart3.set_XYSeries(income, "心跳");
        lineChart3.set_XYMultipleSeriesRenderer_Style(lineChart3
                .set_XYSeriesRender_Style());

        lineChart4 = new ChartDrawing("bloodpressure ", "Year 2018",
                "Test Chart", mMonth);
        lineChart4.set_XYSeries(income, "血压");
        lineChart4.set_XYMultipleSeriesRenderer_Style(lineChart4
                .set_XYSeriesRender_Style());

        lineChart5 = new ChartDrawing("bloodfat", "Year 2018",
                "Test Chart", mMonth);
        lineChart5.set_XYSeries(income, "血脂");
        lineChart5.set_XYMultipleSeriesRenderer_Style(lineChart5
                .set_XYSeriesRender_Style());


        LinearLayout lc_history_temperature = (LinearLayout) findViewById(R.id.lc_history_temperature);
        LinearLayout lc_history_weight = (LinearLayout) findViewById(R.id.lc_history_weight);
        LinearLayout lc_history_heartbeat = (LinearLayout) findViewById(R.id.lc_history_heartbeat);
        LinearLayout lc_history_bloodpressure = (LinearLayout) findViewById(R.id.lc_history_bloodpressure);
        LinearLayout lc_history_bloodfat = (LinearLayout) findViewById(R.id.lc_history_bloodfat);
        // Creating a Line Chart
        lChart1 = (GraphicalView) ChartFactory.getLineChartView(
                getBaseContext(), lineChart1.getDataset(),
                lineChart1.getMultiRenderer());
        lChart2 = (GraphicalView) ChartFactory.getLineChartView(
                getBaseContext(), lineChart2.getDataset(),
                lineChart2.getMultiRenderer());
        lChart3 = (GraphicalView) ChartFactory.getLineChartView(
                getBaseContext(), lineChart3.getDataset(),
                lineChart3.getMultiRenderer());
        lChart4 = (GraphicalView) ChartFactory.getLineChartView(
                getBaseContext(), lineChart4.getDataset(),
                lineChart4.getMultiRenderer());
        lChart5 = (GraphicalView) ChartFactory.getLineChartView(
                getBaseContext(), lineChart5.getDataset(),
                lineChart5.getMultiRenderer());
        // Adding the Line Chart to the LinearLayout

        lc_history_temperature.addView(lChart1);
        lc_history_weight.addView(lChart2);
        lc_history_heartbeat.addView(lChart3);
        lc_history_bloodpressure.addView(lChart4);
        lc_history_bloodfat.addView(lChart5);
    }


}