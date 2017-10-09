package com.kawakp.kp.animationlib.attention;

import android.animation.ObjectAnimator;
import android.view.View;

import com.kawakp.kp.animationlib.BaseViewAnimator;

/**
 * 创建人: qi
 * 创建时间: 2017/9/28
 * 修改人:qi
 * 修改时间: 2017/9/28
 * 修改内容:
 * <p>
 * 功能描述: 闪动
 */
public class FlashAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0, 1, 0, 1)
        );
    }
}
