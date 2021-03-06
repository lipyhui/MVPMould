package com.kawakp.kp.application.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.kawakp.kp.application.ui.StartActivity;
import com.kawakp.kp.kernel.plc.kawa.RWClientManager;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/4
 * 修改人:penghui.li
 * 修改时间:2017/8/4
 * 修改内容:
 *
 * 功能描述:捕获异常并重启
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

	public static CrashHandler mAppCrashHandler;

	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private KawakpApplication mAppContext;

	public static CrashHandler getInstance() {
		if (mAppCrashHandler == null) {
			mAppCrashHandler = new CrashHandler();
		}
		return mAppCrashHandler;
	}

	public void initCrashHandler(KawakpApplication application) {
		this.mAppContext = application;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			AlarmManager mgr = (AlarmManager) mAppContext.getSystemService(Context.ALARM_SERVICE);

			//1秒后重启
			Intent intent = new Intent(mAppContext, StartActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("crash", true);
			PendingIntent restartIntent = PendingIntent.getActivity(mAppContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用

			RWClientManager.INSTANCE.unregister();

			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			System.gc();
		}
	}

	/**
	 * 错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 自定义处理错误信息
		return true;
	}
}
