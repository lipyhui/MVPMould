package com.kawakp.kp.application

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/10
 * 修改人:penghui.li
 * 修改时间:2017/10/10
 * 修改内容:
 *
 * 功能描述:
 */
@RealmClass
open class Cat : RealmObject() {
    open var name: String? = null
}