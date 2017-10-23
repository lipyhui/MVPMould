package com.kawakp.kp.application.ui.acitvity.main;

import android.util.Log;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityMainBinding;
import com.kawakp.kp.kernel.plc.SocketClient;

public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainAble {

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void init() {
		mBinding.testText.setOnClickListener(view -> {
			byte[] data = new byte[]{0x08, (byte) 0x83, (byte) 0xc2, (byte) 0xd3, (byte) 0xf5, 0x08};
			Log.e("socket_Test", "Socket Start!!!!!!!!!!!!!!!!!!");
		/*	new PLCManager.ReadBuilder()
					.readBool(PLCElement.BOOL.X, 20)
					.build();*/
			SocketClient.sendMsg(data)
					.subscribe(s -> Log.e("socket_Test", "END***************************\n"));
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
