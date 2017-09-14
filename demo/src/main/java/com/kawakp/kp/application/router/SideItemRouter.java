package com.kawakp.kp.application.router;

import android.support.v4.app.Fragment;

import com.kawakp.kp.application.ui.fragment.MainFragment;

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
	CHART(1, "折线图"),
	ANIM(2, "动画");

	private int mType = 1;
	private String mSideName = "折线图";

	SideItemRouter(int type, String name){
		mSideName = name;
		mType = type;
	}

	public String getSideName() {
		return mSideName;
	}

	public Fragment getFragment() {
		return MainFragment.Companion.newInstance(mType);
	}
}
