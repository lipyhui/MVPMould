package com.kawakp.kp.kernel.base.interfaces

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:该接口定义了一些界面状态变化实现方法，供使用者实现这些方法
 */
interface IStateView {
    /**
     * 显示界面还在加载中的画面
     */
    fun showLoading() = Unit

    /**
     * 隐藏界面还在加载中的画面
     */
    fun hideLoading() = Unit

    /**
     * 显示界面界面或者数据为空的画面
     */
    fun showEmpty() = Unit

    /**
     * 显示正常界面(主界面)
     */
    fun showContent() = Unit

    /**
     * 普通提示信息
     */
    fun showMessage(content: String) = Unit

    /**
     * 网络错误提示信息
     */
    fun showMessageFromNet(error: Any, content: String) = Unit
}
