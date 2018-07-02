package com.kawakp.kp.application.ui.fragment.item2

import android.os.Bundle
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
 * 功能描述:Item2逻辑控制器
 */
class Item2Presenter : BasePresenter<Item2Able>(){
    /**
     * 默认加载方法
     */
    override fun onViewCreated(view: Item2Able, arguments: Bundle?, savedInstanceState: Bundle?) {
        loadData()
    }

    /**
     * 加载数据
     */
    fun loadData(){
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view().showLoading() }
                .bindToLifecycle(this)
                .subscribe {
                    view().setText("This is fragment two")
                    view().showMessageFromNet("error", "This is error")
                }
    }

}