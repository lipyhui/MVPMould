package com.kawakp.kp.kernel.plc

import android.util.Log
import com.kawakp.kp.kernel.utils.VerifyUtil
import com.kawakp.kp.kernel.utils.VerifyUtil.calcCrc16
import io.reactivex.Observable
import java.util.*

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/23
 * 修改人:penghui.li
 * 修改时间:2017/10/23
 * 修改内容:
 *
 * 功能描述:KAWA PLC读写操作(超出缓冲区，默认丢失后加入的元件):
 * 1、读一次最多读取 338 个位/字(BOOL、WORD)元件、169 个双字(DWORD、REAL)元件
 * 2、写一次最多 253 个位(BOOL)元件、202 个字(WORD)元件、101 个双字(DWORD、REAL)元件
 */

class PLCManager
/**
 * 构造方法
 *
 * @param mData            协议数据域
 * @param mVerify          协议校验
 * @param mBitElementName  位元件名称列表(按顺序)
 * @param mBitElementName 字元件名称列表(按顺序)
 * @param mWordElementName        字元件的数据类型列表(按顺序)
 * @param mBitCount        位元件个数
 */
private constructor(
        /** 待发送数据  */
        private val mData: ByteArray?,
        /** 数据校验信息  */
        private val mVerify: ByteArray?,
        /** 记录BOOL类型元件名称(按顺序排序),例：X100  */
        private val mBitElementName: List<String>?,
        /** 记录WORD类型元件名称(按顺序排序)，例：D100  */
        private val mWordElementName: List<String>?,
        /** WORD 数据的类型列表(按顺序排序)  */
        private val mWordType: List<TYPE>?,
        /** 位元件个数  */
        private val mBitCount: Int = 0) {

    /** 定义字数据类型  */
    private enum class TYPE {
        WORD,
        DWORD,
        REAL
    }

    /** PLC 读写数据监听，返回 PLC 读写结果 */
    interface OnPLCResponseListener {
        fun onPLCResponse(response: PLCResponse)
    }

    /**
     * 异步发送数据，不关心返回
     */
    fun startAsync() {
        start().subscribe{response -> Log.e("socket_Test_response", "code = ${response.responseCode}, msg = ${response.responseMsg}")}
    }

    /**
     * 开始发送数据并接收响应数据
     *
     * @param listener 监听返回响应数据
     */
    fun start(listener: OnPLCResponseListener) {
        start().subscribe { response -> listener.onPLCResponse(response) }
    }

    /**
     * 开始发送数据并接收响应数据,以 rxJava 方式返回
     *
     * @return 返回响应数据
     */
    fun start(): Observable<PLCResponse> {
        return SocketClient.sendMsg(mData, mVerify)
                .map { bytes ->
                    val response = verifyResponse(bytes)

                    Log.e("socket_Test_response", "bitCount = $mBitCount")

                    for (b in bytes) {
                        Log.e("socket_Test_response", "byte = ${Integer.toHexString(b.toInt())}")
                    }
                    response
                }
    }

    /*****************************************************************************
     * 读元件构造器
     * 一次最多读取 338 个位/字(BOOL、WORD)元件
     * 一次最多读169 个双字(DWORD、REAL)元件
     */
    class ReadBuilder {
        /** 去除 2 字节的校验码长度，协议头部内容放入bits数组中  */
        private val MAX_BIT_LEN = MAX_BUFF_LEN - 2
        /** 去除 8 字节的头部长度，2 字节的校验码长度  */
        private val MAX_WORD_LEN = MAX_BUFF_LEN - 8 - 2

        /** 定义位元件数据  */
        private val bits: ByteArray
        /** 定义字元件数据  */
        private val words: ByteArray

        /** 记录BOOL类型元件名称(按顺序排序),例：X100  */
        private val bitElementName: MutableList<String>
        /** 记录WORD类型元件名称(按顺序排序)，例：D100  */
        private val wordElementName: MutableList<String>
        /** 记录需读取 WORD 数据的类型(按顺序排序)  */
        private val wordType: MutableList<TYPE>

        /** 统计添加元件的数据域(bits + words)总长度,避免总长度超过缓存区大小  */
        private var bytesCount = 0

        /** 统计要读的位元件个数  */
        private var bitCount = 0
        /** 统计要读的字元件个数  */
        private var wordCount = 0

        /** 读一个位元件需要的字节数  */
        private val singleBit = 3
        /** 读一个字元件需要的字节数  */
        private val singleWord = 3

        init {
            bits = ByteArray(MAX_BIT_LEN)
            words = ByteArray(MAX_WORD_LEN)

            bitElementName = ArrayList()
            wordElementName = ArrayList()
            wordType = ArrayList()
        }

        /**
         * 读布尔(BOOL)数据
         *
         * @param element 布尔(BOOL)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readBool(element: PLCElement.BOOL, addr: Int): ReadBuilder {
            //防止数据超过缓冲大小
            if (8 + bytesCount + singleBit > MAX_BIT_LEN) {
                return this
            }

            //从协议头部后开始接收位元件
            val start = bitCount * singleBit + 8
            bits[start] = element.code
            bits[start + 1] = addr.toByte()
            bits[start + 2] = (addr shr 8).toByte()

            //统计
            bitElementName.add(element.name + addr)
            bitCount++
            bytesCount += singleBit
            return this
        }

        /**
         * 读字(WORD)数据
         *
         * @param element 字(WORD)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readWord(element: PLCElement.WORD, addr: Int): ReadBuilder {
            //防止数据超过缓冲大小
            if (bytesCount + singleWord > MAX_WORD_LEN) {
                return this
            }

            val start = wordCount * singleWord
            words[start] = element.code
            words[start + 1] = addr.toByte()
            words[start + 2] = (addr shr 8).toByte()

            //统计
            wordElementName.add(element.name + addr)
            wordType.add(TYPE.WORD)
            wordCount++
            bytesCount += singleWord
            return this
        }

        /**
         * 读双字(DWORD)数据
         *
         * @param element 双字(DWORD)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readDWord(element: PLCElement.DWORD, addr: Int): ReadBuilder {
            //防止数据超过缓冲大小
            if (bytesCount + singleWord * 2 > MAX_WORD_LEN) {
                return this
            }

            val start = wordCount * singleWord
            words[start] = element.code
            words[start + 1] = addr.toByte()
            words[start + 2] = (addr shr 8).toByte()

            val addr2 = addr + 1
            words[start + 3] = element.code
            words[start + 4] = addr2.toByte()
            words[start + 5] = (addr2 shr 8).toByte()

            //统计
            wordElementName.add(element.name + addr)
            wordType.add(TYPE.DWORD)
            wordCount += 2
            bytesCount += singleWord * 2
            return this
        }

        /**
         * 读双字(REAL)数据
         *
         * @param element 双字(REAL)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readReal(element: PLCElement.REAL, addr: Int): ReadBuilder {
            //防止数据超过缓冲大小
            if (bytesCount + singleWord * 2 > MAX_WORD_LEN) {
                return this
            }

            val start = wordCount * singleWord
            words[start] = element.code
            words[start + 1] = addr.toByte()
            words[start + 2] = (addr shr 8).toByte()

            val addr2 = addr + 1
            words[start + 3] = element.code
            words[start + 4] = addr2.toByte()
            words[start + 5] = (addr2 shr 8).toByte()

            //统计
            wordElementName.add(element.name + addr)
            wordType.add(TYPE.REAL)
            wordCount += 2
            bytesCount += singleWord * 2
            return this
        }

        /**
         * 读取一组位(BOOL)元件
         *
         * @param bools 需要读取的位(BOOL)元件列表
         * @return 当前建造类
         */
        fun readBoolList(bools: List<PLCElement.ElementBOOL>?): ReadBuilder {
            if (bools == null || bools.size <= 0) {
                return this
            }

            for (bool in bools) {
                readBool(bool.element, bool.addr)
            }
            return this
        }

        /**
         * 读取一组字(WORD)元件
         *
         * @param words 需要读取的字(WORD)元件列表
         * @return 当前建造类
         */
        fun readWordList(words: List<PLCElement.ElementWORD>?): ReadBuilder {
            if (words == null || words.size <= 0) {
                return this
            }

            for (word in words) {
                readWord(word.element, word.addr)
            }
            return this
        }

        /**
         * 读取一组双字(DWORD)元件
         *
         * @param dwords 需要读取的位双字(DWORD)件列表
         * @return 当前建造类
         */
        fun readDWordList(dwords: List<PLCElement.ElementDWORD>?): ReadBuilder {
            if (dwords == null || dwords.size <= 0) {
                return this
            }

            for (dword in dwords) {
                readDWord(dword.element, dword.addr)
            }
            return this
        }

        /**
         * 读取一组双字(REAL)元件
         *
         * @param reals 需要读取的双字(REAL)元件列表
         * @return 当前建造类
         */
        fun readRealList(reals: List<PLCElement.ElementREAL>?): ReadBuilder {
            if (reals == null || reals.size <= 0) {
                return this
            }

            for (real in reals) {
                readReal(real.element, real.addr)
            }
            return this
        }

        /**
         * 构造 PLC 读写管理器
         *
         * @return PLC 读写管理器
         */
        fun build(): PLCManager {
            //判断是否有读元件
            if (bitCount == 0 && wordCount == 0) {
                return PLCManager(null, null, null, null, null, 0)
            }

            buildHeader()
            val data = addBytes(bits, 8 + bitCount * singleBit, words, wordCount * singleWord)
            return PLCManager(data, calcCrc16(data, 1, data.size - 1), bitElementName, wordElementName, wordType,
                    bitCount)
        }

        /**
         * 构造协议头
         */
        private fun buildHeader() {
            //协议头部
            bits[0] = 0x52
            bits[1] = 0x01
            bits[2] = LOCAL_READ_CODE
            bits[3] = 0x0B
            //数据长度
            val count = bitCount + wordCount
            bits[4] = count.toByte()
            bits[5] = (count shr 8).toByte()
            bits[6] = bitCount.toByte()
            bits[7] = (bitCount shr 8).toByte()
        }
    }

    /*****************************************************************************
     * 写元件构造器
     * 一次最多写 253 个位(BOOL)元件
     * 一次最多写202 个字(WORD)元件
     * 一次最多写101 个双字(DWORD、REAL)元件
     */
    class WriteBuilder {
        /** 去除 2 字节的校验码长度，协议头部内容放入bits数组中  */
        private val MAX_BIT_LEN = MAX_BUFF_LEN - 2
        /** 去除 8 字节的头部长度，2 字节的校验码长度  */
        private val MAX_WORD_LEN = MAX_BUFF_LEN - 8 - 2

        /** 定义位元件数据  */
        private val bits: ByteArray
        /** 定义字元件数据  */
        private val words: ByteArray

        /** 统计添加元件的数据域(bits + words)总长度,避免总长度超过缓存区大小  */
        private var bytesCount = 0

        /** 统计要写的位元件个数  */
        private var bitCount = 0
        /** 统计要写的字元件个数  */
        private var wordCount = 0

        /** 写一个位元件需要的字节数  */
        private val singleBit = 4
        /** 写一个字元件需要的字节数  */
        private val singleWord = 5

        init {
            bits = ByteArray(MAX_BIT_LEN)
            words = ByteArray(MAX_WORD_LEN)
        }

        /**
         * 写一个布尔(BOOL)类型元件
         *
         * @param element 布尔(BOOL)元件类型
         * @param addr    元件地址
         * @param value   要写入元件的值
         * @return 当前建造类
         */
        fun writeBool(element: PLCElement.BOOL, addr: Int, value: Boolean): WriteBuilder {
            //防止数据超过缓冲大小
            if (8 + bytesCount + singleBit > MAX_BIT_LEN) {
                return this
            }

            //从协议头部后开始接收位元件
            val start = bitCount * singleBit + 8
            bits[start] = element.code
            bits[start + 1] = addr.toByte()
            bits[start + 2] = (addr shr 8).toByte()
            bits[start + 3] = (if (value) 1 else 0).toByte()

            //统计
            bitCount++
            bytesCount += singleBit
            return this
        }

        /**
         * 写一个字(WORD)类型元件
         *
         * @param element 字(WORD)元件类型
         * @param addr    元件地址
         * @param value   要写入元件的值
         * @return 当前建造类
         */
        fun writeWord(element: PLCElement.WORD, addr: Int, value: Int): WriteBuilder {
            //防止数据超过缓冲大小
            if (bytesCount + singleWord > MAX_WORD_LEN) {
                return this
            }

            val start = wordCount * singleWord
            words[start] = element.code
            words[start + 1] = addr.toByte()
            words[start + 2] = (addr shr 8).toByte()
            words[start + 3] = value.toByte()
            words[start + 4] = (value shr 8).toByte()

            //统计
            wordCount++
            bytesCount += singleWord
            return this
        }

        /**
         * 写一个双字(DWORD)类型元件
         *
         * @param element 双字(DWORD)元件类型
         * @param addr    元件地址
         * @param value   要写入元件的值
         * @return 当前建造类
         */
        fun writeDWord(element: PLCElement.DWORD, addr: Int, value: Int): WriteBuilder {
            //防止数据超过缓冲大小
            if (bytesCount + singleWord * 2 > MAX_WORD_LEN) {
                return this
            }

            val start = wordCount * singleWord
            words[start] = element.code
            words[start + 1] = addr.toByte()
            words[start + 2] = (addr shr 8).toByte()
            words[start + 3] = value.toByte()
            words[start + 4] = (value shr 8).toByte()

            val addr2 = addr + 1
            words[start + 5] = element.code
            words[start + 6] = addr2.toByte()
            words[start + 7] = (addr2 shr 8).toByte()
            words[start + 8] = (value shr 16).toByte()
            words[start + 9] = (value shr 24).toByte()

            //统计
            wordCount += 2
            bytesCount += singleWord * 2
            return this
        }

        /**
         * 写一个双字(REAL)类型元件
         *
         * @param element 双字(REAL)元件类型
         * @param addr    元件地址
         * @param value   要写入元件的值
         * @return 当前建造类
         */
        fun writeReal(element: PLCElement.REAL, addr: Int, value: Float): WriteBuilder {
            //防止数据超过缓冲大小
            if (bytesCount + singleWord * 2 > MAX_WORD_LEN) {
                return this
            }

            //float 转 int
            val valueInt = java.lang.Float.floatToIntBits(value)

            val start = wordCount * singleWord
            words[start] = element.code
            words[start + 1] = addr.toByte()
            words[start + 2] = (addr shr 8).toByte()
            words[start + 3] = valueInt.toByte()
            words[start + 4] = (valueInt shr 8).toByte()

            val addr2 = addr + 1
            words[start + 5] = element.code
            words[start + 6] = addr2.toByte()
            words[start + 7] = (addr2 shr 8).toByte()
            words[start + 8] = (valueInt shr 16).toByte()
            words[start + 9] = (valueInt shr 24).toByte()

            //统计
            wordCount += 2
            bytesCount += singleWord * 2
            return this
        }

        /**
         * 写取一组位(BOOL)元件
         *
         * @param bools 需要写的位(BOOL)元件列表
         * @return 当前建造类
         */
        fun writeBoolList(bools: List<PLCElement.ElementBOOL>?): WriteBuilder {
            if (bools == null || bools.size <= 0) {
                return this
            }

            for (bool in bools) {
                writeBool(bool.element, bool.addr, bool.value)
            }
            return this
        }

        /**
         * 写取一组字(WORD)元件
         *
         * @param words 需要写的字(WORD)元件列表
         * @return 当前建造类
         */
        fun writeWordList(words: List<PLCElement.ElementWORD>?): WriteBuilder {
            if (words == null || words.size <= 0) {
                return this
            }

            for (word in words) {
                writeWord(word.element, word.addr, word.value)
            }
            return this
        }

        /**
         * 写取一组双字(DWORD)元件
         *
         * @param dwords 需要写的字(DWORD)元件列表
         * @return 当前建造类
         */
        fun writeDWordList(dwords: List<PLCElement.ElementDWORD>?): WriteBuilder {
            if (dwords == null || dwords.size <= 0) {
                return this
            }

            for (dword in dwords) {
                writeDWord(dword.element, dword.addr, dword.value)
            }
            return this
        }

        /**
         * 写取一组双字(REAL)元件
         *
         * @param reals 需要写的字(REAL)元件列表
         * @return 当前建造类
         */
        fun writeRealList(reals: List<PLCElement.ElementREAL>?): WriteBuilder {
            if (reals == null || reals.size <= 0) {
                return this
            }

            for (real in reals) {
                writeReal(real.element, real.addr, real.value.toFloat())
            }
            return this
        }

        /**
         * 构造 PLC 读写管理器
         *
         * @return PLC 读写管理器
         */
        fun build(): PLCManager {
            //判断是否有写元件
            if (bitCount == 0 && wordCount == 0) {
                return PLCManager(null, null, null, null, null, 0)
            }

            buildHeader()
            val data = addBytes(bits, 8 + bitCount * singleBit, words, wordCount * singleWord)
            return PLCManager(data, calcCrc16(data, 1, data.size - 1), null, null, null, bitCount)
        }

        /**
         * 构造协议头
         */
        private fun buildHeader() {
            //协议头部
            bits[0] = 0x57
            bits[1] = 0x01
            bits[2] = LOCAL_WRITE_CODE
            bits[3] = 0x0B
            //数据长度
            val count = bitCount + wordCount
            bits[4] = count.toByte()
            bits[5] = (count shr 8).toByte()
            bits[6] = bitCount.toByte()
            bits[7] = (bitCount shr 8).toByte()
        }
    }

    companion object {

        /** 本地 PLC 读功能码  */
        private val LOCAL_READ_CODE: Byte = 0x69
        /** 本地 PLC 写功能码  */
        private val LOCAL_WRITE_CODE: Byte = 0x68

        /** 定义PLC读写最大缓存区大小  */
        private val MAX_BUFF_LEN = 1024

        /**
         * 校验响应数据
         *
         * @param bytes 接收的响应数据
         * @return 返回校验结果
         */
        private fun verifyResponse(bytes: ByteArray): PLCResponse {
            //获得数据长度
            val length = bytes.size

            //判断 Socket 是否连接成功
            if (length <= 0) {
                return PLCResponse(-1, "连接失败")
            }

            //crc16校验接收数据
            val crc = VerifyUtil.calcCrc16(bytes, 0, length - 2)
            if (crc[0] != bytes[length - 2] || crc[1] != bytes[length - 1]) {
                return PLCResponse(-4, "校验失败")
            } else {
                return PLCResponse(0, "成功")
            }
        }

        /**
         * 合并两个byte数组
         *
         * @param bytes1       待合并 byte 数组 1
         * @param bytes1Length 待合并 byte 数组 1 的长度，从第 0 为开始合并
         * @param bytes2       待合并 byte 数组 2
         * @param bytes2Length 待合并 byte 数组 2 的长度，从第 0 为开始合并
         * @return bytes1 与 bytes2 按相应长度拼接后的结果
         */
        private fun addBytes(bytes1: ByteArray, bytes1Length: Int, bytes2: ByteArray, bytes2Length: Int): ByteArray {

            val data = ByteArray(bytes1Length + bytes2Length)

            if (bytes1Length == 0) {    //当bytes1数组为空时，只处理bytes2数组，优化性能
                System.arraycopy(bytes2, 0, data, 0, bytes2Length)
            } else if (bytes2Length == 0) {    //当bytes2数组为空时，只处理bytes1数组，优化性能
                System.arraycopy(bytes1, 0, data, 0, bytes1Length)
            } else {
                System.arraycopy(bytes1, 0, data, 0, bytes1Length)
                System.arraycopy(bytes2, 0, data, bytes1Length, bytes2Length)
            }

            return data
        }
    }
}
