package com.kawakp.kp.application.ui.fragment.anim;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentPushBinding;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;
import com.kawakp.shengqi.kputilslib.guide.Direction;
import com.kawakp.shengqi.kputilslib.guide.GuidesView;
import com.kawakp.shengqi.kputilslib.toast.MyToast;

/**
 * 创建人: qi
 * 创建时间: 2017/9/1
 * 修改人:qi
 * 修改时间:2017/9/1
 * 修改内容:
 * <p>
 * 功能描述:点击引导
 */
public class GuideFragment extends BaseFragment<EmptyPresenter, FragmentPushBinding> {
    private String TAG = "--";
    private GuidesView mGVOne, mGVTwo, mGVThree;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_push;
    }

    @Override
    protected void onFirstUserVisible() {
        init();
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
    }

    private void init() {
        mBinding.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGuideViews();
            }
        });
        mBinding.btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.info(getContext(), "出现了错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showGuideViews() {
        TextView mHintViewOne = new TextView(getActivity());
        mHintViewOne.setText("这是一条提示你点击的文字");
        mHintViewOne.setTextSize(20f);
        mHintViewOne.setTextColor(Color.WHITE);
        TextView mHintView2 = new TextView(getActivity());
        mHintView2.setText("这是第二条提示你点击的文字");
        mHintView2.setTextSize(20f);
        mHintView2.setTextColor(Color.WHITE);
        TextView mHintView3 = new TextView(getActivity());
        mHintView3.setText("这是第三条提示你点击的文字");
        mHintView3.setTextSize(20f);
        mHintView3.setTextColor(Color.WHITE);
        mGVOne = new GuidesView.Builder(getActivity())
                .setTargetView(mBinding.text1)
                .setHintView(mHintViewOne)
                .setHintViewDirection(Direction.RIGHT_BOTTOM)
                .setTransparentOvalPadding(20)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVOne.hide();
                        mGVTwo.show();
                    }
                })
                .create();
        mGVOne.show();

        mGVTwo = new GuidesView.Builder(getActivity())
                .setTargetView(mBinding.text2)
                .setHintView(mHintView2)
                .setHintViewDirection(Direction.BOTTOM_ALIGN_LEFT)
                .setTransparentOvalPaddingLeft(20)
                .setTransparentOvalPaddingRight(20)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVTwo.hide();
                        mGVThree.show();
                    }
                })
                .create();

        mGVThree = new GuidesView.Builder(getActivity())
                .setTargetView(mBinding.text3)
                .setHintView(mHintView3)
                .setHintViewDirection(Direction.LEFT_BOTTOM)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVThree.hide();
                    }
                })
                .create();
    }
}
