package com.kawakp.kp.kernel.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/5
 * 修改人:penghui.li
 * 修改时间:2017/9/5
 * 修改内容:
 *
 * 功能描述:该基类自定义Item的View绑定的ViewHolder
 */
class DataBoundViewHolder<T : ViewDataBinding>(val binding:T) : RecyclerView.ViewHolder(binding.root) {
}