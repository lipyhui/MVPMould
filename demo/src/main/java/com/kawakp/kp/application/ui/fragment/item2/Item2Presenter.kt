package com.kawakp.kp.application.ui.fragment.item2

import android.os.Bundle
import com.kawakp.kp.kernel.base.BasePresenter
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
 * 功能描述:
 */
class Item2Presenter : BasePresenter<Item2Able>(){
    override fun onViewCreated(view: Item2Able, arguments: Bundle?, savedInstanceState: Bundle?) {
        loadData()
    }

    fun loadData(){
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view().showLoading() }
                .subscribe {
                    view().setText("This is fragment two")
                    view().showMessageFromNet("error", "This is error")
                }
    }

}