package com.kawakp.kp.application.router;

import com.kawakp.kp.application.ui.activity.AnimActivity;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:侧边栏fragment功能列表配置
 */

public enum AnimItemRouter {
	CHART("响应动画", AnimActivity.class);

	private String mItemName;
	private Class mTarget;

	AnimItemRouter(String name, Class target){
		mItemName = name;
		mTarget = target;
	}

	public String getItemName() {
		return mItemName;
	}

	public String getClassName() {
		return mTarget.getName();
	}
}
