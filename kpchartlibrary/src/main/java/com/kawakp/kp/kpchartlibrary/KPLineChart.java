package com.kawakp.kp.kpchartlibrary;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;

/**
 * 创建人: qi
 * 创建时间: 2017/9/28
 * 修改人:qi
 * 修改时间:2017/9/28
 * 修改内容:
 * <p>
 * 功能描述: KP线形图 基础默认属性
 */
public class KPLineChart extends LineChart {
    private final String NO_DATA_TEXT = "暂时尚无数据";

    public KPLineChart(Context context) {
        super(context);
    }

    public KPLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KPLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * @param text 无数据时显示的文字,NO_DATA_TEXT
     */
    @Override
    public void setNoDataText(String text) {
        super.setNoDataText(NO_DATA_TEXT);
    }


    /**
     * @param enabled 默认可以点击
     */
    @Override
    public void setTouchEnabled(boolean enabled) {
        super.setTouchEnabled(true);
    }

    /**
     * @param enabled 默认可以拖拽
     */
    @Override
    public void setDragEnabled(boolean enabled) {
        super.setDragEnabled(true);
    }

    /**
     * @param enabled 可以滑动
     */
    @Override
    public void setScaleEnabled(boolean enabled) {
        super.setScaleEnabled(true);
    }

    /**
     * @param enabled 背景
     */
    @Override
    public void setDrawGridBackground(boolean enabled) {
        super.setDrawGridBackground(false);
    }

    /**
     * @param enabled 可缩放
     */
    @Override
    public void setPinchZoom(boolean enabled) {
        super.setPinchZoom(true);
    }


}
