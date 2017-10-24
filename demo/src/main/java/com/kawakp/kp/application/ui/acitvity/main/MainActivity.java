package com.kawakp.kp.application.ui.acitvity.main;

import android.util.Log;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityMainBinding;
import com.kawakp.kp.kernel.plc.PLCElement;
import com.kawakp.kp.kernel.plc.PLCManager;

public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainAble {

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void init() {
		mBinding.testText.setOnClickListener(view -> {
			Log.e("socket_Test", "Socket Start!!!!!!!!!!!!!!!!!!");
	/*		List<PLCElement.ElementBOOL> list = new ArrayList<PLCElement.ElementBOOL>();
			for (int i = 0; i < 1000; i ++){
				list.add(new PLCElement.ElementBOOL(PLCElement.BOOL.X, i));
			}*/

			new PLCManager.ReadBuilder()
//					.readBoolList(list)
					.readBool(PLCElement.BOOL.X, 15)
					.readBool(PLCElement.BOOL.X, 16)
					.readBool(PLCElement.BOOL.X, 17)
					.readBool(PLCElement.BOOL.X, 18)
					.readBool(PLCElement.BOOL.X, 19)
					.readBool(PLCElement.BOOL.X, 20)
					.readWord(PLCElement.WORD.D, 21)
					.readWord(PLCElement.WORD.D, 22)
					.readWord(PLCElement.WORD.D, 23)
					.readWord(PLCElement.WORD.D, 24)
					.readDWord(PLCElement.DWORD.D, 25)
					.readDWord(PLCElement.DWORD.D, 27)
					.readREAL(PLCElement.REAL.D, 29)
					.readREAL(PLCElement.REAL.D, 31)
					.readREAL(PLCElement.REAL.D, 33)
					.build()
					.startAsync();
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
