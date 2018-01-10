package com.kawakp.kp.application.ui.activity.main

import android.os.Bundle
import com.kawakp.kp.kernel.KernelJNI
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
 * 功能描述:主页presenter
 */
class MainPresenter : BasePresenter<MainAble>(){
    /**
     * 默认加载方法
     */
    override fun onViewCreated(view: MainAble, arguments: Bundle?, savedInstanceState: Bundle?) {
        loadData()
    }

    /**
     * 数据加载
     */
    fun loadData(){
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(this)
                .subscribe {
                    //					hideLoading();
                    //					showContent();
//                    view().showMessageFromNet("error", "This is other error!")
                    view().showContent()
                    //showMessage("this is a test msg");
                    view().setData(KernelJNI.stringFromJNI())
                }
    }
}
