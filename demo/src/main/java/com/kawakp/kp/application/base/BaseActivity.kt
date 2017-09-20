package com.kawakp.kp.application.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.kawakp.kp.kernel.base.BaseBindingActivity
import com.kawakp.kp.kernel.base.BasePresenter
import com.kennyc.view.MultiStateView
import org.jetbrains.anko.toast

//stateView
abstract class BaseActivity<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingActivity<T, B>(), IStateView {

    override fun getStateView(): MultiStateView? {
        return super.getStateView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.stateViewSetup(this)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun showLoading() {
        super.showLoading()
        super.stateViewLoading()
    }

    override fun showMessageFromNet(error: Any, content: String) {
        super.showMessageFromNet(error, content)
        super.stateViewError(error, content)
    }

    override fun showEmpty() {
        super.showEmpty()
        super.stateViewEmpty()
    }

    override fun showContent() {
        super.showContent()
        super.stateViewContent()
    }

    override fun showMessage(content: String) {
        super.showMessage(content)
        toast(content)
    }

    override fun hideLoading() {
        super.hideLoading()
        showContent()
    }

    abstract fun init()
}
