package com.kawakp.kp.kernel.plc

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/23
 * 修改人:penghui.li
 * 修改时间:2017/10/23
 * 修改内容:
 *
 * 功能描述:PLC读写响应数据
 *         responseCode: 存放返回代码，默认为 -100
 *                      0：成功
 *                      -1：连接失败
 *                      -2：数据发送失败
 *                      -3：响应接收失败
 *                      -4：校验失败
 *                      -5：数据解析失败
 *                      -100：未知原因失败
 *         data: 存放返回数据，默认长度为 0
 */
data class PLCResponse constructor(var responseCode: Int = -10,
                                   var responseMsg: String = "未知原因失败",
                                   val data: Map<String, PLCRespElement> = HashMap())