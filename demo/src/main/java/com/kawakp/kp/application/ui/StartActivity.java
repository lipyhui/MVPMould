package com.kawakp.kp.application.ui;

import android.content.Intent;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseActivity;
import com.kawakp.kp.application.databinding.ActivityLaunchBinding;
import com.kawakp.kp.application.ui.activity.main.MainActivity;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:启动界面
 */
public class StartActivity extends BaseActivity<EmptyPresenter, ActivityLaunchBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    public void init() {
        redirectTo();
    }

    /**
     * 3秒后跳转到主页
     */
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
