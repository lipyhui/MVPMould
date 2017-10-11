package com.kawakp.kp.kernel.base.defaults

import android.os.Bundle
import com.kawakp.kp.kernel.base.BasePresenter
import com.kawakp.kp.kernel.base.interfaces.IView

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:空Presenter，供不使用MVP的Activity、Fragment使用
 */
class EmptyPresenter : BasePresenter<IView>() {
    override fun onViewCreated(view: IView, arguments: Bundle?, savedInstanceState: Bundle?) {
    }
}