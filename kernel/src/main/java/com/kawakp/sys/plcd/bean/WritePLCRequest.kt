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
 * 功能描述: PLC 写请求数据
 *
 * @param BOOL 布尔数据类型 —— Boolean
 * @param WORD  WORD 数据类型 —— Int
 * @param DWORD DWORD 数据类型 —— Int
 * @param INT INT 数据类型 —— Int
 * @param DINT DINT 数据类型 —— Int
 * @param REAL REAL 数据类型 —— Float
 */
@Suppress("UNCHECKED_CAST")
data class WritePLCRequest constructor(var BOOL: HashMap<String, Boolean> = HashMap(),
                                       var WORD: HashMap<String, Int> = HashMap(),
                                       var DWORD: HashMap<String, Int> = HashMap(),
                                       var INT: HashMap<String, Int> = HashMap(),
                                       var DINT: HashMap<String, Int> = HashMap(),
                                       var REAL: HashMap<String, Float> = HashMap()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readHashMap(HashMap::class.java.classLoader) as HashMap<String, Boolean>,
            parcel.readHashMap(HashMap::class.java.classLoader) as HashMap<String, Int>,
            parcel.readHashMap(HashMap::class.java.classLoader) as HashMap<String, Int>,
            parcel.readHashMap(HashMap::class.java.classLoader) as HashMap<String, Int>,
            parcel.readHashMap(HashMap::class.java.classLoader) as HashMap<String, Int>,
            parcel.readHashMap(HashMap::class.java.classLoader) as HashMap<String, Float>)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeMap(BOOL)
        parcel.writeMap(WORD)
        parcel.writeMap(DWORD)
        parcel.writeMap(INT)
        parcel.writeMap(DINT)
        parcel.writeMap(REAL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WritePLCRequest> {
        override fun createFromParcel(parcel: Parcel): WritePLCRequest {
            return WritePLCRequest(parcel)
        }

        override fun newArray(size: Int): Array<WritePLCRequest?> {
            return arrayOfNulls(size)
        }
    }
}