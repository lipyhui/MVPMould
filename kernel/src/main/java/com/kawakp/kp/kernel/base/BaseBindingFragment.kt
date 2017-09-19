package com.kawakp.kp.kernel.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/5
 * 修改人:penghui.li
 * 修改时间:2017/9/5
 * 修改内容:
 *
 * 功能描述:该基类进行Fragment的View绑定，所有Fragment继承该类。
 */
abstract class BaseBindingFragment<B: ViewDataBinding> : BaseLazyFragment(){

    lateinit var mBinding : B

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun getLayoutId(): Int

}