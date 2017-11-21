package com.kawakp.kp.kernel.plc.siemens;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/11/21
 * 修改人:penghui.li
 * 修改时间:2017/11/21
 * 修改内容:
 *
 * 功能描述:西门子后台服务
 */

public class SiemensService extends Service {

	private static final String SIEMENS_CLIENT_ID = "kawaSiemens";

	// These are the actions for the service (name are descriptive enough)
	private static final String ACTION_START = SIEMENS_CLIENT_ID + ".START";
	private static final String ACTION_STOP = SIEMENS_CLIENT_ID + ".STOP";

	//西门子同步更新时间
	private static final int UPDATE_TIME = 100;

	// Static method to start the service
	public static void actionStart(Context ctx) {
		Intent i = new Intent(ctx, SiemensService.class);
		i.setAction(ACTION_START);
		ctx.startService(i);
	}

	// Static method to stop the service
	public static void actionStop(Context ctx) {
		Intent i = new Intent(ctx, SiemensService.class);
		i.setAction(ACTION_STOP);
		ctx.startService(i);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		Log.e("SiemensService", "onStart");

		if (intent == null) {
			return;
		}

		if (intent.getAction().equals(ACTION_STOP)) {
			stop();
		} else if (intent.getAction().equals(ACTION_START)) {
			start();
		}

	}

	private synchronized void start() {
	}

	private synchronized void stop() {
	}

	@Override
	public void onDestroy() {
		stop();
		super.onDestroy();
	}
}
