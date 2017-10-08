package com.kawakp.kp.application.router;

import android.support.v4.app.Fragment;

import com.kawakp.kp.application.ui.fragment.form.FormFragment;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:数据功能列表配置
 */

public enum DataItemRouter implements FunRouter {
    CHART("表格", new FormFragment()),
    ;

    private String mItemName;
    private Fragment mTarget;

    DataItemRouter(String name, Fragment target) {
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
