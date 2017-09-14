package com.kawakp.kp.application.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kawakp.kp.application.databinding.FragmentOneBinding;
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

public class Item1Fragment extends BaseBingingFragment<FragmentOneBinding>{
	@NotNull
	@Override
	public FragmentOneBinding createDataBinding(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return FragmentOneBinding.inflate(inflater, container, false);
	}

	@Override
	public void initView() {
		Log.e("ItemFragment", "Item1Fragment one");
	}
}
