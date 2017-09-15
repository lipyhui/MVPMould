package com.kawakp.kp.application;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kawakp.kp.application.databinding.ActivityLaunchBinding;
import com.kawakp.kp.kernel.base.BaseBindingActivity;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class StartActivity extends BaseBindingActivity<ActivityLaunchBinding> {

    @NotNull
    @Override
    public ActivityLaunchBinding createDataBinding(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_launch);
    }

    @Override
    public void initView() {
        redirectTo();
    }

    private void redirectTo() {
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(aLong -> {
                    startActivity(new Intent(this, MainActivity.class));
                    StartActivity.this.finish();
                });
    }
}
