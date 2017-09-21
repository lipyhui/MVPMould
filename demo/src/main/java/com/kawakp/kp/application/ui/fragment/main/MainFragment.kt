package com.kawakp.kp.application.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kawakp.kp.application.R
import com.kawakp.kp.application.base.BaseFragment
import com.kawakp.kp.application.databinding.FragmentMainBinding
import com.kawakp.kp.application.ui.activity.demo.DemoActivity
import com.kawakp.kp.application.ui.adapter.MainListAdapter
import java.io.Serializable

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:主fragment适配
 */
class MainFragment private constructor(): BaseFragment<MainPresenter, FragmentMainBinding>(), MainAble {

    private lateinit var mAdapter: MainListAdapter

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onFirstUserVisible() {
        mAdapter = MainListAdapter(mPresenter.getList())
        mBinding.fragmentMainLists.adapter = mAdapter
        mBinding.fragmentMainLists.layoutManager = LinearLayoutManager(context)

        mPresenter.checkSideFragment()

        mAdapter.setOnItemClickListener {binding, pos ->
            val intent = Intent(context, DemoActivity::class.java)
            intent.putExtra("TARGET_ROUTER", mPresenter.getTarget()[pos] as Serializable)
            startActivity(intent) }
    }

    override fun updateList() {
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