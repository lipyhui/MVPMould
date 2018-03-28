package com.kawakp.sys.plcd.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/23
 * 修改人:penghui.li
 * 修改时间:2017/10/23
 * 修改内容:
 *
 * 功能描述:PLC读写响应数据
 *         respCode: 响应代码，默认为 -100
 *                   0：成功
 *                   -1：连接失败
 *                   -2：读写请求数据为空
 *                   -3：一次读写数据过多
 *                   -4：响应接收失败
 *                   -5：校验失败
 *                   -6：数据解析失败
 *                   -100：未知原因失败
 *         respMsg: 响应提示消息
 *         data: 返回数据，默认长度为 0,类型为对应数据类型(BOOL - Boolean, INT、DINT、WORD、DWORD - Int, REAL - Float)
 */
@Suppress("UNCHECKED_CAST")
data class PLCResponse constructor(var respCode: Int = -100,
                                   var respMsg: String = "未知原因失败",
                                   var data: HashMap<String, Any> = HashMap()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readHashMap(HashMap::class.java.classLoader) as HashMap<String, Any>)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(respCode)
        parcel.writeString(respMsg)
        parcel.writeMap(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PLCResponse> {
        override fun createFromParcel(parcel: Parcel): PLCResponse {
            return PLCResponse(parcel)
        }

        override fun newArray(size: Int): Array<PLCResponse?> {
            return arrayOfNulls(size)
        }
    }
}