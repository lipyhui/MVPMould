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
 * 功能描述: PLC 读响请求数据
 *
 * @param BOOL 布尔数据类型
 * @param WORD  WORD 数据类型
 * @param DWORD DWORD 数据类型
 * @param INT INT 数据类型
 * @param DINT DINT 数据类型
 * @param REAL REAL 数据类型
 */
@Suppress("UNCHECKED_CAST")
data class ReadPLCRequest constructor(var BOOL: ArrayList<String> = ArrayList(),
                                      var WORD: ArrayList<String> = ArrayList(),
                                      var DWORD: ArrayList<String> = ArrayList(),
                                      var INT: ArrayList<String> = ArrayList(),
                                      var DINT: ArrayList<String> = ArrayList(),
                                      var REAL: ArrayList<String> = ArrayList()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readArrayList(ArrayList::class.java.classLoader) as ArrayList<String>,
            parcel.readArrayList(ArrayList::class.java.classLoader) as ArrayList<String>,
            parcel.readArrayList(ArrayList::class.java.classLoader) as ArrayList<String>,
            parcel.readArrayList(ArrayList::class.java.classLoader) as ArrayList<String>,
            parcel.readArrayList(ArrayList::class.java.classLoader) as ArrayList<String>,
            parcel.readArrayList(ArrayList::class.java.classLoader) as ArrayList<String>)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(BOOL)
        parcel.writeList(WORD)
        parcel.writeList(DWORD)
        parcel.writeList(INT)
        parcel.writeList(DINT)
        parcel.writeList(REAL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReadPLCRequest> {
        override fun createFromParcel(parcel: Parcel): ReadPLCRequest {
            return ReadPLCRequest(parcel)
        }

        override fun newArray(size: Int): Array<ReadPLCRequest?> {
            return arrayOfNulls(size)
        }
    }
}