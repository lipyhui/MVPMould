package com.kawakp.kp.application.ui.fragment.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kawakp.kp.application.databinding.FragmentChartRealTimeBinding;
import com.kawakp.kp.kernel.base.BaseBingingFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:  QS
 * 修改时间:2017/9/14
 * 修改内容: 折线图的添加
 * <p>
 * 功能描述:折线图的展示
 */

public class RealTimeChartFragment extends BaseBingingFragment<FragmentChartRealTimeBinding> {
    private final String color_line = "#1976D2";
    private final String color_text = "#666666";
    private final String color_grid = "#e4e5e8";
    private int count = 0;
    private LineDataSet.Mode mode = LineDataSet.Mode.LINEAR;

    private class ChartThread extends Thread {
        @Override
        public void run() {
            while (true) {
                SystemClock.sleep(1000);
                if (getActivity() == null)
                    break;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addEntry();
                    }
                });
            }
        }
    }

    private Disposable dis;

    @NotNull
    @Override
    public FragmentChartRealTimeBinding createDataBinding(LayoutInflater inflater, ViewGroup container,
                                                          Bundle savedInstanceState) {
        return FragmentChartRealTimeBinding.inflate(inflater, container, false);
    }

    @Override
    public void initView() {
        Log.e("ItemFragment", "Item1Fragment one");
        init();
        initChart1();
//        new ChartThread().start();

        if (dis == null) {
            dis = Observable.interval(1, TimeUnit.SECONDS).compose(this.bindToLifecycle())
                    .subscribe(it -> addEntry());
        } else {
            dis.dispose();
            dis = Observable.interval(1, TimeUnit.SECONDS).compose(this.bindToLifecycle())
                    .subscribe(it -> addEntry());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void init() {
        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = LineDataSet.Mode.HORIZONTAL_BEZIER;
            }
        });
        mBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = LineDataSet.Mode.LINEAR;
            }
        });
    }


    private void initChart1() {
        mBinding.charts1.setNoDataText("暂时尚无数据");
        mBinding.charts1.setTouchEnabled(true);
        // 可拖曳
        mBinding.charts1.setDragEnabled(true);
        // 可缩放
        mBinding.charts1.setScaleEnabled(true);
        mBinding.charts1.setDrawGridBackground(false);
        mBinding.charts1.setPinchZoom(true);
        // 设置图表的背景颜色
        mBinding.charts1.setBackgroundColor(Color.parseColor("#00000000"));
        LineData data = new LineData();
        // 数据显示的颜色
        data.setValueTextColor(Color.BLACK);
        // 先增加一个空的数据，随后往里面动态添加
        mBinding.charts1.setData(data);
        // 图表的注解(只有当数据集存在时候才生效)
        Legend l = mBinding.charts1.getLegend();
        // 可以修改图表注解部分的位置
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        // 线性，也可是圆
        l.setForm(Legend.LegendForm.LINE);
        // 颜色
        l.setTextColor(Color.WHITE);
        // x坐标轴
        XAxis xl = mBinding.charts1.getXAxis();
        // x轴字体颜色
        xl.setTextColor(Color.parseColor(color_text));
        //是否显示x轴背景线
        xl.setDrawGridLines(true);
        //x轴背景线的颜色
        xl.setGridColor(Color.parseColor(color_grid));
        xl.setAvoidFirstLastClipping(true);
        // x轴的数量
        xl.setLabelCount(5);
        // 如果false，那么x坐标轴将不可见
        xl.setEnabled(true);
        // 将X坐标轴放置在底部，默认是在顶部。
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 图表左边的y坐标轴线
        YAxis leftAxis = mBinding.charts1.getAxisLeft();
        // Y轴字体颜色
        leftAxis.setTextColor(Color.parseColor(color_text));
        // y轴数量
        leftAxis.setLabelCount(5);
        // 最大值
        leftAxis.setAxisMaxValue(90f);
        // 最小值
        leftAxis.setAxisMinValue(40f);
        // 不一定要从0开始
        leftAxis.setStartAtZero(true);
        //是否显示Y轴的背景线
        leftAxis.setDrawGridLines(true);
        //Y轴背景线的颜色
        leftAxis.setGridColor(Color.parseColor(color_grid));
        YAxis rightAxis = mBinding.charts1.getAxisRight();
        // 不显示图表的右边y坐标轴线
        rightAxis.setEnabled(false);
    }

    // 添加进去一个坐标点
    private void addEntry() {
        LineData data = mBinding.charts1.getData();
        // 每一个LineDataSet代表一条线，每张统计图表可以同时存在若干个统计折线，这些折线像数组一样从0开始下标。
        // 本例只有一个，那么就是第0条折线
        LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
        // 如果该统计折线图还没有数据集，则创建一条出来，如果有则跳过此处代码。
        if (set == null) {
            set = createLineDataSet();
            data.addDataSet(set);
        }
        // 生成随机测试数
        int max = 21;
        int min = 5;
        Random random = new Random();
        float g = random.nextInt(max) % (max - min + 1) + min;
        // set.getEntryCount()获得的是所有统计图表上的数据点总量，
        // Entry(X轴坐标，Y轴坐标)
        count++;
        Entry entry = new Entry(count, g);
        // 往linedata里面添加点。注意：addentry的第二个参数即代表折线的下标索引。
        // 因为本例只有一个统计折线，那么就是第一个，其下标为0.
        // 如果同一张统计图表中存在若干条统计折线，那么必须分清是针对哪一条（依据下标索引）统计折线添加。
        data.addEntry(entry, 0);
        // 像ListView那样的通知数据更新
        mBinding.charts1.notifyDataSetChanged();
        // 当前统计图表中最多在x轴坐标线上显示的总量
        mBinding.charts1.setVisibleXRangeMaximum(5);
        // y坐标轴线最大值,文字的位置
        mBinding.charts1.setVisibleYRange(5, 25, YAxis.AxisDependency.LEFT);
//        mBinding.charts1.setVisibleYRangeMaximum(5, YAxis.AxisDependency.LEFT);
        // 将坐标移动到最新
        // 此代码将刷新图表的绘图
//        mBinding.charts1.moveViewToX(data.getEntryCount());
        mBinding.charts1.moveViewToAnimated(data.getEntryCount(), 0, YAxis.AxisDependency.LEFT, 500);
        // mBinding.charts1.moveViewTo(data.getXValCount()-7, 55f,
        // AxisDependency.LEFT);
        // y轴显示多少个点
        mBinding.charts1.getAxisLeft().setLabelCount(6);
        set.setMode(mode);

    }

    // 初始化数据集，添加一条统计折线，可以简单的理解是初始化y坐标轴线上点的表征
    private LineDataSet createLineDataSet() {
        LineDataSet set = new LineDataSet(null, null);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        // 折线的颜色
        set.setColor(Color.parseColor(color_line));
        //字体颜色
        set.setValueTextColor(Color.parseColor(color_text));
        //字体大小
        set.setValueTextSize(10f);
        set.setDrawValues(true);
        //点的颜色
        set.setCircleColor(Color.parseColor(color_line));
        // 折线的宽
        set.setLineWidth(2f);
        set.setDrawCircleHole(true);
        set.setCircleHoleRadius(2f);
        set.setCircleRadius(3f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        //是否填充线与轴之间
        set.setDrawFilled(true);
        return set;
    }

}
