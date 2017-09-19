package com.kawakp.kp.kernel.base

import android.os.Bundle
import android.util.Log
import android.view.View
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseLazyFragment : RxFragment() {

    private var isFirstResume = true
    private var isFirstVisible = true
    private var isFirstInvisible = true
    private var isPrepared = false
    private var isUserVisibleHint = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("ItemFragment", "onViewCreated *****************************")
        initPrepare()
    }

    override fun onResume() {
        super.onResume()
        Log.e("ItemFragment", "onResume *****************************")
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
        Log.e("ItemFragment", "initPrepare isUserVisibleHint = $isUserVisibleHint, isPrepared = $isPrepared")
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
