package com.kawakp.kp.application.ui.fragment.anim;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentAnimResponseBinding;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:响应动画
 */

public class ResponseAnimFragment extends BaseFragment<EmptyPresenter, FragmentAnimResponseBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_anim_response;
    }

    @Override
    public void onFirstUserInvisible() {
        Log.e("Test", "onFirstUserInvisible");
        super.onFirstUserInvisible();
    }

    @Override
    protected void onFirstUserVisible() {
        Log.e("Test", "onFirstUserVisible");
        init();
    }

    @Override
    public void onUserVisible() {
        Log.e("Test", "onUserVisible");
        super.onUserVisible();
    }

    @Override
    public void onUserInvisible() {
        Log.e("Test", "onUserInvisible");
        super.onUserInvisible();
    }

    private void init() {
        Glide.with(getContext()).load(R.drawable.loading_1).into(mBinding.gifImg1);
        Glide.with(getContext()).load(R.drawable.loading_2).into(mBinding.gifImg2);
    }
}
