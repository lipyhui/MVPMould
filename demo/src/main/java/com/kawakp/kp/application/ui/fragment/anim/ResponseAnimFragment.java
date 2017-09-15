package com.kawakp.kp.application.ui.fragment.anim;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kawakp.kp.application.databinding.FragmentAnimResponseBinding;
import com.kawakp.kp.kernel.base.BaseBingingFragment;

import org.jetbrains.annotations.NotNull;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:响应动画
 */

public class ResponseAnimFragment extends BaseBingingFragment<FragmentAnimResponseBinding>{
	@NotNull
	@Override
	public FragmentAnimResponseBinding createDataBinding(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return FragmentAnimResponseBinding.inflate(inflater, container, false);
	}

	@Override
	public void initView() {
		Log.e("ItemFragment", "Item2Fragment two");
	}
}
