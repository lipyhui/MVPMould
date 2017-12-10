package com.kawakp.kp.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kawakp.kp.kernel.plc.siemens.SiemensService;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/12/8
 * 修改人:penghui.li
 * 修改时间:2017/12/8
 * 修改内容:
 *
 * 功能描述:
 */

public class BootBroadcastReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("KAWABroadcastTAG", "BootBroadcastReceiver onReceive");
		SiemensService.actionStart(context);
	}
}
