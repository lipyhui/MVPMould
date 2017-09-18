package com.kawakp.kp.application;

import com.kawakp.kp.application.databinding.ActivityMainBinding;
import com.kawakp.kp.kernel.KernelJNI;
import com.kawakp.kp.kernel.base.BaseBindingActivity;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding> {

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void initView() {
		mBinding.testText.setText(KernelJNI.stringFromJNI());
	}
}
