

package com.kawakp.kp.animationlib;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

public class Play {

    private static final long DURATION = BaseViewAnimator.DURATION;//持续时间
    private static final long NO_DELAY = 0;//没有延迟
    public static final int INFINITE = -1;//无限
    public static final float CENTER_PIVOT = Float.MAX_VALUE;//中心

    private BaseViewAnimator animator;
    private long duration;
    private long delay;
    private boolean repeat;
    private int repeatTimes;
    private int repeatMode;
    private Interpolator interpolator;
    private float pivotX, pivotY;
    private List<Animator.AnimatorListener> callbacks;
    private View target;

    private Play(AnimationComposer animationComposer) {
        animator = animationComposer.animator;
        duration = animationComposer.duration;
        delay = animationComposer.delay;
        repeat = animationComposer.repeat;
        repeatTimes = animationComposer.repeatTimes;
        repeatMode = animationComposer.repeatMode;
        interpolator = animationComposer.interpolator;
        pivotX = animationComposer.pivotX;
        pivotY = animationComposer.pivotY;
        callbacks = animationComposer.callbacks;
        target = animationComposer.target;
    }

    /**
     * 选择播放的动画
     */
    public static AnimationComposer with(Techniques techniques) {
        return new AnimationComposer(techniques);
    }

    public static AnimationComposer with(BaseViewAnimator animator) {
        return new AnimationComposer(animator);
    }

    public interface AnimatorCallback {
        public void call(Animator animator);
    }

    private static class EmptyAnimatorListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

    public static final class AnimationComposer {

        private List<Animator.AnimatorListener> callbacks = new ArrayList<>();

        private BaseViewAnimator animator;
        private long duration = DURATION;

        private long delay = NO_DELAY;
        private boolean repeat = false;
        private int repeatTimes = 0;
        private int repeatMode = ValueAnimator.RESTART;
        private float pivotX = Play.CENTER_PIVOT, pivotY = Play.CENTER_PIVOT;
        private Interpolator interpolator;
        private View target;

        private AnimationComposer(Techniques techniques) {
            this.animator = techniques.getAnimator();
        }

        private AnimationComposer(BaseViewAnimator animator) {
            this.animator = animator;
        }

        public AnimationComposer duration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * 动画的延迟
         */
        public AnimationComposer delay(long delay) {
            this.delay = delay;
            return this;
        }

        public AnimationComposer interpolate(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        /**
         * 中心
         */
        public AnimationComposer pivot(float pivotX, float pivotY) {
            this.pivotX = pivotX;
            this.pivotY = pivotY;
            return this;
        }

        public AnimationComposer pivotX(float pivotX) {
            this.pivotX = pivotX;
            return this;
        }

        public AnimationComposer pivotY(float pivotY) {
            this.pivotY = pivotY;
            return this;
        }

        /**
         * 重播
         */
        public AnimationComposer repeat(int times) {
            if (times < INFINITE) {
                throw new RuntimeException("Can not be less than -1, -1 is infinite loop");
            }
            repeat = times != 0;
            repeatTimes = times;
            return this;
        }

        public AnimationComposer repeatMode(int mode) {
            repeatMode = mode;
            return this;
        }

        /**
         * 动画带监听播放
         */
        public AnimationComposer withListener(Animator.AnimatorListener listener) {
            callbacks.add(listener);
            return this;
        }

        /**
         * 动画开始
         */
        public AnimationComposer onStart(final AnimatorCallback callback) {
            callbacks.add(new EmptyAnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    callback.call(animation);
                }
            });
            return this;
        }

        /**
         * 动画结束
         */
        public AnimationComposer onEnd(final AnimatorCallback callback) {
            callbacks.add(new EmptyAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    callback.call(animation);
                }
            });
            return this;
        }

        /**
         * 动画取消
         */
        public AnimationComposer onCancel(final AnimatorCallback callback) {
            callbacks.add(new EmptyAnimatorListener() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    callback.call(animation);
                }
            });
            return this;
        }

        /**
         * 动画重播
         */
        public AnimationComposer onRepeat(final AnimatorCallback callback) {
            callbacks.add(new EmptyAnimatorListener() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    callback.call(animation);
                }
            });
            return this;
        }

        /**
         *  动画需要播放的时候直接调用
         */
        public YString playOn(View target) {
            this.target = target;
            return new YString(new Play(this).play(), this.target);
        }

    }

    /**
     * YoYo string, you can use this string to control your YoYo.
     */
    public static final class YString {

        private BaseViewAnimator animator;
        private View target;

        private YString(BaseViewAnimator animator, View target) {
            this.target = target;
            this.animator = animator;
        }

        public boolean isStarted() {
            return animator.isStarted();
        }

        /**
         * 判断动画是否在运行
         */
        public boolean isRunning() {
            return animator.isRunning();
        }

        /**
         * 调用即停止，直接停止
         */
        public void stop() {
            stop(true);
        }

        /**
         * 调用即停止
         */
        public void stop(boolean reset) {
            animator.cancel();
            if (reset)
                animator.reset(target);
        }
    }

    /**
     * 对目标对象进行播放
     */
    private BaseViewAnimator play() {
        animator.setTarget(target);

        if (pivotX == Play.CENTER_PIVOT) {
            ViewCompat.setPivotX(target, target.getMeasuredWidth() / 2.0f);
        } else {
            target.setPivotX(pivotX);
        }
        if (pivotY == Play.CENTER_PIVOT) {
            ViewCompat.setPivotY(target, target.getMeasuredHeight() / 2.0f);
        } else {
            target.setPivotY(pivotY);
        }

        animator.setDuration(duration)
                .setRepeatTimes(repeatTimes)
                .setRepeatMode(repeatMode)
                .setInterpolator(interpolator)
                .setStartDelay(delay);

        if (callbacks.size() > 0) {
            for (Animator.AnimatorListener callback : callbacks) {
                animator.addAnimatorListener(callback);
            }
        }
        animator.animate();
        return animator;
    }

}
