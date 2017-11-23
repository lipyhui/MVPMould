package com.kawakp.kp.kernel.plc.kawa

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/20
 * 修改人:penghui.li
 * 修改时间:2017/10/20
 * 修改内容:
 *
 * 功能描述: KAWA 元件类型管理
 */
enum class SyncElement(val code: Byte){
    /**
     * 元件X
     * 元件Y
     * 元件M
     * 元件D
     * 元件SD
     * 元件R
     */
    X(0x01),
    Y(0x02),
    M(0x03),
    D(0x09),
    SD(0x0c),
    R(0x0d)
}