package com.example.lenovo.myapplication;

import android.graphics.Color;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Arrays;

/**
 * Created by Lenovo on 2018/6/5.
 */

public class ChartDrawing {
    private String xTitle, yTitle, chartTitle;
    private String xLabel[];
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer multiRenderer;

    public XYMultipleSeriesRenderer getMultiRenderer() {
        return multiRenderer;
    }

    public XYMultipleSeriesDataset getDataset() {
        return dataset;
    }


    ChartDrawing(String xTitle, String yTitle, String chartTitle,
                 String xLabel[]) {

        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.xLabel = Arrays.copyOf(xLabel, xLabel.length);
        this.chartTitle = chartTitle;
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        this.multiRenderer = new XYMultipleSeriesRenderer();
        // Creating a dataset to hold each series
        this.dataset = new XYMultipleSeriesDataset();
    }


    /**
     * 给XYSeries对象复制。并将其加到数据集 XYMultipleSeriesDataset对象中去
     * */
    public void set_XYSeries(int value[], String lineName) {
        // 创建一个XYSeries存放线为lineName上的数据
        XYSeries oneSeries = new XYSeries(lineName);
        // Adding data to Series
        for (int i = 0; i < value.length; i++) {
            oneSeries.add(i + 1, value[i]);

        }
        // Adding Series to the dataset
        this.dataset.addSeries(oneSeries);
    }


    public XYSeriesRenderer set_XYSeriesRender_Style() {
        XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
        //设置线条的颜色
        seriesRenderer.setColor(Color.BLUE);
        seriesRenderer.setFillPoints(true);
        //设置线条的宽度
        seriesRenderer.setLineWidth(2);
        seriesRenderer.setDisplayChartValues(true);
        return seriesRenderer;

    }

    public void set_XYMultipleSeriesRenderer_Style(XYSeriesRenderer renderer) {
        // 设置 X 轴不显示数字(改用我们手动添加的文字标签)
        this.multiRenderer.setXLabels(0);
        //设置Y轴的结点数
        this.multiRenderer.setYLabels(10);
        //设置X轴的代表的名称
        this.multiRenderer.setXTitle(xTitle);
        //设置Y轴的代表的名称
        this.multiRenderer.setYTitle(yTitle);
        //设置柱状图中柱子之间的间隔
        this.multiRenderer.setBarSpacing(0.5);
        this.multiRenderer.setZoomButtonsVisible(true);
        for (int i = 0; i < xLabel.length; i++) {
            //添加X轴便签
            this.multiRenderer.addXTextLabel(i + 1, this.xLabel[i]);
        }

        this.multiRenderer.addSeriesRenderer(renderer);

    }

}
