package com.kawakp.kp.application.ui.fragment.anim;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentGifBinding;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:Gif支持
 */

public class GifFragment extends BaseFragment<EmptyPresenter, FragmentGifBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gif;
    }


    @Override
    protected void onFirstUserVisible() {
    }
}
