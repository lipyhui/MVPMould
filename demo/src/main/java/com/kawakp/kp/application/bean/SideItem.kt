package com.kawakp.kp.application.bean

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:侧边栏数据项。 img:侧边栏文字图标；name:侧边栏文本；selected:选中状态
 */
data class SideItem(val img: String, val name: String, var selected: Boolean = false)
