package com.kawakp.kp.kernel.localHttp

/**
 * 创建人: penghui.li
 * 创建时间: 2017/9/15
 * 修改人:penghui.li
 * 修改时间:2017/9/15
 * 修改内容:
 *
 * 功能描述:PLC读写常量表
 */
enum class Constants(val code: Int, val type: String, val cmd: String){
    OP_BIT_X(0, "BIT", "bit_x"),
    OP_BIT_Y(1, "BIT", "bit_y"),
    OP_BIT_M(2, "BIT", "bit_m"),
    OP_BIT_S(3, "BIT", "bit_s"),
    OP_BIT_T(4, "BIT", "bit_t"),
    OP_BIT_C(5, "BIT", "bit_c"),
    OP_BIT_SM(6, "BIT", "bit_sm"),

    OP_WORD_D(10, "WORD", "word_d"),
    OP_WORD_R(11, "WORD", "word_r"),
    OP_WORD_SD(12, "WORD", "word_sd"),
    OP_WORD_Z(13, "WORD", "word_z"),
    OP_WORD_T(14, "WORD", "word_t"),

    OP_DWORD_D(20, "DWORD", "dword_d"),
    OP_DWORD_R(21, "DWORD", "dword_r"),
    OP_DWORD_SD(22, "DWORD", "dword_sd"),
    OP_DWORD_Z(23, "DWORD", "dword_z"),
    OP_DWORD_C(24, "DWORD", "dword_c"),

    OP_REAL_D(30, "REAL", "real_d"),
    OP_REAL_R(31, "REAL", "real_r");

    fun getReadCmd() = "get_$cmd"
    fun getWriteCmd() = "set_$cmd"
}