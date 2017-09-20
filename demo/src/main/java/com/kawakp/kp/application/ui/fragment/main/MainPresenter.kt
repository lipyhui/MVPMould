package com.kawakp.kp.application.ui.fragment.main

import android.os.Bundle
import com.kawakp.kp.application.bean.MainListItem
import com.kawakp.kp.application.router.AnimItemRouter
import com.kawakp.kp.application.router.CharItemRouter
import com.kawakp.kp.application.router.FunRouter
import com.kawakp.kp.kernel.base.BasePresenter

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/20
 * 修改人:penghui.li
 * 修改时间:2017/9/20
 * 修改内容:
 *
 * 功能描述:主控制器
 */
class MainPresenter : BasePresenter<MainAble>() {
    private val mList = ArrayList<MainListItem>()
    private lateinit var mTarget: MutableList<FunRouter>

    override fun onViewCreated(view: MainAble, arguments: Bundle?, savedInstanceState: Bundle?) {

    }

    fun getList(): ArrayList<MainListItem> {
        return mList
    }

    fun getTarget(): MutableList<FunRouter> {
        return mTarget
    }

    /**
     * 匹配侧边分类列表对应的fragment
     */
    fun checkSideFragment(){
        when(arguments.getInt("MAIN_FRAGMENT_TYPE")){
            1 -> initChartList()
            2 -> initAnimList()
            else -> initChartList()
        }
    }

    /**
     * 初始化折线图列表
     */
    private fun initChartList() {
        mTarget = java.util.ArrayList()

        for (item in CharItemRouter.values()){
            mList.add(MainListItem(item.itemName))
            mTarget.add(item)
        }

        view().updateList()

    }

    /**
     * 初始化动画列表
     */
    private fun initAnimList() {
        mTarget = java.util.ArrayList()

        for (item in AnimItemRouter.values()){
            mList.add(MainListItem(item.itemName))
            mTarget.add(item)
        }

        view().updateList()
    }

}