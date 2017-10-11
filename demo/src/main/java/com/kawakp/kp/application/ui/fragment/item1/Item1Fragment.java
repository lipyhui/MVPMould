package com.kawakp.kp.application.ui.fragment.item1;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.databinding.FragmentOneBinding;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:
 */
public class Item1Fragment extends BaseFragment<Item1Presenter, FragmentOneBinding> implements Item1Able {

	@Override
	public int getLayoutId() {
		return R.layout.fragment_one;
	}

	@Override
	protected void onFirstUserVisible() {
		Log.e("ItemFragment", "Item1Fragment onFirstUserVisible");
	}

	/**
	 * 实现设置文本接口
	 */
	@Override
	public void setText(@NonNull String data) {
		mBinding.setTextOne(data);
	}

	/**
	 * 错误点击重试响应
	 */
	@Override
	public void onStateViewRetryListener() {
		showContent();
	}
}
