package com.kawakp.kp.application.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kawakp.kp.application.bean.SideItem
import com.kawakp.kp.application.databinding.ItemSideBinding
import com.kawakp.kp.kernel.base.BaseBindingAdapter
import com.kawakp.kp.kernel.base.DataBoundViewHolder

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:侧边栏Item适配器
 */
class SideItemAdapter(private val mList: List<SideItem>) : BaseBindingAdapter<ItemSideBinding>() {
    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemSideBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.item = mList[holder.adapterPosition]

        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ItemSideBinding> {
        return DataBoundViewHolder(ItemSideBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}