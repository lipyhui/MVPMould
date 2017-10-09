package com.kawakp.kp.application.bean

import io.realm.RealmObject

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/14
 * 修改人:penghui.li
 * 修改时间:2017/9/14
 * 修改内容:
 *
 * 功能描述:表格列表数据项
 */
open class FormItem(
        open var bg: Boolean = false,
        open var time: String = "2017-04-07 03:00:00",
        open var oneP: String = "0",
        open var twoP: String = "0",
        open var oneW: String = "0",
        open var twoW: String = "0"
) : RealmObject(){

}