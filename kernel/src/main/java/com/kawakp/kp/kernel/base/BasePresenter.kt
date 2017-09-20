package com.kawakp.kp.kernel.base

import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kawakp.kp.kernel.base.interfaces.IView
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 *
 * <h3>Build Presenter with Fragment：</h3>
 *
 * Fragment has a life cycle
 *
 * FragmentManager can cache fragment
 *
 * Coordinate with RxLifeCycle
 *
 */
@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<V : IView> : RxFragment() {
    lateinit var mView: V

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onViewCreated(mView, arguments, savedInstanceState)
        return null
    }

    abstract fun onViewCreated(@NonNull view: V, arguments: Bundle?, savedInstanceState: Bundle?)

    //abandon
    final override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun view(): V = mView
    fun setView(view: Any) {
        this.mView = view as V
    }

}
