package com.kawakp.kp.application.router;

import android.support.v4.app.Fragment;

import com.kawakp.kp.application.ui.fragment.anim.GifFragment;
import com.kawakp.kp.application.ui.fragment.anim.GuideFragment;
import com.kawakp.kp.application.ui.fragment.anim.ResponseAnimFragment;
import com.kawakp.kp.application.ui.fragment.anim.SimpleAnimFragment;
import com.kawakp.kp.application.ui.fragment.anim.ToastFragment;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:动画列表配置
 */
public enum AnimItemRouter implements FunRouter {
    RESPONSE("响应动画", new ResponseAnimFragment()),
    GIF("GIF图片", new GifFragment()),
    PUSH("使用引导", new GuideFragment()),
    TOAST("信息提示", new ToastFragment()),
    ANIM("简单流程动画", new SimpleAnimFragment());

    private String mItemName;
    private Fragment mTarget;

    AnimItemRouter(String name, Fragment target) {
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
