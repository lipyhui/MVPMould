package com.kawakp.kp.kernel.plc

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/20
 * 修改人:penghui.li
 * 修改时间:2017/10/20
 * 修改内容:
 *
 * 功能描述:元件类型管理
 */
class PLCElement private constructor() {
    /**
     * 布尔类型元件：
     * 元件X
     * 元件Y
     * 元件M
     */
    enum class BOOL(val code: Byte) {
        X(0x01),
        Y(0x02),
        M(0x03)
    }

    /**
     * 字节类型元件:
     * 元件D
     * 元件SD
     * 元件R
     */
    enum class WORD(val code: Byte) {
        D(0x09),
        SD(0x0c),
        R(0x0d)
    }

    /**
     * 双字节类型元件:
     * 元件D
     * 元件SD
     * 元件R
     */
    enum class DWORD(val code: Byte) {
        D(0x09),
        SD(0x0c),
        R(0x0d)
    }

    /**
     * REAL类型元件:
     * 元件D
     * 元件R
     */
    enum class REAL(val code: Byte) {
        D(0x09),
        R(0x0d)
    }

    /** 创建PLC BOOL 元件 */
    class ElementBOOL constructor(var element: BOOL = BOOL.X, var addr: Int = 0, var value: Boolean = false)

    /** 创建PLC WORD 元件 */
    class ElementWORD constructor(var element: WORD = WORD.D, var addr: Int = 0, var value: Int = 0)

    /** 创建PLC DWORD 元件 */
    class ElementDWORD constructor(var element: DWORD = DWORD.D, var addr: Int = 0, var value: Int = 0)

    /** 创建PLC REAL 元件 */
    class ElementREAL constructor(var element: REAL = REAL.D, var addr: Int = 0, var value: Int = 0)
}