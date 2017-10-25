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

	new PLCManager.WriteBuilder()
					.writeBool(PLCElement.BOOL.Y, 0, true)
					.writeBool(PLCElement.BOOL.Y, 1, true)
					.writeBool(PLCElement.BOOL.Y, 2, true)
					.writeBool(PLCElement.BOOL.Y, 3, true)
					.writeBool(PLCElement.BOOL.Y, 4, false)
					.writeBool(PLCElement.BOOL.Y, 5, true)
					.writeBool(PLCElement.BOOL.Y, 6, true)
					.writeBool(PLCElement.BOOL.Y, 7, false)
					.writeBool(PLCElement.BOOL.Y, 8, true)
				/*	.writeWord(PLCElement.WORD.D, 4, 15)
					.writeWord(PLCElement.WORD.D, 5, 16)
					.writeWord(PLCElement.WORD.D, 6, 17)
					.writeDWord(PLCElement.DWORD.D, 7, 18)
					.writeDWord(PLCElement.DWORD.D, 9, 19)
					.writeDWord(PLCElement.DWORD.D, 11, 20)
					.writeReal(PLCElement.REAL.D, 13, 20.5F)
					.writeReal(PLCElement.REAL.D, 15, 25.4F)
					.writeReal(PLCElement.REAL.D, 17, 39.8F)*/
					.build()
					.startAsync();

			new PLCManager.ReadBuilder()
//					.readBoolList(list)
					.readBool(PLCElement.BOOL.Y, 0)
					.readBool(PLCElement.BOOL.Y, 1)
					.readBool(PLCElement.BOOL.Y, 2)
					.readBool(PLCElement.BOOL.Y, 3)
					.readBool(PLCElement.BOOL.Y, 4)
					.readBool(PLCElement.BOOL.Y, 5)
					.readBool(PLCElement.BOOL.Y, 6)
					.readBool(PLCElement.BOOL.Y, 7)
					.readBool(PLCElement.BOOL.Y, 8)
//					.readWord(PLCElement.WORD.D, 21)
//					.readWord(PLCElement.WORD.D, 22)
//					.readWord(PLCElement.WORD.D, 23)
//					.readWord(PLCElement.WORD.D, 24)
//					.readDWord(PLCElement.DWORD.D, 25)
//					.readDWord(PLCElement.DWORD.D, 27)
//					.readReal(PLCElement.REAL.D, 29)
//					.readReal(PLCElement.REAL.D, 31)
//					.readReal(PLCElement.REAL.D, 33)
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
