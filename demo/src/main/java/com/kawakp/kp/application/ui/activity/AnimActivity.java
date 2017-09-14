package com.kawakp.kp.application.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.databinding.ActivityAnimBinding;
import com.kawakp.kp.kernel.base.BaseBindingActivity;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:
 */

public class AnimActivity extends BaseBindingActivity<ActivityAnimBinding>{
	@NotNull
	@Override
	public ActivityAnimBinding createDataBinding(Bundle savedInstanceState) {
		return DataBindingUtil.setContentView(this, R.layout.activity_anim);
	}

	@Override
	public void initView() {
	}
}
