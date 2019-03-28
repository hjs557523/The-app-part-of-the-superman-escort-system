package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2018/6/10.
 */

public class YearFragment extends Fragment {
    private List<Map<String, Object>> g1,g2,g3,g4,g5;
    private Map<String, Object> d1,d2,d3,d4,d5;
    private final String Temperature = "temperature";
    private final String Weight = "weight";//map中key的值
    private final String HeartBeat = "heartBeat";
    private final String BloodPressure = "bloodPressure";
    private final String BloodFat = "bloodFat";
    private LinearLayout lc_history_temperature,lc_history_weight,lc_history_heartbeat ,
            lc_history_bloodpressure,lc_history_bloodfat;
    private GraphicalView lChart1,lChart2,lChart3,lChart4,lChart5;
    private ChartDrawing lineChart1,lineChart2,lineChart3,lineChart4,lineChart5;
    private String[] mYear = new String[] { "一月", "二月", "三月", "四月", "五月",
            "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"

    };
    private String[] mWeek = new String[] { "星期天", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"

    };
    private String[] mDay=new String[] { "星期天", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"

    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_month, container, false);
        d1 = new HashMap<String, Object>();
        d2  = new HashMap<String, Object>();
        d3 = new HashMap<String, Object>();
        d4 = new HashMap<String, Object>();
        d5 = new HashMap<String, Object>();
        List<double[]> values = new ArrayList<double[]>();

        int[] temperature = { 37, 37, 38, 37, 37, 37, 38, 37, 37, 37, 37 };
        int[] weight = { 100,101,102, 100,101,102,100,101,102,100,101
        };
        int[] heartbeat = { 100, 90, 98, 100, 90, 98,100, 90, 98,100, 90
        };
        int[] bloodpressure = { 100, 90, 100, 90, 100, 90,  100, 90,100, 90,
                100 };
        int[] bloodfat = { 10, 37, 38, 20, 25, 6, 10, 8, 9,
                50, 20 };

        lineChart1 = new ChartDrawing("temperature", "Year 2018",
                "Test Chart", mYear);
        lineChart1.set_XYSeries(temperature, "体温");
        lineChart1.set_XYMultipleSeriesRenderer_Style(lineChart1
                .set_XYSeriesRender_Style());

        lineChart2 = new ChartDrawing("weight", "Year 2018",
                "Test Chart", mYear);
        lineChart2.set_XYSeries(weight, "体重");
        lineChart2.set_XYMultipleSeriesRenderer_Style(lineChart2
                .set_XYSeriesRender_Style());

        lineChart3 = new ChartDrawing("heartbeat", "Year 2018",
                "Test Chart", mYear);
        lineChart3.set_XYSeries(heartbeat, "心跳");
        lineChart3.set_XYMultipleSeriesRenderer_Style(lineChart3
                .set_XYSeriesRender_Style());

        lineChart4 = new ChartDrawing("bloodpressure ", "Year 2018",
                "Test Chart", mYear);
        lineChart4.set_XYSeries(bloodpressure, "血压");
        lineChart4.set_XYMultipleSeriesRenderer_Style(lineChart4
                .set_XYSeriesRender_Style());

        lineChart5 = new ChartDrawing("bloodfat", "Year 2018",
                "Test Chart", mYear);
        lineChart5.set_XYSeries(bloodfat, "血脂");
        lineChart5.set_XYMultipleSeriesRenderer_Style(lineChart5
                .set_XYSeriesRender_Style());


        lc_history_temperature = (LinearLayout) view.findViewById(R.id.lc_history_temperature);
        lc_history_weight = (LinearLayout) view.findViewById(R.id.lc_history_weight);
        lc_history_heartbeat = (LinearLayout) view.findViewById(R.id.lc_history_heartbeat);
        lc_history_bloodpressure = (LinearLayout) view.findViewById(R.id.lc_history_bloodpressure);
        lc_history_bloodfat = (LinearLayout) view.findViewById(R.id.lc_history_bloodfat);
        // Creating a Line Chart
        lChart1 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), lineChart1.getDataset(),
                lineChart1.getMultiRenderer());
        lChart2 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), lineChart2.getDataset(),
                lineChart2.getMultiRenderer());
        lChart3 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), lineChart3.getDataset(),
                lineChart3.getMultiRenderer());
        lChart4 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), lineChart4.getDataset(),
                lineChart4.getMultiRenderer());
        lChart5 = (GraphicalView) ChartFactory.getLineChartView(
                getContext(), lineChart5.getDataset(),
                lineChart5.getMultiRenderer());

        // Adding the Line Chart to the LinearLayout

        lc_history_temperature.addView(lChart1);
        lc_history_weight.addView(lChart2);
        lc_history_heartbeat.addView(lChart3);
        lc_history_bloodpressure.addView(lChart4);
        lc_history_bloodfat.addView(lChart5);
        return view;
    }


}
