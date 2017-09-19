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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (KpApplication.isScreenOn()){
            window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        mBinding = createViewDataBinding()
        mPresenter = PresenterFactory.createPresenter(this)

        initView()
    }

    private fun createViewDataBinding(): B = DataBindingUtil.setContentView(this, getLayoutId())

    abstract fun getLayoutId(): Int

    abstract fun initView()
}
