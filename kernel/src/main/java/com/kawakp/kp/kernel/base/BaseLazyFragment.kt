package com.kawakp.kp.kernel.base

import android.os.Bundle
import android.view.View
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:	细化Fragment的生命周期
 */
abstract class BaseLazyFragment : RxFragment() {

    private var isFirstResume = true
    private var isFirstVisible = true
    private var isFirstInvisible = true
    private var isPrepared = false
    private var isUserVisibleHint = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPrepare()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return@onResume
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onUserInvisible()
        }
    }

    override fun onDestroyView() {
        isFirstResume = true
        isFirstVisible = true
        isFirstInvisible = true
        isPrepared = false
        isUserVisibleHint = false
        super.onDestroyView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isUserVisibleHint = true
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    @Synchronized private fun initPrepare() {
        if (isUserVisibleHint) {
            if (isPrepared) {
                onFirstUserVisible()
            } else {
                isPrepared = true
            }
        } else if (!isPrepared){
            onFirstUserVisible()
            isPrepared = true
        }
    }

    //首次可见
    protected abstract fun onFirstUserVisible()

    //每次可见
    open fun onUserVisible() {}

    //首次不可见,预加载会调用
    open fun onFirstUserInvisible() {}

    //每次不可见
    open fun onUserInvisible() {}
}
