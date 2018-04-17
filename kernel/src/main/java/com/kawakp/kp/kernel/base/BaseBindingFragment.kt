package com.kawakp.kp.kernel.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kawakp.kp.kernel.base.interfaces.IView
import com.kawakp.kp.kernel.utils.PresenterFactory

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/19
 * 修改人:penghui.li
 * 修改时间:2017/9/19
 * 修改内容:
 *
 * 功能描述:该基类进行Fragment的View绑定，所有Fragment继承该类。
 */
abstract class BaseBindingFragment<T : BasePresenter<*>, B: ViewDataBinding>: BaseLazyFragment(), IView{

    protected lateinit var mBinding: B
    protected lateinit var mPresenter: T

    /**
     * 实现Layout和Presenter和绑定
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater!!, getLayoutId(), container, false)
        mPresenter = PresenterFactory.createPresenter(this)
        return mBinding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * 给子类提供配置Layout Id，且返回的Layout Id 不能为空、要真是存在、和ViewDataBinding一致
     */
    abstract fun getLayoutId(): Int
}