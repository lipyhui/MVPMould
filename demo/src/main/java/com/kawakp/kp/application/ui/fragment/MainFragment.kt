package com.kawakp.kp.application.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kawakp.kp.application.bean.MainListItem
import com.kawakp.kp.application.databinding.FragmentMainBinding
import com.kawakp.kp.application.router.AnimItemRouter
import com.kawakp.kp.application.router.CharItemRouter
import com.kawakp.kp.application.ui.adapter.MainListAdapter
import com.kawakp.kp.kernel.base.BaseBingingFragment
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
class MainFragment private constructor(): BaseBingingFragment<FragmentMainBinding>() {
    private val mList = ArrayList<MainListItem>()
    private lateinit var mAdapter: MainListAdapter
    private lateinit var mTarget: MutableList<String>

    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        mAdapter = MainListAdapter(mList)
        mBinding.fragmentMainLists.adapter = mAdapter
        mBinding.fragmentMainLists.layoutManager = LinearLayoutManager(context)

        checkSideFragment()

        mAdapter.setOnItemClickListener { startActivity(Intent(context, (Class.forName(mTarget[it])))) }
    }

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
            mTarget.add(item.className)
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
            mTarget.add(item.className)
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