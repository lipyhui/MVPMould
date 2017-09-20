package com.kawakp.kp.application.ui;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityMainBinding;
import com.kawakp.kp.kernel.KernelJNI;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<EmptyPresenter, ActivityMainBinding> {

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void onStateViewRetryListener() {
		showContent();
	}

	@Override
	public void init() {
		Observable.timer(3, TimeUnit.SECONDS)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(l -> {
					//					hideLoading();
//					showContent();
					stateViewError("error", "this is error");
					//showMessage("this is a test msg");
					mBinding.testText.setText(KernelJNI.stringFromJNI());
				});
	}
}
