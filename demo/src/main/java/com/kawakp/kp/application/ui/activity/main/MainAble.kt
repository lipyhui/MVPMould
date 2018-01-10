package com.kawakp.kp.application.ui.activity.main

import com.kawakp.kp.kernel.base.interfaces.IView

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/20
 * 修改人:penghui.li
 * 修改时间:2017/9/20
 * 修改内容:
 *
 * 功能描述:主页view接口定义
 */
interface MainAble : IView{
    /**
     * 设置字符串
     */
    fun setData(data: String)
}