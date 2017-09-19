package com.kawakp.kp.application.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kawakp.kp.application.R
import com.kawakp.kp.application.bean.MainListItem
import com.kawakp.kp.application.databinding.FragmentMainBinding
import com.kawakp.kp.application.router.AnimItemRouter
import com.kawakp.kp.application.router.CharItemRouter
import com.kawakp.kp.application.router.FunRouter
import com.kawakp.kp.application.ui.activity.DemoActivity
import com.kawakp.kp.application.ui.adapter.MainListAdapter
import com.kawakp.kp.kernel.base.BaseBindingFragment
import java.io.Serializable
import java.util.*

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:
 */
class MainFragment private constructor(): BaseBindingFragment<FragmentMainBinding>() {

    private val mList = ArrayList<MainListItem>()
    private lateinit var mAdapter: MainListAdapter
    private lateinit var mTarget: MutableList<FunRouter>

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onFirstUserVisible() {
        mAdapter = MainListAdapter(mList)
        mBinding.fragmentMainLists.adapter = mAdapter
        mBinding.fragmentMainLists.layoutManager = LinearLayoutManager(context)

        checkSideFragment()

        mAdapter.setOnItemClickListener {
            val intent = Intent(context, DemoActivity::class.java)
            intent.putExtra("TARGET_ROUTER", mTarget[it] as Serializable)
            startActivity(intent) }
    }

    /**
     * 匹配侧边分类列表对应的fragment
     */
    private fun checkSideFragment(){
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
        mTarget = ArrayList()

        for (item in CharItemRouter.values()){
            mList.add(MainListItem(item.itemName))
            mTarget.add(item)
        }

        mAdapter.notifyDataSetChanged()
    }

    /**
     * 初始化动画列表
     */
    private fun initAnimList() {
        mTarget = ArrayList()

        for (item in AnimItemRouter.values()){
            mList.add(MainListItem(item.itemName))
            mTarget.add(item)
        }

        mAdapter.notifyDataSetChanged()
    }


    companion object {
        fun newInstance(type: Int): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt("MAIN_FRAGMENT_TYPE", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}