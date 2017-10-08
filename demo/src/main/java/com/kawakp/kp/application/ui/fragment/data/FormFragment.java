package com.kawakp.kp.application.ui.fragment.data;

import android.support.v7.widget.LinearLayoutManager;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.bean.FormItem;
import com.kawakp.kp.application.databinding.FragmentFormBinding;
import com.kawakp.kp.application.ui.adapter.FormAdapter;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 * <p>
 * 功能描述:表格支持
 */

public class FormFragment extends BaseFragment<EmptyPresenter, FragmentFormBinding> {

	private FormAdapter mAdapter;
	private List<FormItem> mList = new ArrayList<FormItem>();

	@Override
	public int getLayoutId() {
		return R.layout.fragment_form;
	}


	@Override
	protected void onFirstUserVisible() {
		mAdapter = new FormAdapter(mList);
		mBinding.data.setAdapter(mAdapter);
		mBinding.data.setLayoutManager(new LinearLayoutManager(getContext()));

		for (int i = 0; i < 10; i++) {
			mList.add(new FormItem(false, "2017-04-07 03:00:0" + i, "12.0" + i, "13.0" + i,
					"14.0" + i,  "15.0" + i));
		}
		for (int i = 10; i < 20; i++) {
			mList.add(new FormItem(false, "2017-04-07 03:00:" + i, "12." + i, "13." + i,
					"14." + i,  "15." + i));
		}
		mAdapter.notifyDataSetChanged();
	}
}
