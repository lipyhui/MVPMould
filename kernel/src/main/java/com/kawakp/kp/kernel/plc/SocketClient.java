package com.kawakp.kp.kernel.plc;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

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

	private SocketClient() {
	}

	/**
	 * 发送消息
	 *
	 * @param msg 要发送的信息
	 * @return 响应信息
	 */
	public static Observable<byte[]> sendMsg(final byte[] msg) {
		return Observable.just(msg)
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io())
				.map(bytes -> {
					//创建socket
					LocalSocket client = new LocalSocket();
					LocalSocketAddress address = new LocalSocketAddress(SOCKET_NAME, LocalSocketAddress.Namespace
							.RESERVED);

					//连接本地socket进程
					boolean isConnected = false;
					int connectCount = 0;
					Log.e("socket_Test", "start connect!");
					while (!isConnected && connectCount <= 10) {
						try {
							if (connectCount != 0) {
								Thread.sleep(20);
							}
							client.connect(address);
							isConnected = true;
						} catch (Exception e) {
							connectCount++;
							isConnected = false;
							Log.e("SocketClient", "Connect fail, err = " + e.toString());
						}
					}

					//发送数据
					Log.e("socket_Test", "start write!");
					client.getOutputStream().write(bytes);

					//接收响应
					Log.e("socket_Test", "start read!");
					byte[] result = readStream(client.getInputStream());
					for (byte b : result) {
						Log.e("socket_Test_result", byteToHexString(b));
//						Log.e("socket_Test_result", byteToHexString(b) + ", length = " + length + ", count = " + count);
//						Log.e("socket_Test_result", byteToHexString(b) + ", length = " + bytes.length);
					}
					return result;
				});
	}

	/**
	 * 读取数据
	 *
	 * @param inStream 输入数据流
	 * @return 反回读取结果
	 * @throws Exception 读取异常
	 */
	private static byte[] readStream(InputStream inStream) throws Exception {
		/*ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();*/

		int count = 0;
		while (count == 0) {
			count = inStream.available();
		}
		byte[] bytes = new byte[count];
		int readCount = 0; // 已经成功读取的字节的个数
		while (readCount < count) {
			readCount += inStream.read(bytes, readCount, count - readCount);
		}
		return bytes;
	}

	public static String byteToHexString(byte src) {
		int j = src;
		if (j < 0) {
			j += 256;
		}
		String s = Integer.toHexString(j);
		if (s.length() < 2) {
			s = "0" + s;
		}
		return s;
	}
}

