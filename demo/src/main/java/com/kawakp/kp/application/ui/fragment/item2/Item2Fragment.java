package com.kawakp.kp.application.ui.fragment.item2;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentTwoBinding;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:
 */

public class Item2Fragment extends BaseFragment<Item2Presenter, FragmentTwoBinding> implements Item2Able{

	@Override
	public int getLayoutId() {
		return R.layout.fragment_two;
	}

	@Override
	protected void onFirstUserVisible() {
		Log.e("ItemFragment", "Item2Fragment onFirstUserVisible");
	}

	@Override
	public void setText(@NonNull String data) {
		mBinding.setTextTwo(data);
	}

	@Override
	public void onStateViewRetryListener() {
		showContent();
	}
}
