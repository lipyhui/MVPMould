package com.kawakp.kp.application.ui.fragment;

import android.util.Log;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.databinding.FragmentTwoBinding;
import com.kawakp.kp.kernel.base.BaseBindingFragment;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:
 */

public class Item2Fragment extends BaseBindingFragment<FragmentTwoBinding>{

	@Override
	public int getLayoutId() {
		return R.layout.fragment_two;
	}

	@Override
	protected void onFirstUserVisible() {
		Log.e("ItemFragment", "Item2Fragment onFirstUserVisible");
	}
}
