
package com.kawakp.shengqi.animationlib.sliders;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.kawakp.shengqi.animationlib.BaseViewAnimator;

/**
 * 创建人: qi
 * 创建时间: 2017/9/28
 * 修改人:qi
 * 修改时间: 2017/9/28
 * 修改内容:
 * <p>
 * 功能描述: 从左滑入中间位置
 */

public class SlideInLeftAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        ViewGroup parent = (ViewGroup) target.getParent();
        int distance = parent.getWidth() - target.getLeft();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                ObjectAnimator.ofFloat(target, "translationX", -distance, 20)
        );
    }
}
