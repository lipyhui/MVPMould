package com.kawakp.kp.kernel.plc;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kawakp.kp.kernel.utils.VerifyUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/23
 * 修改人:penghui.li
 * 修改时间:2017/10/23
 * 修改内容:
 *
 * 功能描述:KAWA PLC读写操作(超出缓冲区，默认丢失后加入的元件)
 */

public class PLCManager {
	/** 定义字数据类型 */
	private enum TYPE {
		WORD,
		DWORD,
		REAL
	}

	/** 定义PLC读写最大缓存区大小 */
	private static int MAX_BUFF_LEN = 1024;

	/** 待发送协议头部 */
	private byte[] mHeader;
	/** 待发送数据 */
	private byte[] mData;
	/** 数据校验信息 */
	private byte[] mVerify;

	/** WORD 数据的类型列表(按顺序排序) */
	private List<TYPE> mWordType;

	/** 位元件个数 */
	private int mBitCount = 0;

	public interface OnPLCResposneListener {
		void onPLCResponse(PLCResponse response);
	}

	/**
	 * 构造方法
	 *
	 * @param header   协议头
	 * @param data     协议数据域
	 * @param verify   协议校验
	 * @param wordType 字数据类型列表(按顺序)
	 * @param bitCount 位元件个数
	 */
	private PLCManager(byte[] header, byte[] data, byte[] verify, List<TYPE> wordType, int bitCount) {
		mHeader = header;
		mData = data;
		mVerify = verify;
		mWordType = wordType;
		mBitCount = bitCount;
	}

	/**
	 * 异步发送数据，不关心返回
	 */
	public void startAsync() {
		start().subscribe();
	}

	/**
	 * 开始发送数据并接收响应数据
	 *
	 * @param listener 监听返回响应数据
	 */
	public void start(@NonNull OnPLCResposneListener listener) {
		start().subscribe(response -> {
		});
	}

	/**
	 * 开始发送数据并接收响应数据,以 rxJava 方式返回
	 *
	 * @return 返回响应数据
	 */
	public Observable<PLCResponse> start() {
		return SocketClient.sendMsg(mHeader, mData, mVerify)
				.map(bytes -> {
//					for (byte b : bytes) {
					for (int i = 0; i < bytes.length; i++) {
						Log.e("socket_Test_response", "byte[" + i + "] = " + Integer.toHexString(bytes[i] & 0xff));
					}
					return new PLCResponse();
				});
	}

	/**
	 * 合并两个byte数组
	 *
	 * @param bits  待合并位元件域 byte 数组 1
	 * @param words 待合并字元件域 byte 数组 2
	 * @return data 与 words 拼接的结果
	 */
	private static byte[] addBytes(byte[] bits, int bitLength, byte[] words, int wordLength) {

		byte[] data = new byte[bitLength + wordLength];

		if (bitLength == 0) {    //当只有字元件时，只处理字元件数组，优化性能
			System.arraycopy(words, 0, data, 0, wordLength);
		} else if (wordLength == 0) {    //当只有位元件时，只处理位元件数组，优化性能
			System.arraycopy(bits, 0, data, 0, bitLength);
		} else {
			System.arraycopy(bits, 0, data, 0, bitLength);
			System.arraycopy(words, 0, data, bitLength, wordLength);
		}

		return data;
	}

	/*****************************************************************************
	 * 								读元件构造器
	 *****************************************************************************/
	public static final class ReadBuilder {
		/** 去除 8 字节的头部长度，2 字节的校验码长度 */
		private int MAX_DATA_LEN = MAX_BUFF_LEN - 8 - 2;

		/** 定义位元件数据 */
		private byte[] bits;
		/** 定义字元件数据 */
		private byte[] words;
		/** 记录需读取 WORD 数据的类型(按顺序排序) */
		private List<TYPE> wordType;

		/** 统计添加元件的数据域(bits + words)总长度,避免总长度超过缓存区大小 */
		private int bytesCount = 0;

		/** 统计要读的位元件个数 */
		private int bitCount = 0;
		/** 统计要读的字元件个数 */
		private int wordCount = 0;

		/** 读一个位元件需要的字节数 */
		private int singleBit = 3;
		/** 读一个字元件需要的字节数 */
		private int singleWord = 3;

		public ReadBuilder() {
			bits = new byte[MAX_DATA_LEN];
			words = new byte[MAX_DATA_LEN];

			wordType = new ArrayList<TYPE>();
		}

		/**
		 * 读布尔(BOOL)数据
		 *
		 * @param element 布尔元件类型
		 * @param addr    元件地址
		 * @return 当前建造类
		 */
		public ReadBuilder readBool(final PLCElement.BOOL element, final int addr) {
			//防止数据超过缓冲大小
			if ((bytesCount + singleBit) > MAX_DATA_LEN) {
				return this;
			}

			int start = bitCount * singleBit;
			bits[start] = element.getCode();
			bits[start + 1] = (byte) (addr >> 8);
			bits[start + 2] = (byte) addr;

			//统计
			bitCount++;
			bytesCount += singleBit;
			return this;
		}

