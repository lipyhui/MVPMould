

package com.kawakp.kp.animationlib;


import com.kawakp.kp.animationlib.attention.BounceAnimator;
import com.kawakp.kp.animationlib.attention.FlashAnimator;
import com.kawakp.kp.animationlib.attention.PulseAnimator;
import com.kawakp.kp.animationlib.attention.RubberBandAnimator;
import com.kawakp.kp.animationlib.attention.ShakeAnimator;
import com.kawakp.kp.animationlib.attention.StandUpAnimator;
import com.kawakp.kp.animationlib.attention.SwingAnimator;
import com.kawakp.kp.animationlib.attention.TadaAnimator;
import com.kawakp.kp.animationlib.attention.WaveAnimator;
import com.kawakp.kp.animationlib.attention.WobbleAnimator;
import com.kawakp.kp.animationlib.fading_entrances.FadeInAnimator;
import com.kawakp.kp.animationlib.fading_entrances.FadeInDownAnimator;
import com.kawakp.kp.animationlib.fading_entrances.FadeInLeftAnimator;
import com.kawakp.kp.animationlib.fading_entrances.FadeInRightAnimator;
import com.kawakp.kp.animationlib.fading_entrances.FadeInUpAnimator;
import com.kawakp.kp.animationlib.fading_exits.FadeOutAnimator;
import com.kawakp.kp.animationlib.fading_exits.FadeOutDownAnimator;
import com.kawakp.kp.animationlib.fading_exits.FadeOutLeftAnimator;
import com.kawakp.kp.animationlib.fading_exits.FadeOutRightAnimator;
import com.kawakp.kp.animationlib.fading_exits.FadeOutUpAnimator;
import com.kawakp.kp.animationlib.rotating_entrances.RotateInAnimator;
import com.kawakp.kp.animationlib.rotating_entrances.RotateInDownLeftAnimator;
import com.kawakp.kp.animationlib.rotating_entrances.RotateInDownRightAnimator;
import com.kawakp.kp.animationlib.rotating_entrances.RotateInUpLeftAnimator;
import com.kawakp.kp.animationlib.rotating_entrances.RotateInUpRightAnimator;
import com.kawakp.kp.animationlib.rotating_exits.RotateOutAnimator;
import com.kawakp.kp.animationlib.rotating_exits.RotateOutDownLeftAnimator;
import com.kawakp.kp.animationlib.rotating_exits.RotateOutDownRightAnimator;
import com.kawakp.kp.animationlib.rotating_exits.RotateOutUpLeftAnimator;
import com.kawakp.kp.animationlib.rotating_exits.RotateOutUpRightAnimator;
import com.kawakp.kp.animationlib.sliders.SlideInDownAnimator;
import com.kawakp.kp.animationlib.sliders.SlideInLeftAnimator;
import com.kawakp.kp.animationlib.sliders.SlideInRightAnimator;
import com.kawakp.kp.animationlib.sliders.SlideInUpAnimator;
import com.kawakp.kp.animationlib.sliders.SlideOutDownAnimator;
import com.kawakp.kp.animationlib.sliders.SlideOutLeftAnimator;
import com.kawakp.kp.animationlib.sliders.SlideOutRightAnimator;
import com.kawakp.kp.animationlib.sliders.SlideOutUpAnimator;
import com.kawakp.kp.animationlib.specials.RollInAnimator;
import com.kawakp.kp.animationlib.specials.RollOutAnimator;
import com.kawakp.kp.animationlib.zooming_entrances.ZoomInAnimator;
import com.kawakp.kp.animationlib.zooming_entrances.ZoomInDownAnimator;
import com.kawakp.kp.animationlib.zooming_entrances.ZoomInLeftAnimator;
import com.kawakp.kp.animationlib.zooming_entrances.ZoomInRightAnimator;
import com.kawakp.kp.animationlib.zooming_entrances.ZoomInUpAnimator;
import com.kawakp.kp.animationlib.zooming_exits.ZoomOutAnimator;
import com.kawakp.kp.animationlib.zooming_exits.ZoomOutDownAnimator;
import com.kawakp.kp.animationlib.zooming_exits.ZoomOutLeftAnimator;
import com.kawakp.kp.animationlib.zooming_exits.ZoomOutRightAnimator;
import com.kawakp.kp.animationlib.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {
    //从左滑入中间位置
    SlideInLeft(SlideInLeftAnimator.class),
    //从右滑入中间位置
    SlideInRight(SlideInRightAnimator.class),
    //从下滑入
    SlideInUp(SlideInUpAnimator.class),
    //从上滑入，向下滑入
    SlideInDown(SlideInDownAnimator.class),
    //向左滑出
    SlideOutLeft(SlideOutLeftAnimator.class),
    //向右滑出
    SlideOutRight(SlideOutRightAnimator.class),
    //向上滑出
    SlideOutUp(SlideOutUpAnimator.class),
    //向下滑出
    SlideOutDown(SlideOutDownAnimator.class),
    //闪动
    Flash(FlashAnimator.class),
    //慢慢变大变小
    Pulse(PulseAnimator.class),
    //大小伸缩弹性
    RubberBand(RubberBandAnimator.class),
    //中心点不动左右晃，类似跷跷板
    Swing(SwingAnimator.class),
    //左右晃动，类似撞墙
    Shake(ShakeAnimator.class),
    //左右晃动，幅度大
    Wobble(WobbleAnimator.class),
    //上下晃动
    Bounce(BounceAnimator.class),
    //tada 的效果
    Tada(TadaAnimator.class),
    //起立
    StandUp(StandUpAnimator.class),
    //波动
    Wave(WaveAnimator.class),
    //从左滑动进
    RollIn(RollInAnimator.class),
    //从右滑动出
    RollOut(RollOutAnimator.class),

    //逐渐出现
    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),
    //逐渐消失
    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),
    //旋转 向内
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),
    //旋转 向外
    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);


    private Class animatorClazz;

    private Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
