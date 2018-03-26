// IAppAidlInterface.aidl
package com.kawakp.sys.plcd;

// Declare any non-default types here with import statements

interface IAppAidlInterface {

	/**
	  * 读 PLC，以字符串方式交互数据
	  */
	 String readPlc(String data);

	/**
	  * 读写 PLC，以字符串方式交互数据
	  */
     String writePlc(String data);

	 /**
	  * 读写 PLC，以 byte 数组方式交互数据
	  */
     byte[] rwPLC(in byte[] data);
}
