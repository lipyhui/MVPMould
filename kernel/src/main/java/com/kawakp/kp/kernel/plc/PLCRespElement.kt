package com.kawakp.kp.kernel.plc

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/23
 * 修改人:penghui.li
 * 修改时间:2017/10/23
 * 修改内容:
 *
 * 功能描述:单元件 PLC 读写响应数据
 *          bit: 存放 PLC BOOL类型数据,默认为 false
 *          word: 存放 PLC WORD类型数据,默认为 0
 *          dword: 存放 PLC DWORD类型数据,默认为 0
 *          real: 存放 PLC REAL类型数据,默认为 0
 */
data class PLCRespElement constructor(val bit: Boolean = false,
                                      val word: Int = 0,
                                      val dword: Int = 0,
                                      val real: Float = 0F)