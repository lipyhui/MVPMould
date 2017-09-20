package com.kawakp.kp.application.ui.activity.demo;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityDemoBinding;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:列表功能实现页面
 */

public class DemoActivity extends BaseActivity<DemoPresenter, ActivityDemoBinding> implements DemoAble {

	@Override
	public int getLayoutId() {
		return R.layout.activity_demo;
	}

	@Override
	public void init() {
		initBack();
	}

	@Override
	public void initTitle(String name) {
		mBinding.title.name.setText(name);
	}

	/**
	 * 初始化返回
	 */
	private void initBack() {
		mBinding.title.back.setOnClickListener(view -> onBackPressed());
	}
}
