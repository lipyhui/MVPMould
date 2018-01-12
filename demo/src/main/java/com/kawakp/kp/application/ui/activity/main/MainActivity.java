package com.kawakp.kp.application.ui.activity.main;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityMainBinding;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:主界面
 */
public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainAble {

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void init() {
	}

	/**
	 * 实现设置字符串接口
	 */
	@Override
	public void setData(String data) {
		mBinding.setHello(data);
	}

	/**
	 * 点击重试响应
	 */
	@Override
	public void onStateViewRetryListener() {
		showContent();
	}
}
