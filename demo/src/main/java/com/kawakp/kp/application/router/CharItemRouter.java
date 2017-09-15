package com.kawakp.kp.application.router;

import android.support.v4.app.Fragment;

import com.kawakp.kp.application.ui.fragment.chart.HistoryPieChartFragment;
import com.kawakp.kp.application.ui.fragment.chart.RealTimeChartFragment;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:折线图功能列表配置
 */

public enum CharItemRouter implements FunRouter {
    CHART("实时曲线", new RealTimeChartFragment()),

    HISTORY_CHART("历史数据(饼状图)", new HistoryPieChartFragment());

    private String mItemName;
    private Fragment mTarget;

    CharItemRouter(String name, Fragment target) {
        mItemName = name;
        mTarget = target;
    }

    public String getItemName() {
        return mItemName;
    }

    public Fragment getTarget() {
        return mTarget;
    }
}
