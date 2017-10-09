package com.kawakp.kp.kputilslib;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * 创建人: qi
 * 创建时间: 2017/9/21
 * 修改人:qi
 * 修改时间:2017/9/21
 * 修改内容:
 * <p>
 * 功能描述:点击动画 目前只有弹性动画，后续的再添加
 */
public class ClickAnimation {
    /**
     * 点击后简单的弹性动画
     *
     * @param v
     */
    public static void onScaleAnimation(View v) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(v, "scaleX", 0.5f, 1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(v, "scaleY", 0.5f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether(animatorX, animatorY);
        set.start();
    }
}
