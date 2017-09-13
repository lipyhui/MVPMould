package com.kawakp.kp.kernel.base

import android.databinding.ViewDataBinding
import android.os.Bundle
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
abstract class BaseBindingActivity<B : ViewDataBinding> : RxAppCompatActivity() {

    lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = createDataBinding(savedInstanceState)

        initView()
    }

    abstract fun  createDataBinding(savedInstanceState: Bundle?): B

    abstract fun initView()
}
