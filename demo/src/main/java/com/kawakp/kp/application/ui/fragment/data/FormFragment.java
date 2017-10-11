package com.kawakp.kp.application.ui.fragment.data;

import static com.kawakp.kp.kernel.utils.RealmManager.getInstance;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.app.KawakpApplication;
import com.kawakp.kp.application.base.BaseFragment;
import com.kawakp.kp.application.bean.FormItem;
import com.kawakp.kp.application.databinding.FragmentFormBinding;
import com.kawakp.kp.application.ui.adapter.FormAdapter;
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter;
import com.kawakp.kp.kernel.utils.RealmManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

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

		List<FormItem> list = new ArrayList<FormItem>();
		for (int i = 0; i < 10; i++) {
			list.add(new FormItem(false, "2017-04-07 03:00:0" + i, "12.0" + i, "13.0" + i,
					"14.0" + i,  "15.0" + i));
		}
		for (int i = 10; i < 20; i++) {
			list.add(new FormItem(false, "2017-04-07 03:00:" + i, "12." + i, "13." + i,
					"14." + i,  "15." + i));
		}

		Realm realm = KawakpApplication.getRealmInstance();

		if (realm != null) {
			Log.e("RealmTest", "realm is Succeed!!! list size = " + list.size());
			/*数据存储到数据库*/
			getInstance(realm).add(list);

			/*从数据库读取数据*/
			RealmManager.getInstance(realm).queryAllToList(FormItem.class, mList);
			mAdapter.notifyDataSetChanged();

		}else {
			Log.e("RealmTest", "realm is NULL!!!");
		}
	}
}
