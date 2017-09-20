package com.kawakp.kp.kernel.base.defaults

import android.os.Bundle
import com.kawakp.kp.kernel.base.BasePresenter
import com.kawakp.kp.kernel.base.interfaces.IView

class EmptyPresenter : BasePresenter<IView>() {
    override fun onViewCreated(view: IView, arguments: Bundle?, savedInstanceState: Bundle?) {
    }
}