package com.kawakp.kp.application.ui.fragment.anim;

import android.animation.Animator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentSimpleAnimBinding;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;
import com.kawakp.shengqi.animationlib.Play;
import com.kawakp.shengqi.animationlib.Techniques;

/**
 * Created by sheng.qi on 2017/9/29.
 */

public class SimpleAnimFragment extends BaseFragment<EmptyPresenter, FragmentSimpleAnimBinding> {
    private Play.YoYoString rope;
    private View mTarget;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_simple_anim;
    }

    @Override
    protected void onFirstUserVisible() {
        initAmin();
    }

    private void initAmin() {
        mBinding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAmin(Techniques.SlideInLeft);
            }
        });
        mBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAmin(Techniques.SlideOutRight);
            }
        });
        mBinding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAmin(Techniques.Flash);
            }
        });
        mBinding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAmin(Techniques.Wobble);
            }
        });
        mBinding.btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAmin(Techniques.FadeOutDown);
            }
        });
        mBinding.btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rope != null) {
                    rope.stop();
                    rope = Play.with(Techniques.RotateIn)
                            .duration(1200)
                            .pivot(Play.CENTER_PIVOT, Play.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    Play.with(Techniques.ZoomOutLeft).duration(1500).playOn(mBinding.mTarget);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            }).playOn(mBinding.mTarget);
                } else {
                    rope = Play.with(Techniques.RotateIn)
                            .duration(1200)
                            .pivot(Play.CENTER_PIVOT, Play.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    Play.with(Techniques.ZoomOutLeft).duration(1500).playOn(mBinding.mTarget);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            }).playOn(mBinding.mTarget);
                }
            }
        });
    }

    private void playAmin(Techniques amin) {
        if (rope != null) {
            rope.stop();
            rope = Play.with(amin).duration(1200).playOn(mBinding.mTarget);
        } else {
            rope = Play.with(amin).duration(1200).playOn(mBinding.mTarget);
        }
    }

}
