package com.kawakp.kp.application.ui.acitvity.main;

import android.util.Log;

import com.kawakp.kp.application.R;
import com.kawakp.kp.kernel.plc.SocketClient;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainAble {

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void init() {
		mBinding.testText.setOnClickListener(view -> {
			Log.e("socket_Test", "Socket Start!!!!!!!!!!!!!!!!!!");
			SocketClient.sendMsg("KAWA_test_socket")
			.subscribe(s -> Log.e("socket_Test", "END*************************** " + s));
		});
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
