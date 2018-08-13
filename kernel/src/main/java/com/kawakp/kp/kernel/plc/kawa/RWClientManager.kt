package com.kawakp.kp.kernel.plc.kawa

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.kawakp.kp.kernel.utils.Logger
import com.kawakp.kp.kernel.utils.ReflectUtils
import com.kawakp.sys.plcd.IAppAidlInterface
import com.kawakp.sys.plcd.bean.PLCResponse
import com.kawakp.sys.plcd.bean.ReadPLCRequest
import com.kawakp.sys.plcd.bean.WritePLCRequest


/**
 * 创建人: penghui.li
 * 创建时间: 2018/3/24
 * 修改人:penghui.li
 * 修改时间:2018/3/24
 * 修改内容:
 *
 * 功能描述:PLC 数据交互单例,需要到 RXJava 的单线程中操作
 */
@SuppressLint("StaticFieldLeak")
object RWClientManager {
    private val TAG = "RWClientManager"

    private val DEBUG = false

    //PLC 读写服务注册单次延时
    private val REGISTER_DELAY = 50L
    //PLC 读写服务注册延时计数 10 次
    private val REGISTER_DELAY_COUNT = 10

    //应用上下文
    private var mContext: Context? = null

    private var mAppAidlInterface: IAppAidlInterface? = null
    private var isBind = false

    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Logger.d(DEBUG, TAG, "service connected!, plc service start !!! ")
            mAppAidlInterface = IAppAidlInterface.Stub.asInterface(service)
            changeBindStatus(true)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Logger.e(DEBUG, TAG, "service connect ERR !!!! ")
            mAppAidlInterface = null
            changeBindStatus(false)
        }
    }

    /**
     * 读 PLC,需要到 RXJava 的单线程中操作
     *
     * @param data 发送数据
     * @return 返回数据
     */
    @Synchronized
    fun readPlc(data: ReadPLCRequest): PLCResponse {
        if (!isBind) {
            Logger.d(DEBUG, TAG, "read first bind plc service!!")
            register()
        }

        if (!isBind) {
            Logger.e(DEBUG, TAG, "read plc service unbinding err !!")
            return PLCResponse(-1, "连接失败")
        }

        if (mAppAidlInterface == null) {
            Logger.e(DEBUG, TAG, "read plc service no connect err !!")
            return PLCResponse(-1, "连接失败")
        }

        try {
            return mAppAidlInterface!!.readPlc(data)
        } catch (e: RemoteException) {
            Logger.e(DEBUG, TAG, "read plc err, e is $e")
        }

        //正常运行不到这里
        return PLCResponse()
    }

    /**
     * 写 PLC,需要到 RXJava 的单线程中操作
     *
     * @param data 发送数据
     * @return 返回数据
     */
    @Synchronized
    fun writePlc(data: WritePLCRequest): PLCResponse {
        if (!isBind) {
            Logger.d(DEBUG, TAG, "write first bind plc service!!")
            register()
        }

        if (!isBind) {
            Logger.e(DEBUG, TAG, "write plc service unbinding err !!")
            return PLCResponse(-1, "连接失败")
        }

        if (mAppAidlInterface == null) {
            Logger.e(DEBUG, TAG, "write plc service no connect err !!")
            return PLCResponse(-1, "连接失败")
        }

        try {
            return mAppAidlInterface!!.writePlc(data)
        } catch (e: RemoteException) {
            Logger.e(DEBUG, TAG, "write plc err, e is $e")
        }

        //正常运行不到这里
        return PLCResponse()
    }

    /**
     * 注销读写 PLC 服务,要求在 APP 退出、奔溃时调用该方法注销 PLC 读写服务
     */
    fun unregister() {
        Logger.d(DEBUG, TAG, "unbinding service !!")
        if (isBind) {
            mContext?.unbindService(mConnection)
            changeBindStatus(false)
        }
    }

    /**
     * 注册读写 PLC 服务
     */
    private fun register() {
        Logger.d(DEBUG, TAG, "start binding service !!")

        //防止重复绑定 APP 通信服务
        if (isBind) {
            Logger.d(DEBUG, TAG, "service is bind !!")
            return
        }

        //防止 Context 初始化失败
        if (!initContext()) {
            Logger.d(DEBUG, TAG, "init context err !!")
            return
        }

        val intent = Intent()
        intent.setClassName("com.kawakp.sys.plcd", "com.kawakp.sys.plcd.service.AppService")
        mContext?.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        Logger.d(DEBUG, TAG, "wait binding service...")

        var count = 0
        while (!isBind || count >= REGISTER_DELAY_COUNT) {
            Logger.d(DEBUG, TAG, "binding service delay count $count")
            count++
            Thread.sleep(REGISTER_DELAY)
        }

        Logger.d(DEBUG, TAG, "binding service end !!! isBind = $isBind")
    }

    /**
     * 初始化 Context
     *
     * @return true：初始化成功  false：初始化失败
     */
    private fun initContext(): Boolean {
        return try {
            mContext = ReflectUtils.reflect(null, "android.app.ActivityThread#currentApplication()") as Application
            Logger.e(DEBUG, TAG, "initContext success !!")

            //返回成功
            true
        } catch (e: Exception) {
            Logger.e(DEBUG, TAG, "initContext err !! $e")
            mContext = null

            //返回失败
            false
        }
    }

    /**
     * 改变绑定状态
     *
     * @param status 绑定状态
     */
    private fun changeBindStatus(status: Boolean) {
        isBind = status
    }
}