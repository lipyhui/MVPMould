package com.kawakp.kp.application.router;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.kawakp.kp.application.R;
import com.kawakp.kp.application.app.KawakpApplication;
import com.kawakp.kp.application.ui.fragment.item1.Item1Fragment;
import com.kawakp.kp.application.ui.fragment.item2.Item2Fragment;

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
	CHART(R.string.side_char_img, R.string.side_char, new Item1Fragment()),
	ANIM(R.string.side_anim_img, R.string.side_anim, new Item2Fragment());

	private String mSideImg;
	private String mSideName;
	private Fragment mFragment;

	SideItemRouter(@StringRes int imgId, @StringRes int nameId, Fragment fragment){
		mSideImg = KawakpApplication.getContext().getResources().getString(imgId);
		mSideName = KawakpApplication.getContext().getResources().getString(nameId);
		mFragment = fragment;
	}

	public String getSideImg() {
		return mSideImg;
	}

	public String getSideName() {
		return mSideName;
	}

	public Fragment getFragment() {
		return mFragment;
	}
}
