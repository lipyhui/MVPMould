package com.kawakp.kp.kernel.plc.siemens;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.List;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:	建立西门子服务守护进程
 */
public class SiemensProtectService extends Service {
    private TimeChangeReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
       // Log.e("ProtectService", "ProtectService");
        //服务启动广播接收器，使得广播接收器可以在程序退出后在后天继续执行，接收系统时间变更广播事件
        receiver = new TimeChangeReceiver();
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_TIME_TICK));

        Intent sendIntent = new Intent(getApplicationContext(), SiemensProtectService.class);
        AlarmManager mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent mPendingIntent = PendingIntent.getService(this, 0, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long now = System.currentTimeMillis();
        mAlarmManager.setInexactRepeating(AlarmManager.RTC, now, 1000*10, mPendingIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isServiceWork(getApplicationContext(), SiemensService.class.getName())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
					SiemensService.actionStart(SiemensProtectService.this);
                }
            }).start();
        }
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                startService(new Intent(SiemensProtectService.this, SiemensProtectService.class));
            }
        }).start();
        super.onDestroy();
    }

    public class TimeChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            //启动service
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                if (!isServiceWork(context, SiemensService.class.getName()))
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(1000);
							SiemensService.actionStart(context);
                        }
                    }).start();
            }

            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                if (!isServiceWork(context, SiemensService.class.getName()))
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(1000);
                            startService(new Intent(context, SiemensProtectService.class));
                        }
                    }).start();
            }
        }
    }

	/**
	 * 判断某个服务是否正在运行的方法
	 *
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	private boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}
}