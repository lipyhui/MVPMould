package com.kawakp.kp.kernel.utils

import android.util.Log
import java.io.File
import java.io.RandomAccessFile
import java.text.SimpleDateFormat

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

    //单条 logger 最大长度
    private const val MAX_LENGTH = 2000

    //logger 文件路径
    private const val LOGGER_PATH = "/storage/sdcard0/application/logger.txt"
    //logger 文件最大大小
    private const val LOGGER_MAX = 500 * 1024 * 1024
    private const val LOGGER_FILE_SWITCH = false

    fun dByte(enable: Boolean, tag: String, msg: String, byte: Byte) {
        if (enable) {
            val info = "$msg: ${byte2Str(byte)}"
            Log.d(tag, info)
            saveLogger("$tag: $info")
        }
    }

    /**
     * 打印一组 byteArray
     */
    fun dByteArray(enable: Boolean, tag: String, msg: String, byteArray: ByteArray) {
        if (enable) {
            var info = "$msg: "
            for (byte in byteArray) {
//                Log.d(tag, "$msg: ${byte2Str(byte)}")
                info += "${byte2Str(byte)} "
            }

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.d(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.d(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.d(tag, info)

            //消息结束
            if (msgSub) {
                Log.d(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    /**
     * 打印一组 byteArray
     */
    fun dByteArray(enable: Boolean, tag: String, msg: String, byteArray: ByteArray, len: Int) {
        if (enable) {
            if (len <= 0) {
                Log.d(tag, "$msg bytes len to short!! $len")
                saveLogger("$tag: $msg bytes len to short!! $len")
                return
            }

            var info = "$msg: "
            for (i in 0 until len) {
//                Log.d(tag, "$msg: ${byte2Str(byte)}")
                info += "${byte2Str(byteArray[i])} "
            }

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.d(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.d(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.d(tag, info)

            //消息结束
            if (msgSub) {
                Log.d(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    /**
     * 打印一组 byteArray
     */
    fun eByteArray(enable: Boolean, tag: String, msg: String, byteArray: ByteArray) {
        if (enable) {
            var info = "$msg: "
            for (byte in byteArray) {
//                Log.e(tag, "$msg: ${byte2Str(byte)}")
                info += "${byte2Str(byte)} "
            }

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.e(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.e(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.e(tag, info)

            //消息结束
            if (msgSub) {
                Log.e(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    /**
     * 打印一组 byteArray
     */
    fun eByteArray(enable: Boolean, tag: String, msg: String, byteArray: ByteArray, len: Int) {
        if (enable) {
            if (len <= 0) {
                Log.e(tag, "$msg bytes len to short!! $len")
                saveLogger("$tag: $msg bytes len to short!! $len")
                return
            }

            var info = "$msg: "
            for (i in 0 until len) {
//                Log.d(tag, "$msg: ${byte2Str(byte)}")
                info += "${byte2Str(byteArray[i])} "
            }

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.e(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.e(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.e(tag, info)

            //消息结束
            if (msgSub) {
                Log.e(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    fun v(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            var info = msg

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.v(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.v(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.v(tag, info)

            //消息结束
            if (msgSub) {
                Log.v(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    fun d(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            var info = msg

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.d(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.d(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.d(tag, info)

            //消息结束
            if (msgSub) {
                Log.d(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    fun i(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            var info = msg

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.i(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.i(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.i(tag, info)

            //消息结束
            if (msgSub) {
                Log.i(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    fun w(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            var info = msg

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.w(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.w(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.w(tag, info)

            //消息结束
            if (msgSub) {
                Log.w(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    fun e(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            var info = msg

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.e(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.e(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.e(tag, info)

            //消息结束
            if (msgSub) {
                Log.e(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    fun e(enable: Boolean, tag: String, msg: String, tr: Throwable) {
        if (enable) {
            var info = msg

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.e(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.e(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.e(tag, info, tr)

            //消息结束
            if (msgSub) {
                Log.e(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    fun wtf(enable: Boolean, tag: String, msg: String) {
        if (enable) {
            var info = msg

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.wtf(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.wtf(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.wtf(tag, info)

            //消息结束
            if (msgSub) {
                Log.wtf(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    fun wtf(enable: Boolean, tag: String, msg: String, tr: Throwable) {
        if (enable) {
            var info = msg

            saveLogger("$tag: $info")

            val maxStrLength = MAX_LENGTH - tag.length
            val msgSub = info.length > maxStrLength

            //消息开始
            if (msgSub) {
                Log.wtf(tag, "****************** start *******************")
            }

            //消息
            while (info.length > maxStrLength) {
                Log.wtf(tag, info.substring(0, maxStrLength))
                info = info.substring(maxStrLength)
            }
            Log.wtf(tag, info, tr)

            //消息结束
            if (msgSub) {
                Log.wtf(tag, ">>>>>>>>>>>>>>>>>>>> end >>>>>>>>>>>>>>>>>>>>")
            }
        }
    }

    /**
     * byte 转 16 进制字符串
     *
     * @param byte 需转字符串的 byte
     * @return 转后的字符串，16 进制显示
     */
    private fun byte2Str(byte: Byte): String {
        val unsignedByte = byte.toInt() and 0XFF
        return if (unsignedByte < 16) {
            "0${Integer.toHexString(unsignedByte)}"
        } else {
            Integer.toHexString(unsignedByte)
        }
    }

    /**
     * 保存 Log 信息到本地文件
     *
     * @param msg String
     */
    private fun saveLogger(msg: String) {
        if (LOGGER_FILE_SWITCH) {
            //防止日志文件过大
            val file = File(LOGGER_PATH)
            if (file.length() >= LOGGER_MAX) {
                file.delete()
            }

            //保存日志
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
            val info = "$time $msg"
            val log = RandomAccessFile(LOGGER_PATH, "rw")
            log.skipBytes(log.length().toInt())
            log.write("$info\n\r".toByteArray())
            log.close()
        }
    }
}