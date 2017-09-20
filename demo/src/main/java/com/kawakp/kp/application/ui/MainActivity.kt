package com.kawakp.kp.application.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.LinearLayoutManager
import com.kawakp.kp.application.R
import com.kawakp.kp.application.base.BaseActivity
import com.kawakp.kp.application.bean.SideItem
import com.kawakp.kp.application.databinding.ActivityMainBinding
import com.kawakp.kp.application.router.SideItemRouter
import com.kawakp.kp.application.ui.adapter.SideItemAdapter
import com.kawakp.kp.kernel.base.defaults.EmptyPresenter
import java.util.*

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:侧边栏动态匹配
 */
class MainActivity : BaseActivity<EmptyPresenter, ActivityMainBinding>() {

    private var selected = 0

    private val mList = ArrayList<SideItem>()
    private lateinit var mAdapter: SideItemAdapter
    private lateinit var mFragments: MutableList<Fragment>

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun init() {
        mAdapter = SideItemAdapter(mList)
        mBinding.sideLists.adapter = mAdapter
        mBinding.sideLists.layoutManager = LinearLayoutManager(this)

        initFragments()

        mBinding.viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = mFragments[position]

            override fun getCount(): Int = mFragments.size
        }

        val limitPage = if (mFragments.size < 8) mFragments.size else 8
        mBinding.viewPager.offscreenPageLimit = limitPage - 1

        mAdapter.setOnItemClickListener {
/*            Log.e("ItemFragment", "************************************\n " +
                    "offscreenPageLimit = ${mBinding.viewPager.offscreenPageLimit}" +
                    ", ${mBinding.viewPager.currentItem + 1} -> ${it + 1} \n************************************")*/

            mAdapter.notifyItemChanged(it)
            mBinding.viewPager.currentItem = it
        }

    }

    private fun initFragments() {
        mFragments = ArrayList()

        for (item in SideItemRouter.values()) {
            mList.add(SideItem(item.sideName))
            mFragments.add(item.fragment)
        }

        mAdapter.notifyDataSetChanged()
    }
}