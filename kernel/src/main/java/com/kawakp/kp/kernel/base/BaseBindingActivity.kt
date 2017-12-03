package com.kawakp.kp.kernel.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.WindowManager
import com.kawakp.kp.kernel.KpApplication
import com.kawakp.kp.kernel.base.interfaces.IView
import com.kawakp.kp.kernel.utils.PresenterFactory
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/5
 * 修改人:penghui.li
 * 修改时间:2017/9/5
 * 修改内容:
 *
 * 功能描述:该基类进行Activity的View绑定，所有Activity继承该类。
 */
abstract class BaseBindingActivity<T : BasePresenter<*>, B : ViewDataBinding> : RxAppCompatActivity(), IView {

    lateinit var mBinding: B
    lateinit var mPresenter: T

    /**
     * 处理屏幕常亮、绑定View和Presenter
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*处理屏幕常亮*/
        if (KpApplication.isScreenOn()){
            window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        //取消状态栏下拉显示消息等
        window.addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY)

        mBinding = createViewDataBinding()
        mPresenter = PresenterFactory.createPresenter(this)
    }

    /**
     * 绑定Layout
     */
    private fun createViewDataBinding(): B = DataBindingUtil.setContentView(this, getLayoutId())

    /**
     * 给子类提供配置Layout Id，且返回的Layout Id 不能为空、要真是存在、和ViewDataBinding一致
     */
    abstract fun getLayoutId(): Int
}
