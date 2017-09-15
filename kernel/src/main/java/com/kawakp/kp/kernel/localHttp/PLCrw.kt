package com.kawakp.kp.kernel.localHttp

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/15
 * 修改人:penghui.li
 * 修改时间:2017/9/15
 * 修改内容:
 *
 * 功能描述:
 */
object PLCrw{
    /**
     *读取PLC相应元件的值
     *
     * @param elements key:读取元件类型
     *                 value:读取一组元件地址列表
     */
    fun read(elements: Map<Constants, IntArray>): Map<Constants, Array<String>>{
        var response = HashMap<Constants, Array<String>>()
        for ((type, addrs) in elements){
            response.put(type, ReadAndWrite.ReadJni(type, addrs))
        }

        return response
    }

    /**
     *读取PLC相应元件的值
     *
     * @param type 读取元件类型
     * @param addrs 读取一组元件地址列表
     */
    fun read(type: Constants, addrs: IntArray): Map<Constants, Array<String>>{
        var response = HashMap<Constants, Array<String>>()
        response.put(type, ReadAndWrite.ReadJni(type, addrs))

        return response
    }

    /**
     *写一组数据到PLC相应元件
     *
     * @param type 写入的元件类型
     * @param vaules K:写入的元件地址
     *               v:写入的元件数据
     */
    fun write(type: Constants, vaules: Map<Int, String>){
        ReadAndWrite.WriteJni(type, vaules)
    }
}