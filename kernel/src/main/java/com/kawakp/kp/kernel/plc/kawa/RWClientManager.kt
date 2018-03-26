package com.kawakp.kp.kernel.plc.kawa

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.kawakp.kp.kernel.KpApplication
import com.kawakp.kp.kernel.utils.Logger
import com.kawakp.sys.plcd.IAppAidlInterface

/**
 * 创建人: penghui.li
 * 创建时间: 2018/3/24
 * 修改人:penghui.li
 * 修改时间:2018/3/24
 * 修改内容:
 *
 * 功能描述:PLC 数据交互单例,需要到 RXJava 的单线程中操作
 */
object RWClientManager {
    private val TAG = "RWClientManager"

    private val DEBUG = false

    //PLC 读写服务注册单次延时
    private val REGISTER_DELAY = 50L
    //PLC 读写服务注册延时计数 10 次
    private val REGISTER_DELAY_COUNT = 10

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
     * 读写 PLC,需要到 RXJava 的单线程中操作
     *
     * @param data 发送数据
     * @return 返回数据
     */
    fun rwPlc(data: ByteArray): ByteArray {
        if (!isBind) {
            Logger.d(DEBUG, TAG, "first bind plc service!!")
            register()
        }

        if (!isBind) {
            Logger.e(DEBUG, TAG, "plc service unbinding err !!")
            return ByteArray(0)
        }

        if (mAppAidlInterface == null) {
            Logger.e(DEBUG, TAG, "plc service no connect err !!")
            return ByteArray(0)
        }

        try {
            Logger.eByteArray(DEBUG, TAG, "send buff", data)
            val recvBuff = mAppAidlInterface!!.rwPLC(data)
            Logger.eByteArray(DEBUG, TAG, "recv buff is", recvBuff)
            return recvBuff

        } catch (e: RemoteException) {
            Logger.e(DEBUG, TAG, "plc err, e is $e")
        }

        //正常运行不到这里
        return ByteArray(0)
    }

    /**
     * 注销读写 PLC 服务,要求在 APP 退出、奔溃时调用该方法注销 PLC 读写服务
     */
    fun unregister() {
        Logger.d(DEBUG, TAG, "unbinding service !!")
        if (isBind) {
            KpApplication.getContext().unbindService(mConnection)
            changeBindStatus(false)
        }
    }

    /**
     * 注册读写 PLC 服务
     */
    private fun register() {
        Logger.d(DEBUG, TAG, "start binding service !!")
        if (isBind) {
            Logger.d(DEBUG, TAG, "service is bind !!")
            return
        }

        val intent = Intent()
        intent.setClassName("com.kawakp.sys.plcd", "com.kawakp.sys.plcd.service.AppService")
        KpApplication.getContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

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
     * 改变绑定状态
     *
     * @param status 绑定状态
     */
    private fun changeBindStatus(status: Boolean) {
        isBind = status
    }
}