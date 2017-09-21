package com.kawakp.kp.kernel.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/5
 * 修改人:penghui.li
 * 修改时间:2017/9/5
 * 修改内容:
 *
 * 功能描述:该基类处理RecyclerView Item的点击监听，所有RecyclerView的适配器继承该类。
 */
abstract class BaseBindingAdapter<B : ViewDataBinding> : RecyclerView.Adapter<DataBoundViewHolder<B>>() {

    var mListener: ((v: View, pos: Int) -> Unit)? = null

    override fun onBindViewHolder(holder: DataBoundViewHolder<B>, position: Int) {
        holder.binding.root.setOnClickListener {
            mListener?.invoke(holder.binding.root, holder.adapterPosition)
        }
    }

    fun setOnItemClickListener(listener: ((v: View, pos: Int) -> Unit)) {
        mListener = listener
    }

}