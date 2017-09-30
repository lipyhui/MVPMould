
package com.kawakp.shengqi.animationlib.fading_entrances;

import android.animation.ObjectAnimator;
import android.view.View;

import com.kawakp.shengqi.animationlib.BaseViewAnimator;

/**
 * 创建人: qi
 * 创建时间: 2017/9/27
 * 修改人:qi
 * 修改时间: 2017/9/27
 * 修改内容:
 * <p>
 * 功能描述: 逐渐从左出现
 */
public class FadeInLeftAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                ObjectAnimator.ofFloat(target, "translationX", -target.getWidth() / 4, 0)
        );
    }
}