		/**
		 * 读字(WORD)数据
		 *
		 * @param element 布尔元件类型
		 * @param addr    元件地址
		 * @return 当前建造类
		 */
		public ReadBuilder readWord(final PLCElement.WORD element, final int addr) {
			//防止数据超过缓冲大小
			if ((bytesCount + singleWord) > MAX_DATA_LEN) {
				return this;
			}

			int start = wordCount * singleWord;
			words[start] = element.getCode();
			words[start + 1] = (byte) (addr >> 8);
			words[start + 2] = (byte) addr;

			//统计
			wordType.add(TYPE.WORD);
			wordCount++;
			bytesCount += singleWord;
			return this;
		}

		/**
		 * 读双字(DWORD)数据
		 *
		 * @param element 布尔元件类型
		 * @param addr    元件地址
		 * @return 当前建造类
		 */
		public ReadBuilder readDWord(final PLCElement.DWORD element, final int addr) {
			//防止数据超过缓冲大小
			if ((bytesCount + singleWord * 2) > MAX_DATA_LEN) {
				return this;
			}

			int start = wordCount * singleWord;
			words[start] = element.getCode();
			words[start + 1] = (byte) (addr >> 8);
			words[start + 2] = (byte) addr;

			int addr2 = addr + 1;
			words[start + 3] = element.getCode();
			words[start + 4] = (byte) (addr2 >> 8);
			words[start + 5] = (byte) addr2;

			//统计
			wordType.add(TYPE.DWORD);
			wordCount += 2;
			bytesCount += singleWord * 2;
			return this;
		}

		/**
		 * 读双字(REAL)数据
		 *
		 * @param element 布尔元件类型
		 * @param addr    元件地址
		 * @return 当前建造类
		 */
		public ReadBuilder readREAL(final PLCElement.REAL element, final int addr) {
			//防止数据超过缓冲大小
			if ((bytesCount + singleWord * 2) > MAX_DATA_LEN) {
				return this;
			}

			int start = wordCount * singleWord;
			words[start] = element.getCode();
			words[start + 1] = (byte) (addr >> 8);
			words[start + 2] = (byte) addr;

			int addr2 = addr + 1;
			words[start + 3] = element.getCode();
			words[start + 4] = (byte) (addr2 >> 8);
			words[start + 5] = (byte) addr2;

			//统计
			wordType.add(TYPE.REAL);
			wordCount += 2;
			bytesCount += singleWord * 2;
			return this;
		}

		public PLCManager build() {
			//判断是否有读元件
			if (bitCount == 0 && wordCount == 0) {
				return new PLCManager(null, null, null, null, 0);
			}

			byte[] data = addBytes(bits, bitCount * singleBit, words, wordCount * singleWord);
			return new PLCManager(buildHeader(), data, VerifyUtil.calc_crc16(data, 0, data.length), wordType,
					bitCount);
		}

		/**
		 * 读协议头部构造
		 */
		private byte[] buildHeader() {
			byte[] header = new byte[8];
			//协议头部
			header[0] = 0x52;
			header[1] = 0x01;
			header[2] = 0x69;
			header[3] = 0x0B;
			//数据长度
			int count = bitCount + wordCount;
			header[4] = (byte) (count >> 8);
			header[5] = (byte) count;
			header[6] = (byte) (bitCount >> 8);
			header[7] = (byte) bitCount;
			return header;
		}
	}

	/*****************************************************************************
	 * 								写元件构造器
	 *****************************************************************************/
	public static final class WriteBuilder {
		/** 去除 8 字节的头部长度，2 字节的校验码长度 */
		private int MAX_DATA_LEN = MAX_BUFF_LEN - 8 - 2;

		/** 定义位元件数据 */
		private byte[] bits;
		/** 定义字元件数据 */
		private byte[] words;

		/** 统计添加元件的数据域(bits + words)总长度,避免总长度超过缓存区大小 */
		private int bytesCount = 0;

		/** 统计要写的位元件个数 */
		private int bitCount = 0;
		/** 统计要写的字元件个数 */
		private int wordCount = 0;

		/** 写一个位元件需要的字节数 */
		private int singleBit = 4;
		/** 写一个字元件需要的字节数 */
		private int singleWord = 5;

		public WriteBuilder() {
			bits = new byte[MAX_DATA_LEN];
			words = new byte[MAX_DATA_LEN];
		}

		public WriteBuilder writeBool(final PLCElement.BOOL element, final short addr, final boolean value) {
			return this;
		}

		public PLCManager build() {
			//判断是否有写元件
			if (bitCount == 0 && wordCount == 0) {
				return new PLCManager(null, null, null, null, 0);
			}

			byte[] data = addBytes(bits, bitCount * singleBit, words, wordCount * singleWord);
			return new PLCManager(buildHeader(), data, VerifyUtil.calc_crc16(data, 0, data.length), null, bitCount);
		}

		/**
		 * 写协议头部构造
		 */
		private byte[] buildHeader() {
			byte[] header = new byte[8];
			//协议头部
			header[0] = 0x57;
			header[1] = 0x01;
			header[2] = 0x68;
			header[3] = 0x0B;
			//数据长度
			int count = bitCount + wordCount;
			header[4] = (byte) (count >> 8);
			header[5] = (byte) count;
			header[6] = (byte) (bitCount >> 8);
			header[7] = (byte) bitCount;
			return header;
		}
	}
}
