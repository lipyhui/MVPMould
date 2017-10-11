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

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:	对BaseBindingFragment再封装。实现IStateView各界面状态关系绑定
 */
abstract class BaseFragment<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingFragment<T, B>(), IStateView {

    /**
     * 初始化MultiStateView
     */
    override fun getStateView(): MultiStateView? {
        return super.getStateView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 绑定状态界面四个Layout
     */
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

    /**
     * 绑定界面加载中界面
     */
    override fun showLoading() {
        super.showLoading()
        super.stateViewLoading()
    }

    /**
     * 绑定网络错误提示信息
     */
    override fun showMessageFromNet(error: Any, content: String) {
        super.showMessageFromNet(error, content)
        super.stateViewError(error, content)
    }

    /**
     * 绑定显示空界面
     */
    override fun showEmpty() {
        super.showEmpty()
        super.stateViewEmpty()
    }

    /**
     * 绑定显示主界面
     */
    override fun showContent() {
        super.showContent()
        super.stateViewContent()
    }

    /**
     * 绑定提示消息
     */
    override fun showMessage(content: String) {
        super.showMessage(content)
        toast(content)
    }

    /**
     * 实现隐藏加载界面
     */
    override fun hideLoading() {
        super.hideLoading()
        showContent()
    }


}
