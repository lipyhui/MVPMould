package com.kawakp.kp.kernel.utils

import android.os.Bundle
import android.support.v4.app.Fragment
import com.kawakp.kp.kernel.base.BaseBindingActivity
import com.kawakp.kp.kernel.base.BaseBindingFragment
import com.kawakp.kp.kernel.base.BasePresenter
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/19
 * 修改人:penghui.li
 * 修改时间:2017/9/19
 * 修改内容:
 *
 * 功能描述:Activity、Fragment与Presenter建立连接的辅助管理类
 */
object PresenterFactory{

    /**
     * Activity与Presenter建立连接
     */
    fun <T : BasePresenter<*>> createPresenter(aty: BaseBindingActivity<*, *>): T {
        val trans = aty.supportFragmentManager.beginTransaction()
        val presenterClass = try {
            (aty::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        } catch (e: Exception) {
            throw IllegalArgumentException("${aty.javaClass.simpleName} create presenter ERROR，" +
                    "try EmptyPresenter If there is no presenter")
        }
        val args = if (aty.intent.extras != null) Bundle(aty.intent.extras) else Bundle()
        var presenter = aty.supportFragmentManager.findFragmentByTag(presenterClass.canonicalName) as T?
        if (presenter == null || presenter.isDetached) {
            presenter = Fragment.instantiate(aty, presenterClass.canonicalName, args) as T
            trans.add(0, presenter, presenterClass.canonicalName)
        }
        presenter.setView(aty)
        trans.commit()
        return presenter
    }

    /**
     * Fragment与Presenter建立连接
     */
    fun <T : BasePresenter<*>> createPresenter(frg: BaseBindingFragment<*, *>): T {
        val trans = frg.fragmentManager.beginTransaction()
        val presenterClass = try {
            (frg::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        } catch (e: Exception) {
            throw IllegalArgumentException("${frg.javaClass.simpleName} create presenter ERROR，" +
                    "try EmptyPresenter If there is no presenter")
        }
        val args = if (frg.arguments != null) Bundle(frg.arguments) else Bundle()
        var presenter = frg.fragmentManager.findFragmentByTag(presenterClass.canonicalName) as T?
        if (presenter == null || presenter.isDetached) {
            presenter = Fragment.instantiate(frg.activity, presenterClass.canonicalName, args) as T
            trans.add(0, presenter, presenterClass.canonicalName)
        }
        presenter.setView(frg)
        trans.commit()
        return presenter
    }
}