package com.kawakp.kp.application;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kawakp.kp.application.databinding.ActivityMainBinding;
import com.kawakp.kp.kernel.KernelJNI;
import com.kawakp.kp.kernel.base.BaseBindingActivity;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding> {

	@NotNull
	@Override
	public ActivityMainBinding createDataBinding(Bundle savedInstanceState) {
		return DataBindingUtil.setContentView(this, R.layout.activity_main);
	}

	@Override
	public void initView() {
		mBinding.testText.setText(KernelJNI.stringFromJNI());
	}
}
