// IAppAidlInterface.aidl
package com.kawakp.sys.plcd;

// Declare any non-default types here with import statements
import com.kawakp.sys.plcd.bean.PLCResponse;
import com.kawakp.sys.plcd.bean.ReadPLCRequest;
import com.kawakp.sys.plcd.bean.WritePLCRequest;

interface IAppAidlInterface {
	 /**
	  * 读 PLC，以类的方式交互数据
	  */
     PLCResponse readPlc(in ReadPLCRequest data);
	 /**
	  * 写 PLC，以类的方式交互数据
	  */
     PLCResponse writePlc(in WritePLCRequest data);
}
