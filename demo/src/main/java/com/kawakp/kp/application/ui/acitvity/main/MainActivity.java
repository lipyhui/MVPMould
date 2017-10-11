package com.kawakp.kp.application.ui.acitvity.main;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainAble {

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void init() {
	}

	@Override
	public void setData(String data) {
		mBinding.setHello(data);
	}

	@Override
	public void onStateViewRetryListener() {
		showContent();
	}
}
