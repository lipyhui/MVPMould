package com.kawakp.kp.application.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kawakp.kp.kernel.base.BaseBindingFragment
import com.kawakp.kp.kernel.base.BasePresenter
import com.kennyc.view.MultiStateView
import org.jetbrains.anko.support.v4.toast

//stateView
abstract class BaseFragment<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingFragment<T, B>(), IStateView {

    override fun getStateView(): MultiStateView? {
        return super.getStateView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val onCreateView = super.onCreateView(inflater, container, savedInstanceState)
        super.stateViewSetup(onCreateView)
        return onCreateView
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
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


}
