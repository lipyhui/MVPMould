package com.kawakp.kp.kernel.utils

import android.util.Log

/**
 * 创建人: penghui.li
 * 创建时间: 2018/3/3
 * 修改人:penghui.li
 * 修改时间:2018/3/3
 * 修改内容:
 *
 * 功能描述: Log 统一管理类，添加 使能参数
 */
object Logger {

    fun dByte(enable: Boolean, tag: String, msg: String, byte: Byte) {
        if (enable) {
            Log.d(tag, "$msg: ${Integer.toHexString(byte.toInt() and 0XFF)}")
        }
    }

    /**
     * 打印一组 byteArray
     */
    fun dByteArray(enable: Boolean, tag: String, msg: String, byteArray: ByteArray) {
        if (enable) {
            var info = "$msg: "
            for (byte in byteArray) {
//                Log.d(tag, "$msg: ${Integer.toHexString(byte.toInt() and 0XFF)}")
                info += "${Integer.toHexString(byte.toInt() and 0XFF)} "
            }

            Log.d(tag, info)
        }
    }

    /**
     * 打印一组 byteArray
     */
    fun eByteArray(enable: Boolean, tag: String, msg: String, byteArray: ByteArray) {
        if (enable) {
            var info = "$msg: "
            for (byte in byteArray) {
//                Log.e(tag, "$msg: ${Integer.toHexString(byte.toInt() and 0XFF)}")
                info += "${Integer.toHexString(byte.toInt() and 0XFF)} "
            }

            Log.e(tag, info)
        }
    }

    fun v(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            Log.v(tag, msg)
        }
    }

    fun d(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            Log.d(tag, msg)
        }
    }

    fun i(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            Log.i(tag, msg)
        }
    }

    fun w(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            Log.w(tag, msg)
        }
    }

    fun e(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            Log.e(tag, msg)
        }
    }

    fun e(enable: Boolean, tag: String, msg: String, tr: Throwable) {
        if (enable) {
            Log.e(tag, msg, tr)
        }
    }

    fun wtf(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            Log.wtf(tag, msg)
        }
    }

    fun wtf(enable: Boolean, tag: String, msg: String, tr: Throwable) {
        if (enable) {
            Log.wtf(tag, msg, tr)
        }
    }
}