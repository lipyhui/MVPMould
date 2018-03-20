package com.kawakp.kp.kernel.plc.kawa;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/20
 * 修改人:penghui.li
 * 修改时间:2017/10/20
 * 修改内容:
 *
 * 功能描述:与Android系统Socket基础通信
 */
public class SocketClient {
	private static final String SOCKET_NAME = "plcd-apps";
	private static final int TIMEOUT = 500;

	private SocketClient() {
	}

	/**
	 * 发送消息
	 *
	 * @param data   要发送的数据
	 * @return 响应信息
	 */
	public static byte[] sendMsg(final byte[] data) {
		//创建socket
		LocalSocket client = new LocalSocket();
		LocalSocketAddress address = new LocalSocketAddress(SOCKET_NAME);

		//连接本地socket进程
		boolean isConnected = false;
		int connectCount = 0;
//					Log.e("java_plcd_SocketClient", "start connect !!!!!!!!!!!");
		while (!isConnected && connectCount < 10) {
			try {
				if (connectCount != 0) {
					Thread.sleep(20);
				}
				client.connect(address);
				isConnected = true;
			} catch (Exception e) {
				connectCount++;
				isConnected = false;
				Log.e("java_plcd_SocketClient", "Connect fail, err = " + e.toString());
			}
		}

		try {
			//防止未连接奔溃
			if (!client.isConnected()){
				//关闭 socket 并返回
				client.close();
				return new byte[0];
			}

			//设置读超时，防止一直阻塞
			client.setSoTimeout(TIMEOUT);

			//发送数据
//					Log.e("java_plcd_SocketClient", "start write! data lenght = " + data.length);
			if (data.length > 0) {
				client.getOutputStream().write(data);
				client.shutdownOutput();
			}

			//接收响应
//					Log.e("java_plcd_SocketClient", "start read!");
			byte[] result = readStream(client.getInputStream());

//			Log.e("java_plcd_SocketClient", "READ data result length = " + result.length);

			//关闭输入流、输出流、socket
		/*	client.shutdownOutput();
			client.shutdownInput();*/
			client.close();

//			Log.e("java_plcd_SocketClient", "READ WRITE END !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

			return result;
		} catch (Exception e) {
			//socket读写操作失败
			Log.e("java_plcd_SocketClient", "SendMsg fail, err = " + e.toString());
			return new byte[0];
		}
	}

	private static final byte READ_CODE = 0X69;    //读 PLC 数据功能码
	private static final byte WRITE_CODE = 0X68;    //写 PLC 数据功能码

	private static final byte DATA_MIN_LENGTH = 5;    //响应数据最小长度

	private static final byte READ_DATA_HEAD_LENGTH = 5;    //读响应数据最小长度，从站地址+功能码+子功能码+数据域长度
	private static final byte CRC_LENGTH = 2;    //接收数据 CRC 校验位占用字节数

	private static final byte WRITE_RESPOND_LENGTH = 5;    //写响应数据长度

	/**
	 * 读取数据
	 *
	 * @param inStream 输入数据流
	 * @return 反回读取结果
	 * @throws Exception 读取异常
	 */
	private static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[2048];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
			byte[] bytes = outSteam.toByteArray();

//			Log.e("java_plcd_SocketClient", "byte length = " + bytes.length);
//			for (byte b : bytes){
//				Log.e("java_plcd_SocketClient", "byte = " + Integer.toHexString(b & 0XFF));
//			}

			if (bytes.length < DATA_MIN_LENGTH) {
				continue;
			}

			switch (bytes[1]) {
				case READ_CODE: {
					int dataLength = (bytes[3] << 8) + bytes[4];
//					Log.e("java_plcd_SocketClient", "read data length = " + dataLength);
					if (bytes.length == (READ_DATA_HEAD_LENGTH + dataLength + CRC_LENGTH)) {	//接收到读的完整数据
						return bytes;
					}
					continue;
				}

				case WRITE_CODE: {
					if (bytes.length == WRITE_RESPOND_LENGTH) {	//接收到写响应的完整数据
						return bytes;
					}
					continue;
				}

				default:	//不支持类型
					return new byte[0];
			}
		}

		//正常情况运行不到这里
		return new byte[0];
	}
}