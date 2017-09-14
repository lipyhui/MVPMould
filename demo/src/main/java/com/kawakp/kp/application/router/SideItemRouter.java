package com.kawakp.kp.application.router;

import android.support.v4.app.Fragment;

import com.kawakp.kp.application.ui.fragment.Item1Fragment;
import com.kawakp.kp.application.ui.fragment.Item2Fragment;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:侧边栏fragment功能列表配置
 */

public enum SideItemRouter {
	CHART("折线图", new Item1Fragment()),
	ANIM("动画", new Item2Fragment());

	private String mSideName;
	private Fragment mFragment;

	SideItemRouter(String name, Fragment fragment){
		mSideName = name;
		mFragment = fragment;
	}

	public String getSideName() {
		return mSideName;
	}

	public Fragment getFragment() {
		return mFragment;
	}
}
