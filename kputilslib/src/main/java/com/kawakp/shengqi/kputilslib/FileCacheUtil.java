package com.kawakp.shengqi.kputilslib;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 创建人: qi
 * 创建时间: 2017/9/1
 * 修改人:qi
 * 修改时间:2017/9/1
 * 修改内容:
 * <p>
 * 功能描述:数据储存为文件
 */
public class FileCacheUtil {
    //定义缓存文件的名字，方便外部调用
    public static final String docCache = "file_cache.txt";//缓存文件
    //缓存超时时间
    public static final int CACHE_SHORT_TIMEOUT = 1000 * 60 * 5; // 5 分钟

    /**
     * 设置缓存
     * content是要存储的内容，可以是任意格式的，不一定是字符串。
     */
    public static void setCache(String content, Context context, String cacheFileName, int mode) {
        FileOutputStream fos = null;
        try {
            //打开文件输出流，接收参数是文件名和模式
            fos = context.openFileOutput(cacheFileName, mode);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读取缓存，返回字符串（JSON）
    public static String getCache(Context context, String cacheFileName) {
        FileInputStream fis = null;
        StringBuffer sBuf = new StringBuffer();
        try {
            fis = context.openFileInput(cacheFileName);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) != -1) {
                sBuf.append(new String(buf, 0, len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (sBuf != null) {
            return sBuf.toString();
        }
        return null;
    }

    public static String getCachePath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }
}
