package com.kawakp.kp.kernel;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:	
 */

public class KernelJNI {
	static {
		System.loadLibrary("kernel-native-lib");
	}

	/**
	 * A native method that is implemented by the 'native-lib' native library,
	 * which is packaged with this application.
	 */
	public static native String stringFromJNI();
}
