package com.kawakp.kp.kernel.localHttp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by xiaojun.ma on 2017/5/19.
 */

public class LocalHttpUtils {
    private static String mUrl;
    private static byte[] mData;
    private static ByteArrayOutputStream mBaos;
    private static int mIsThreadRunning;

    public static byte[] getBit(Constants type, int addr, int len) {
        byte[] result = new byte[len];
        for  (int i=0;i<len;i++) {
            result[i] = 0;
        }

        String url = "http://localhost:9800/a/api?t=" + type.getReadCmd() + "&a=" + Integer.toString(addr) + "&l=" + Integer.toString(len);
       // Log.e("LocalHttpUrl", "Bit url = "+ url.toString());
        byte[] b = sendPost(url, null);
        if (b.length == len) {
            System.arraycopy(b, 0, result, 0, len);
        }

        return result;
    }

    public static short[] getWord(Constants type, int addr, int len) {
        short[] result = new short[len];
        for  (int i=0;i<len;i++) {
            result[i] = 0;
        }

        String url = "http://localhost:9800/a/api?t=" + type.getReadCmd() + "&a=" + Integer.toString(addr) + "&l=" + Integer.toString(len);
        //Log.e("LocalHttpUrl", "url"+ url.toString());
        byte[] b = sendPost(url, null);
        if (b.length == len * 2) {
            result = b2s(b);
        }

        return result;
    }

    public static int[] getDWord(Constants type, int addr, int len) {
        int[] result = new int[len];
        for  (int i=0;i<len;i++) {
            result[i] = 0;
        }

        String url = "http://localhost:9800/a/api?t=" + type.getReadCmd() + "&a=" + Integer.toString(addr) + "&l=" + Integer.toString(len);
        byte[] b = sendPost(url, null);
        if (b.length == len * 4) {
            result = b2i(b);
        }

        return result;
    }

    public static float[] getReal(Constants type, int addr, int len) {
        float[] result = new float[len];
        for  (int i=0;i<len;i++) {
            result[i] = 0;
        }

        String url = "http://localhost:9800/a/api?t=" + type.getReadCmd() + "&a=" + Integer.toString(addr) + "&l=" + Integer.toString(len);
        byte[] b = sendPost(url, null);
        if (b.length == len * 4) {
            result = b2f(b);
        }

        return result;
    }

    public static void setBit(Constants type, byte buf[], int addr, int len) {
        String url = "http://localhost:9800/a/api?t=" + type.getWriteCmd() + "&a=" + Integer.toString(addr) + "&l=" + Integer.toString(len);
        sendPost(url, buf);
    }

    public static void setWord(Constants type, short buf[], int addr, int len) {
        String url = "http://localhost:9800/a/api?t=" + type.getWriteCmd() + "&a=" + Integer.toString(addr) + "&l=" + Integer.toString(len);
        //Log.e("LocalHttpUrl", "url"+ url.toString());
        sendPost(url, s2b(buf));
    }

    public static void setDWord(Constants type, int buf[], int addr, int len) {
        String url = "http://localhost:9800/a/api?t=" + type.getWriteCmd() + "&a=" + Integer.toString(addr) + "&l=" + Integer.toString(len);
        sendPost(url, i2b(buf));
    }

    public static void setReal(Constants type, float buf[], int addr, int len) {
        String url = "http://localhost:9800/a/api?t=" + type.getWriteCmd() + "&a=" + Integer.toString(addr) + "&l=" + Integer.toString(len);
        sendPost(url, f2b(buf));
    }

    private synchronized static byte[] sendPost(String url, byte[] data) {
        mUrl = url;
        mData = data;
        mBaos = null;
        mIsThreadRunning = 1;
        new myThread().start();
        while(mIsThreadRunning == 1)for(int i=0;i<10;i++);
        return mBaos.toByteArray();
    }

    private static short[] b2s(byte buf[]) {
        short[] result = new short[buf.length / 2];
        for (int i=0;i<buf.length/2;i++) {
            result[i] = (short) ((buf[i*2] & 0xff) | ((buf[i*2+1] << 8) & 0xff00));
        }
        return result;
    }

    private static int[] b2i(byte buf[]) {
        int[] result = new int[buf.length / 4];
        for (int i=0;i<buf.length/4;i++) {
            result[i] = (int) ((buf[i*2] & 0xff) | ((buf[i*2+1] << 8) & 0xff00) | ((buf[i*2+2] << 16) & 0xff0000) | ((buf[i*2+3] << 24) & 0xff000000));
        }
        return result;
    }

    private static float[] b2f(byte buf[]) {
        float[] result = new float[buf.length / 4];
        for (int i=0;i<buf.length/4;i++) {
            int a = (int) ((buf[i*2] & 0xff) | ((buf[i*2+1] << 8) & 0xff00) | ((buf[i*2+2] << 16) & 0xff0000) | ((buf[i*2+3] << 24) & 0xff000000));
            result[i] = Float.intBitsToFloat(a);
            result[i] = Float.intBitsToFloat(a);
            result[i] = Float.intBitsToFloat(a);
        }
        return result;
    }

    private static byte[] s2b(short buf[]) {
        byte[] result = new byte[buf.length * 2];
        for (int i=0;i<buf.length;i++) {
            result[i*2] = (byte)(buf[i] >> 0);
            result[i*2+1] = (byte)(buf[i] >> 8);
        }
        return result;
    }

    private static byte[] i2b(int buf[]) {
        byte[] result = new byte[buf.length * 4];
        for (int i=0;i<buf.length;i++) {
            result[i*4] = (byte)(buf[i] >> 0);
            result[i*4+1] = (byte)(buf[i] >> 8);
            result[i*4+2] = (byte)(buf[i] >> 16);
            result[i*4+3] = (byte)(buf[i] >> 24);
        }
        return result;
    }

    private static byte[] f2b(float buf[]) {
        byte[] result = new byte[buf.length * 4];
        for (int i=0;i<buf.length;i++) {
            int a = Float.floatToIntBits(buf[i]);
            result[i*4] = (byte)(a >> 0);
            result[i*4+1] = (byte)(a >> 8);
            result[i*4+2] = (byte)(a >> 16);
            result[i*4+3] = (byte)(a >> 24);
        }
        return result;
    }

    private static class myThread extends Thread {
        public void run() {
            try {
                //Log.e("LocalHttpThread", "myThread");

                mBaos = new ByteArrayOutputStream();
                URL realUrl = new URL(mUrl);
                URLConnection conn = realUrl.openConnection();
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream out = conn.getOutputStream();
                if (mData != null) {
                    out.write(mData);
                    out.flush();
                }

                out.close();

                InputStream in = conn.getInputStream();
                byte[] b = new byte[1024];
                int len;
                while ((len = in.read(b)) != -1)
                    mBaos.write(b, 0, len);

                in.close();

         /*       Log.e("LocalHttp", "len = " + mBaos.size());

                StringBuffer buffer = new StringBuffer();
                byte[] bytes =  mBaos.toByteArray();
                for (int i = 0; i < bytes.length; i++)
                    buffer.append(bytes[i]);

                Log.e("LocalHttpString", "mBaosBuff = " + buffer.toString());*/
                //Log.e("LocalHttpString", "string = " + String.valueOf(mBaos.toByteArray()));

            } catch (Exception e) {
                e.printStackTrace();
                // Log.e("LocalHttpThread", "myThread Error" + e.toString());
            }
            mUrl = null;
            mData = null;
            mIsThreadRunning = 0;
        }
    }
}
