package com.kawakp.kp.kernel.localHttp


/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/15
 * 修改人:penghui.li
 * 修改时间:2017/9/15
 * 修改内容:
 *
 * 功能描述:读写KAWAKP PLC，支持同数据类型多元件类型同时读写
 */
object ReadAndWrite{
    /**
     * 读PLC同一数据类型的数据
     *
     * @param type 数据类型
     * @param adrs 数据地址
     */
    fun ReadJni(type: Constants, adrs: IntArray): Array<String>{
        val Return: Array<String> = Array(adrs.size, {"0"})

        when (type.type) {
            "BIT" -> for (i in adrs.indices) {
                val x = LocalHttpUtils.getBit(type, adrs[i], 1)
                Return[i] = x[0].toString()
            }

            "WORD" -> for (i in adrs.indices) {
                val x = LocalHttpUtils.getWord(type, adrs[i], 1)
                Return[i] = x[0].toString()
            }

            "DWORD" -> for (i in adrs.indices) {
                val x = LocalHttpUtils.getDWord(type, adrs[i], 1)
                Return[i] = x[0].toString()
            }

            "REAL" -> for (i in adrs.indices) {
                val x = LocalHttpUtils.getReal(Constants.OP_REAL_D, adrs[i], 1)
                Return[i] = x[0].toString()
            }
        }

        return Return
    }

    /***
     * 写数据到PLC里面，改方法已经过时
     *
     * @param type 数据类型
     * @param adrs 数据地址
     * @param value 要写的数据
     */
    @Deprecated("This function is deprecated")
    fun WriteJni(type: Constants, adrs: IntArray, value: Array<String>){
        when (type.type) {
            "BIT" -> for (i in adrs.indices) {
                val b = byteArrayOf(value[i].toByte())
                LocalHttpUtils.setBit(type, b, adrs[i], 1)
            }

            "WORD" -> for (i in adrs.indices) {
                val s = shortArrayOf(value[i].toShort())
                LocalHttpUtils.setWord(type, s, adrs[i], 1)
            }

            "DWORD" -> for (i in adrs.indices) {
                val integer = intArrayOf(value[i].toInt())
                LocalHttpUtils.setDWord(type, integer, adrs[i], 1)
            }

            "REAL" -> for (i in adrs.indices) {
                val floats = floatArrayOf(value[i].toFloat())
                LocalHttpUtils.setReal(type, floats, adrs[i], 1)
            }
        }
    }

    /***
     * 写数据到PLC里面
     *
     * @param type 数据类型
     * @param value k:数据地址
     *              v:要写的数据
     */
    fun WriteJni(type: Constants, value: Map<Int, String>){
        when (type.type) {
            "BIT" -> for ((k, v) in value) {
                val b = byteArrayOf(v.toByte())
                LocalHttpUtils.setBit(type, b, k, 1)
            }

            "WORD" -> for ((k, v) in value) {
                val s = shortArrayOf(v.toShort())
                LocalHttpUtils.setWord(type, s, k, 1)
            }

            "DWORD" -> for ((k, v) in value) {
                val integer = intArrayOf(v.toInt())
                LocalHttpUtils.setDWord(type, integer, k, 1)
            }

            "REAL" -> for ((k, v) in value) {
                val floats = floatArrayOf(v.toFloat())
                LocalHttpUtils.setReal(type, floats, k, 1)
            }
        }
    }
}