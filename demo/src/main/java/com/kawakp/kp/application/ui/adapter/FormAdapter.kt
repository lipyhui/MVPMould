package com.kawakp.kp.application.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kawakp.kp.application.bean.FormItem
import com.kawakp.kp.application.databinding.ItemFormBinding
import com.kawakp.kp.kernel.base.BaseBindingAdapter
import com.kawakp.kp.kernel.base.DataBoundViewHolder

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:数据列表Item适配器
 */
class FormAdapter(private val mList: List<FormItem>) : BaseBindingAdapter<ItemFormBinding>() {
    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemFormBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = mList[holder.adapterPosition]
        data.bg = (position % 2 == 0)
        holder.binding.item = data

        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ItemFormBinding> {
        return DataBoundViewHolder(ItemFormBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}