package com.kawakp.kp.application.ui.fragment.chart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.SeekBar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentPieChartHistoryBinding;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;

import java.util.ArrayList;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:  QS
 * 修改时间:2017/9/14
 * 修改内容: 折线图的添加
 * <p>
 * 功能描述:饼状图
 */

public class HistoryPieChartFragment extends BaseFragment<EmptyPresenter, FragmentPieChartHistoryBinding> implements SeekBar.OnSeekBarChangeListener {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pie_chart_history;
    }

    @Override
    protected void onFirstUserVisible() {
        initPie();
    }

    private void initPie() {

        mBinding.mSeekBarX.setProgress(4);
        mBinding.mChart.setUsePercentValues(true);
        mBinding.mChart.getDescription().setEnabled(false);
        mBinding.mChart.setExtraOffsets(5, 10, 5, 5);

        mBinding.mChart.setDragDecelerationFrictionCoef(0.95f);

        mBinding.mChart.setCenterText("这是一个虚拟图表");

        mBinding.mChart.setDrawHoleEnabled(true);
        mBinding.mChart.setHoleColor(Color.WHITE);

        mBinding.mChart.setTransparentCircleColor(Color.WHITE);
        mBinding.mChart.setTransparentCircleAlpha(110);

        mBinding.mChart.setHoleRadius(58f);
        mBinding.mChart.setTransparentCircleRadius(61f);

        mBinding.mChart.setDrawCenterText(true);

        mBinding.mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mBinding.mChart.setRotationEnabled(true);
        mBinding.mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener

        setData(7, 100);

        mBinding.mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = mBinding.mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mBinding.mChart.setEntryLabelColor(Color.WHITE);
        mBinding.mChart.setEntryLabelTextSize(12f);
        mBinding.mSeekBarX.setOnSeekBarChangeListener(this);
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("这是一个虚拟图表");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    protected String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.mipmap.ic_launcher)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mBinding.mChart.setData(data);

        // undo all highlights
        mBinding.mChart.highlightValues(null);

        mBinding.mChart.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        setData(mBinding.mSeekBarX.getProgress(), 100);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
