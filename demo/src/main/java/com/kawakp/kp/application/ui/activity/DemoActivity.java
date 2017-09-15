package com.kawakp.kp.application.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.databinding.ActivityDemoBinding;
import com.kawakp.kp.application.router.FunRouter;
import com.kawakp.kp.kernel.base.BaseBindingActivity;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:列表功能实现页面
 */

public class DemoActivity extends BaseBindingActivity<ActivityDemoBinding> {

	@NotNull
	@Override
	public ActivityDemoBinding createDataBinding(Bundle savedInstanceState) {
		return DataBindingUtil.setContentView(this, R.layout.activity_demo);
	}

	@Override
	public void initView() {
		FunRouter funRouter = (FunRouter) getIntent().getSerializableExtra("TARGET_ROUTER");
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment, funRouter.getTarget())
				.commit();

		mBinding.title.name.setText(funRouter.getItemName());

		initBack();
	}

	/**
	 * 初始化返回
	 */
	private void initBack() {
		mBinding.title.back.setOnClickListener(view -> onBackPressed());
	}
}
