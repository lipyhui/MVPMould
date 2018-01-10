package com.kawakp.kp.application.app;

import com.kawakp.kp.kernel.KpApplication;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/15
 * 修改人:penghui.li
 * 修改时间:2017/9/15
 * 修改内容:
 *
 * 功能描述:绑定异常捕获并重启
 */
public class KawakpApplication extends KpApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		//异常捕获初始化
		CrashHandler.getInstance().initCrashHandler(this); // 一定要先初始化
		Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());
	}

}
