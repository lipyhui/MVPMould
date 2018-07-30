package com.kawakp.kp.kernel.plc.kawa

import com.kawakp.kp.kernel.plc.interfaces.OnPLCResponseListener
import com.kawakp.sys.plcd.bean.PLCResponse
import com.kawakp.sys.plcd.bean.ReadPLCRequest
import com.kawakp.sys.plcd.bean.WritePLCRequest
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/23
 * 修改人:penghui.li
 * 修改时间:2017/10/23
 * 修改内容:
 *
 * 功能描述:KAWA PLC读写操作，超出缓冲区，返回读写错误
 */

class PLCManager
/**
 * 构造方法
 *
 * @param mReadData        待发送读数据
 * @param mWriteData       待发送写数据
 */
private constructor(
        /** 待发送数据  */
        private val mReadData: ReadPLCRequest?,
        /** 数据校验信息  */
        private val mWriteData: WritePLCRequest?) {

    private companion object {
        private val TAG = "PLCManager"
        /** 调试开关 */
        private val DEBUG = true
    }

    /**
     * 异步发送数据，不关心返回
     */
    fun startAsync() {
        start().subscribe()
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
    @Synchronized
    fun start(): Observable<PLCResponse> {
        //防止 PLC 读数据为空
        mReadData?.let {
            //读 PLC 数据,并返回
            return Observable.just(0)
                    .subscribeOn(Schedulers.single())
                    .observeOn(Schedulers.single())
                    .map { RWClientManager.readPlc(mReadData) }
//                    .map {
//                        Logger.e(DEBUG, TAG, "READ PLC response is: $it")
//                        it
//                    }
        }

        //防止 PLC 写数据为空
        mWriteData?.let {
            //写 PLC 数据,并返回
            return Observable.just(0)
                    .subscribeOn(Schedulers.single())
                    .observeOn(Schedulers.single())
                    .map { RWClientManager.writePlc(mWriteData) }
//                    .map {
//                        Logger.e(DEBUG, TAG, "WRITE PLC response is: $it")
//                        it
//                    }
        }

        //正常情况运行不到这里
        return Observable.just(0)
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .map { PLCResponse(-2, "读写请求数据为空") }
    }

    /**
     * 同步发送数据并接收响应数据，未切换到后台线程执行(与调用方法处于统一线程，因尽量使用单线程读写 PLC)
     *
     * @return 返回响应数据
     */
    @Synchronized
    fun startSync(): PLCResponse {
        //防止 PLC 读数据为空
        mReadData?.let {
            //读 PLC 数据,并返回
            return RWClientManager.readPlc(mReadData)

//            val response = RWClientManager.readPlc(mReadData)
//            Logger.e(DEBUG, TAG, "READ PLC response is: $response")
        }

        //防止 PLC 写数据为空
        mWriteData?.let {
            //写 PLC 数据,并返回
            return RWClientManager.writePlc(mWriteData)

//            val response = RWClientManager.writePlc(mWriteData)
//            Logger.e(DEBUG, TAG, "WRITE PLC response is: $response")
        }

        //正常情况运行不到这里
        return PLCResponse(-2, "读写请求数据为空")
    }

    /*****************************************************************************
     *                              读元件构造器
     *****************************************************************************/
    class ReadBuilder {
        /** 读请求数据类 */
        private val mReadData = ReadPLCRequest()

        /**
         * 读 PLC 元件，如果参数错误可能会抛出异常
         *
         * @param dataType 数据类型
         * @param elementType 元件类型
         * @param addr 元件地址
         * @return 当前建造类
         */
        fun read(dataType: Element.DATA_TYPE, elementType: Element.ELEMENT_TYPE, addr: Int): ReadBuilder {
            when (dataType) {
            //读 BOOL 类型元件
                Element.DATA_TYPE.BOOL -> readBool(Element.BOOL.valueOf(elementType.name), addr)

            //读 WORD 类型元件
                Element.DATA_TYPE.WORD -> readWord(Element.WORD.valueOf(elementType.name), addr)

            //读 DWORD 类型元件
                Element.DATA_TYPE.DWORD -> readDWord(Element.DWORD.valueOf(elementType.name), addr)

            //读 INT 类型元件
                Element.DATA_TYPE.INT -> readInt(Element.INT.valueOf(elementType.name), addr)

            //读 DINT 类型元件
                Element.DATA_TYPE.DINT -> readDInt(Element.DINT.valueOf(elementType.name), addr)

            //读 REAL 类型元件
                Element.DATA_TYPE.REAL -> readReal(Element.REAL.valueOf(elementType.name), addr)

                else -> {
                }
            }

            return this
        }

        /**
         * 读布尔(BOOL)数据
         *
         * @param element 布尔(BOOL)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readBool(element: Element.BOOL, addr: Int): ReadBuilder {
            val key = "${element.name}$addr"
            if (!mReadData.BOOL.contains(key)) {
                mReadData.BOOL.add(key)
            }
            return this
        }

        /**
         * 读字(WORD)数据
         *
         * @param element 字(WORD)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readWord(element: Element.WORD, addr: Int): ReadBuilder {
            val key = "${element.name}$addr"
            if (!mReadData.WORD.contains(key)) {
                mReadData.WORD.add(key)
            }
            return this
        }

        /**
         * 读双字(DWORD)数据
         *
         * @param element 双字(DWORD)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readDWord(element: Element.DWORD, addr: Int): ReadBuilder {
            val key = "${element.name}$addr"
            if (!mReadData.DWORD.contains(key)) {
                mReadData.DWORD.add(key)
            }
            return this
        }

        /**
         * 读字(INT)数据
         *
         * @param element 字(INT)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readInt(element: Element.INT, addr: Int): ReadBuilder {
            val key = "${element.name}$addr"
            if (!mReadData.INT.contains(key)) {
                mReadData.INT.add(key)
            }
            return this
        }

        /**
         * 读双字(DINT)数据
         *
         * @param element 双字(DINT)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readDInt(element: Element.DINT, addr: Int): ReadBuilder {
            val key = "${element.name}$addr"
            if (!mReadData.DINT.contains(key)) {
                mReadData.DINT.add("${element.name}$addr")
            }
            return this
        }

        /**
         * 读双字(REAL)数据
         *
         * @param element 双字(REAL)元件类型
         * @param addr    元件地址
         * @return 当前建造类
         */
        fun readReal(element: Element.REAL, addr: Int): ReadBuilder {
            val key = "${element.name}$addr"
            if (!mReadData.REAL.contains(key)) {
                mReadData.REAL.add("${element.name}$addr")
            }
            return this
        }

        /**
         * 读取一组位(BOOL)元件
         *
         * @param bools 需要读取的位(BOOL)元件列表
         * @return 当前建造类
         */
        fun readBoolList(bools: List<Element.ElementBOOL>?): ReadBuilder {
            if (bools == null || bools.isEmpty()) {
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
        fun readWordList(words: List<Element.ElementWORD>?): ReadBuilder {
            if (words == null || words.isEmpty()) {
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
        fun readDWordList(dwords: List<Element.ElementDWORD>?): ReadBuilder {
            if (dwords == null || dwords.isEmpty()) {
                return this
            }

            for (dword in dwords) {
                readDWord(dword.element, dword.addr)
            }
            return this
        }

        /**
         * 读取一组字(INT)元件
         *
         * @param ints 需要读取的字(WORD)元件列表
         * @return 当前建造类
         */
        fun readIntList(ints: List<Element.ElementINT>?): ReadBuilder {
            if (ints == null || ints.isEmpty()) {
                return this
            }

            for (int in ints) {
                readInt(int.element, int.addr)
            }
            return this
        }

        /**
         * 读取一组双字(DINT)元件
         *
         * @param dints 需要读取的位双字(DINT)件列表
         * @return 当前建造类
         */
        fun readDIntList(dints: List<Element.ElementDINT>?): ReadBuilder {
            if (dints == null || dints.isEmpty()) {
                return this
            }

            for (dint in dints) {
                readDInt(dint.element, dint.addr)
            }
            return this
        }

        /**
         * 读取一组双字(REAL)元件
         *
         * @param reals 需要读取的双字(REAL)元件列表
         * @return 当前建造类
         */
        fun readRealList(reals: List<Element.ElementREAL>?): ReadBuilder {
            if (reals == null || reals.isEmpty()) {
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
        fun build(): PLCManager = PLCManager(mReadData, null)
    }

    /*****************************************************************************
     *                                  写元件构造器
     *****************************************************************************/
    class WriteBuilder {
        /** 写请求数据类  */
        private val mWriteRequest = WritePLCRequest()

        /**
         * 写 PLC BOOL 类型元件，如果参数错误可能会抛出异常
         *
         * @param dataType 数据类型
         * @param elementType 元件类型
         * @param addr 元件地址
         * @param value 要写入元件的值
         * @return 当前建造类
         */
        fun write(dataType: Element.DATA_TYPE, elementType: Element.ELEMENT_TYPE, addr: Int, value: Boolean): WriteBuilder {
            if (dataType == Element.DATA_TYPE.BOOL) {
                writeBool(Element.BOOL.valueOf(elementType.name), addr, value)
            }
            return this
        }

        /**
         * 写 PLC WORD、DWORD、INT、DINT 类型元件，如果参数错误可能会抛出异常
         *
         * @param dataType 数据类型
         * @param elementType 元件类型
         * @param addr 元件地址
         * @param value 要写入元件的值
         * @return 当前建造类
         */
        fun write(dataType: Element.DATA_TYPE, elementType: Element.ELEMENT_TYPE, addr: Int, value: Int): WriteBuilder {
            when (dataType) {
            //写 WORD 类型元件
                Element.DATA_TYPE.WORD -> writeWord(Element.WORD.valueOf(elementType.name), addr, value)

            //写 DWORD 类型元件
                Element.DATA_TYPE.DWORD -> writeDWord(Element.DWORD.valueOf(elementType.name), addr, value)

            //写 INT 类型元件
                Element.DATA_TYPE.INT -> writeInt(Element.INT.valueOf(elementType.name), addr, value)

            //写 DINT 类型元件
                Element.DATA_TYPE.DINT -> writeDInt(Element.DINT.valueOf(elementType.name), addr, value)

                else -> {
                }
            }

            return this
        }

        /**
         * 写 PLC REAL 类型元件，如果参数错误可能会抛出异常
         *
         * @param dataType 数据类型
         * @param elementType 元件类型
         * @param addr 元件地址
         * @param value 要写入元件的值
         * @return 当前建造类
         */
        fun write(dataType: Element.DATA_TYPE, elementType: Element.ELEMENT_TYPE, addr: Int, value: Float): WriteBuilder {
            if (dataType == Element.DATA_TYPE.REAL) {
                writeReal(Element.REAL.valueOf(elementType.name), addr, value)
            }
            return this
        }

        /**
         * 写一个布尔(BOOL)类型元件
         *
         * @param element 布尔(BOOL)元件类型
         * @param addr    元件地址
         * @param value   要写入元件的值
         * @return 当前建造类
         */
        fun writeBool(element: Element.BOOL, addr: Int, value: Boolean): WriteBuilder {
            mWriteRequest.BOOL.put("${element.name}$addr", value)
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
        fun writeWord(element: Element.WORD, addr: Int, value: Int): WriteBuilder {
            mWriteRequest.WORD.put("${element.name}$addr", value)
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
        fun writeDWord(element: Element.DWORD, addr: Int, value: Int): WriteBuilder {
            mWriteRequest.DWORD.put("${element.name}$addr", value)
            return this
        }

        /**
         * 写一个字(INT)类型元件
         *
         * @param element 字(INT)元件类型
         * @param addr    元件地址
         * @param value   要写入元件的值
         * @return 当前建造类
         */
        fun writeInt(element: Element.INT, addr: Int, value: Int): WriteBuilder {
            mWriteRequest.INT.put("${element.name}$addr", value)
            return this
        }

        /**
         * 写一个双字(DINT)类型元件
         *
         * @param element 双字(DINT)元件类型
         * @param addr    元件地址
         * @param value   要写入元件的值
         * @return 当前建造类
         */
        fun writeDInt(element: Element.DINT, addr: Int, value: Int): WriteBuilder {
            mWriteRequest.DINT.put("${element.name}$addr", value)
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
        fun writeReal(element: Element.REAL, addr: Int, value: Float): WriteBuilder {
            mWriteRequest.REAL.put("${element.name}$addr", value)
            return this
        }

        /**
         * 写取一组位(BOOL)元件
         *
         * @param bools 需要写的位(BOOL)元件列表
         * @return 当前建造类
         */
        fun writeBoolList(bools: List<Element.ElementBOOL>?): WriteBuilder {
            if (bools == null || bools.isEmpty()) {
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
        fun writeWordList(words: List<Element.ElementWORD>?): WriteBuilder {
            if (words == null || words.isEmpty()) {
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
        fun writeDWordList(dwords: List<Element.ElementDWORD>?): WriteBuilder {
            if (dwords == null || dwords.isEmpty()) {
                return this
            }

            for (dword in dwords) {
                writeDWord(dword.element, dword.addr, dword.value)
            }
            return this
        }

        /**
         * 写取一组字(INT)元件
         *
         * @param ints 需要写的字(INT)元件列表
         * @return 当前建造类
         */
        fun writeIntList(ints: List<Element.ElementINT>?): WriteBuilder {
            if (ints == null || ints.isEmpty()) {
                return this
            }

            for (int in ints) {
                writeInt(int.element, int.addr, int.value)
            }
            return this
        }

        /**
         * 写取一组双字(DINT)元件
         *
         * @param dints 需要写的字(DINT)元件列表
         * @return 当前建造类
         */
        fun writeDIntList(dints: List<Element.ElementDINT>?): WriteBuilder {
            if (dints == null || dints.isEmpty()) {
                return this
            }

            for (dint in dints) {
                writeDInt(dint.element, dint.addr, dint.value)
            }
            return this
        }

        /**
         * 写取一组双字(REAL)元件
         *
         * @param reals 需要写的字(REAL)元件列表
         * @return 当前建造类
         */
        fun writeRealList(reals: List<Element.ElementREAL>?): WriteBuilder {
            if (reals == null || reals.isEmpty()) {
                return this
            }

            for (real in reals) {
                writeReal(real.element, real.addr, real.value)
            }
            return this
        }

        /**
         * 构造 PLC 读写管理器
         *
         * @return PLC 读写管理器
         */
        fun build(): PLCManager = PLCManager(null, mWriteRequest)
    }
}
