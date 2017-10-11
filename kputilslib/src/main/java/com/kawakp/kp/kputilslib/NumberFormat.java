package com.kawakp.kp.kputilslib;

import java.text.DecimalFormat;

/**
 * 创建人: qi
 * 创建时间: 2017/9/1
 * 修改人:qi
 * 修改时间:2017/9/1
 * 修改内容:
 * <p>
 * 功能描述:只保留两位小数的工具
 */
public class NumberFormat {

    /**
     * String 转为保留两位的double
     */
    public static String StringNumberFormat(double num) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(num);
    }

    /**
     * double 转为保留两位的double
     */
    public static double doubleNumberFormat(double num) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(num));
    }

    /**
     * float 转为保留两位的double
     */
    public static float floatNumberFormat(double num) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Float.parseFloat(df.format(num));
    }

}

