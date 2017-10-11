package com.kawakp.kp.kernel.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 创建人: penghui.li
 * 创建时间: 2017/8/29
 * 修改人:penghui.li
 * 修改时间:2017/8/29
 * 修改内容:
 *
 * 功能描述:	提供一个不可以滑动切换的ViewPager
 */
class NoScrollViewPager : ViewPager {

    private val isPagingEnabled = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * 拦截滑动响应
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.isPagingEnabled && super.onTouchEvent(event)
    }

    /**
     * 拦截滑动响应
     */
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event)
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }
}