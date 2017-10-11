package com.kawakp.kp.kernel;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:	与JNI建立连接的方法，提供可以供Java调用方法
 */

public class KernelJNI {
	static {
		System.loadLibrary("kernel-native-lib");
	}

	/**
	 * 测试C++返回一个字符串，Java调用该native方法来获取C++的返回内容
	 */
	public static native String stringFromJNI();
}
