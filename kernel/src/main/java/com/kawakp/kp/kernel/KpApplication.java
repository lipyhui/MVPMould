package com.kawakp.kp.kernel;

import android.app.Application;
import android.content.Context;

import com.kawakp.kp.kernel.plc.siemens.SiemensService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/15
 * 修改人:penghui.li
 * 修改时间:2017/9/15
 * 修改内容:
 *
 * 功能描述:应用全局配置
 */

public class KpApplication extends Application {
	private static KpApplication mContext;
	/* 配置屏幕常亮*/
	private static boolean ScreenOn = true;
	/*获取数据库*/
	private static Realm REALM_INSTANCE;

	@Override
	public void onCreate()
	{
		super.onCreate();

		mContext = this;

		//初始化realm数据库
		REALM_INSTANCE = initRealm();


		SiemensService.actionStart(mContext);
	}

	/**
	 * 获取context
	 *
	 * @return 返回整个应用生命周期内的Context
	 */
	public static Context getContext(){
		return mContext;
	}

	/**
	 * 获取屏幕是否常亮
	 *
	 * @return 返回屏幕常亮配置，true:屏幕常亮 false:屏幕不常亮(一段时间熄屏)
	 */
	public static boolean isScreenOn() {
		return ScreenOn;
	}

	/**
	 * 设置屏幕是否常亮
	 *
	 * @param screenOn 配置屏幕是否常亮，true:屏幕常亮 false:屏幕不常亮(一段时间熄屏)
	 */
	public static void setScreenOn(boolean screenOn) {
		ScreenOn = screenOn;
	}

	/**
	 * 获取Realm数据库单列
	 *
	 * @return 返回Realm数据库实例
	 */
	public static Realm getRealmInstance() {
		return REALM_INSTANCE;
	}

	/**
	 * 	初始化Realm数据库，可以别子类继承修改相关配置
	 */
	protected Realm initRealm(){
		Realm.init(this);
		RealmConfiguration config = new RealmConfiguration.Builder()
				.schemaVersion(1)
				.name("KAWAKP.realm")
				.deleteRealmIfMigrationNeeded()
				.build();
		return Realm.getInstance(config);
	}
}
