package com.kawakp.kp.application.ui.fragment.form;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentFormBinding;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:表格支持
 */

public class FormFragment extends BaseFragment<EmptyPresenter, FragmentFormBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_form;
    }


    @Override
    protected void onFirstUserVisible() {
    }
}
