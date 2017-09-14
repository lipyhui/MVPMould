package com.kawakp.kp.application;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.kawakp.kp.application.databinding.ActivityMainBinding;
import com.kawakp.kp.application.ui.fragment.Item1Fragment;
import com.kawakp.kp.application.ui.fragment.Item2Fragment;
import com.kawakp.kp.kernel.base.BaseBindingActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding> {

	private List<Fragment> mFragments;

	@NotNull
	@Override
	public ActivityMainBinding createDataBinding(Bundle savedInstanceState) {
		return DataBindingUtil.setContentView(this, R.layout.activity_main);
	}

	@Override
	public void initView() {
		initFragments();

		mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return mFragments.get(position);
			}

			@Override
			public int getCount() {
				return mFragments.size();
			}
		});

		mBinding.viewPager.setOffscreenPageLimit(2);

		mBinding.item1.setOnClickListener(view -> mBinding.viewPager.setCurrentItem(0));
		mBinding.item2.setOnClickListener(view -> mBinding.viewPager.setCurrentItem(1));

	}

	private void initFragments(){
		mFragments = new ArrayList();
		mFragments.add(new Item1Fragment());
		mFragments.add(new Item2Fragment());
	}
}
