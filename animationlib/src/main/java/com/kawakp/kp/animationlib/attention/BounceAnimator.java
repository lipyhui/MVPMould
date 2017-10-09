package com.kawakp.kp.animationlib.attention;

import android.animation.ObjectAnimator;
import android.view.View;

import com.kawakp.kp.animationlib.BaseViewAnimator;

/**
 * 创建人: qi
 * 创建时间: 2017/9/1
 * 修改人:qi
 * 修改时间: 2017/9/1
 * 修改内容:
 * <p>
 * 功能描述: 上下晃动
 */
public class BounceAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "translationY", 0, 0, -30, 0, -15, 0, 0)
        );
    }
}
