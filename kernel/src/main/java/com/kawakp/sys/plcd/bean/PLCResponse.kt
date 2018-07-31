package com.kawakp.sys.plcd.bean

import android.os.Parcel
import android.os.Parcelable
import com.kawakp.kp.kernel.plc.kawa.Element

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
 *                   -2：读写请求数据空
 *                   -3：不支持的元件/元件类型
 *                   -4：读写请求数据溢出
 *                   -5：响应接收失败
 *                   -6：校验失败
 *                   -7：写数据失败
 *                   -8：数据解析失败
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

    /**
     * 获取元件数据，如果参数错误可能会抛出异常
     *
     * @param dataType 数据类型
     * @param elementType 元件类型
     * @param addr 元件地址
     * @return PLC 元件值
     */
    fun getValue(dataType: Element.DATA_TYPE, elementType: Element.ELEMENT_TYPE, addr: Int): Any {
        return when (dataType) {
        //获取 BOOL 类型元件数据
            Element.DATA_TYPE.BOOL -> getBool(Element.BOOL.valueOf(elementType.name), addr)

        //获取 WORD 类型元件数据
            Element.DATA_TYPE.WORD -> getWord(Element.WORD.valueOf(elementType.name), addr)

        //获取 DWORD 类型元件数据
            Element.DATA_TYPE.DWORD -> getDWord(Element.DWORD.valueOf(elementType.name), addr)

        //获取 INT 类型元件数据
            Element.DATA_TYPE.INT -> getInt(Element.INT.valueOf(elementType.name), addr)

        //获取 DINT 类型元件数据
            Element.DATA_TYPE.DINT -> getDInt(Element.DINT.valueOf(elementType.name), addr)

        //获取 REAL 类型元件数据
            Element.DATA_TYPE.REAL -> getReal(Element.REAL.valueOf(elementType.name), addr)

            else -> {
                0
            }
        }
    }

    /**
     * 获取布尔(BOOL)元件的值
     *
     * @param element 布尔(BOOL)元件类型
     * @param addr    元件地址
     * @return 布尔(BOOL)元件值，默认 false
     */
    fun getBool(element: Element.BOOL, addr: Int): Boolean {
        val key = "${element.name}$addr"
        if (data.containsKey(key) && data[key] is Boolean) {
            return data[key].toString().toBoolean()
        }

        return false
    }

    /**
     * 获取字(WORD)元件的值
     *
     * @param element 字(WORD)元件类型
     * @param addr    元件地址
     * @return 字(WORD)元件值，默认 0
     */
    fun getWord(element: Element.WORD, addr: Int): Int {
        val key = "${element.name}$addr"
        if (data.containsKey(key) && data[key] is Int) {
            return data[key].toString().toInt()
        }

        return 0
    }

    /**
     * 获取双字(DWORD)元件的值
     *
     * @param element 双字(DWORD)元件类型
     * @param addr    元件地址
     * @return 双字(DWORD)元件值，默认 0
     */
    fun getDWord(element: Element.DWORD, addr: Int): Long {
        val key = "${element.name}$addr"
        if (data.containsKey(key) && data[key] is Long) {
            return data[key].toString().toLong()
        }

        return 0
    }

    /**
     * 获取字(INT)元件的值
     *
     * @param element 字(INT)元件类型
     * @param addr    元件地址
     * @return 字(INT)元件值，默认 0
     */
    fun getInt(element: Element.INT, addr: Int): Int {
        val key = "${element.name}$addr"
        if (data.containsKey(key) && data[key] is Int) {
            return data[key].toString().toInt()
        }

        return 0
    }

    /**
     * 获取双字(DINT)元件的值
     *
     * @param element 双字(DINT)元件类型
     * @param addr    元件地址
     * @return 双字(DINT)元件值，默认 0
     */
    fun getDInt(element: Element.DINT, addr: Int): Long {
        val key = "${element.name}$addr"
        if (data.containsKey(key) && data[key] is Long) {
            return data[key].toString().toLong()
        }

        return 0
    }

    /**
     * 获取双字(REAL)元件的值
     *
     * @param element 双字(REAL)元件类型
     * @param addr    元件地址
     * @return 双字(REAL)元件值，默认 0
     */
    fun getReal(element: Element.REAL, addr: Int): Float {
        val key = "${element.name}$addr"
        if (data.containsKey(key) && data[key] is Float) {
            return data[key].toString().toFloat()
        }

        return 0F
    }
}