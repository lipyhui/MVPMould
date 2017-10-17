package com.kawakp.kp.application.ui.activity.demo

import android.os.Bundle
import com.kawakp.kp.application.R
import com.kawakp.kp.application.router.FunRouter
import com.kawakp.kp.kernel.base.BasePresenter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/20
 * 修改人:penghui.li
 * 修改时间:2017/9/20
 * 修改内容:
 *
 * 功能描述:主控制器
 */
class DemoPresenter : BasePresenter<DemoAble>() {

    override fun onViewCreated(view: DemoAble, arguments: Bundle?, savedInstanceState: Bundle?) {
        loadView()
    }

    /**
     * 加载界面
     */
    private fun loadView(){
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view().showLoading() }
                .bindToLifecycle(view().geRxtActivity())
                .subscribe {
                    val funRouter = activity.intent.getSerializableExtra("TARGET_ROUTER") as FunRouter

                    view().initTitle(funRouter.itemName)
                    replaceFragment(funRouter)
                    view().showContent()
                }
    }

    /**
     * Fragment匹配
     */
    private fun replaceFragment(funRouter: FunRouter) {
        activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, funRouter.target)
                .commit()
    }

}