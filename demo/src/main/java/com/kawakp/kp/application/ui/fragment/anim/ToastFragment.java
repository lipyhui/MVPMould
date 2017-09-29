package com.kawakp.kp.application.ui.fragment.anim;

import android.view.View;
import android.widget.Toast;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentToastBinding;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;
import com.kawakp.shengqi.kputilslib.toast.MyToast;

/**
 * 创建人: qi
 * 创建时间: 2017/9/1
 * 修改人:qi
 * 修改时间: 2017/9/1
 * 修改内容:
 * <p>
 * 功能描述: toast 的展示页面
 */
public class ToastFragment extends BaseFragment<EmptyPresenter, FragmentToastBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_toast;
    }

    @Override
    protected void onFirstUserVisible() {
        mBinding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.info(getContext(), "这是一条提示信息", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.error(getContext(), "这是一条错误信息", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.success(getContext(), "这是一条成功信息", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.warning(getContext(), "这是一条警告信息", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.normal(getContext(), "这是一条自定义信息").show();
            }
        });
    }
}
