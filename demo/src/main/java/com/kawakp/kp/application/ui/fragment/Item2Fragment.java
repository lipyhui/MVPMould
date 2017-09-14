package com.kawakp.kp.application.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kawakp.kp.application.databinding.FragmentTwoBinding;
import com.kawakp.kp.kernel.base.BaseBingingFragment;

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

public class Item2Fragment extends BaseBingingFragment<FragmentTwoBinding>{
	@NotNull
	@Override
	public FragmentTwoBinding createDataBinding(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return FragmentTwoBinding.inflate(inflater, container, false);
	}

	@Override
	public void initView() {
		Log.e("ItemFragment", "Item2Fragment two");
	}
}
