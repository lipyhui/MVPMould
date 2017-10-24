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

	/** 待发送数据 */
	private byte[] mData;
	/** 数据校验信息 */
	private byte[] mVerify;

	/** 记录BOOL类型元件名称(按顺序排序),例：X100 */
	private List<String> mBitElementName;
	/** 记录WORD类型元件名称(按顺序排序)，例：D100 */
	private List<String> mWordElementName;
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
	 * @param data            协议数据域
	 * @param verify          协议校验
	 * @param bitElementName  位元件名称列表(按顺序)
	 * @param wordElementName 字元件名称列表(按顺序)
	 * @param wordType        字元件的数据类型列表(按顺序)
	 * @param bitCount        位元件个数
	 */
	private PLCManager(byte[] data, byte[] verify, List<String> bitElementName, List<String> wordElementName,
			List<TYPE> wordType, int bitCount) {
		mData = data;
		mVerify = verify;
		mBitElementName = bitElementName;
		mWordElementName = wordElementName;
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
		return SocketClient.sendMsg(mData, mVerify)
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
	 * @param bytes1       待合并 byte 数组 1
	 * @param bytes1Length 待合并 byte 数组 1 的长度，从第 0 为开始合并
	 * @param bytes2       待合并 byte 数组 2
	 * @param bytes2Length 待合并 byte 数组 2 的长度，从第 0 为开始合并
	 * @return bytes1 与 bytes2 按相应长度拼接后的结果
	 */
	private static byte[] addBytes(byte[] bytes1, int bytes1Length, byte[] bytes2, int bytes2Length) {

		byte[] data = new byte[bytes1Length + bytes2Length];

		if (bytes1Length == 0) {    //当bytes1数组为空时，只处理bytes2数组，优化性能
			System.arraycopy(bytes2, 0, data, 0, bytes2Length);
		} else if (bytes2Length == 0) {    //当bytes2数组为空时，只处理bytes1数组，优化性能
			System.arraycopy(bytes1, 0, data, 0, bytes1Length);
		} else {
			System.arraycopy(bytes1, 0, data, 0, bytes1Length);
			System.arraycopy(bytes2, 0, data, bytes1Length, bytes2Length);
		}

		return data;
	}

	/*****************************************************************************
	 * 								读元件构造器
	 *****************************************************************************/
	public static final class ReadBuilder {
		/** 去除 2 字节的校验码长度，协议头部内容放入bits数组中 */
		private int MAX_BIT_LEN = MAX_BUFF_LEN - 2;
		/** 去除 8 字节的头部长度，2 字节的校验码长度 */
		private int MAX_WORD_LEN = MAX_BUFF_LEN - 8 - 2;

		/** 定义位元件数据 */
		private byte[] bits;
		/** 定义字元件数据 */
		private byte[] words;

		/** 记录BOOL类型元件名称(按顺序排序),例：X100 */
		private List<String> bitElementName;
		/** 记录WORD类型元件名称(按顺序排序)，例：D100 */
		private List<String> wordElementName;
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
			bits = new byte[MAX_BIT_LEN];
			words = new byte[MAX_WORD_LEN];

			bitElementName = new ArrayList<>();
			wordElementName = new ArrayList<>();
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
			if ((8 + bytesCount + singleBit) > MAX_BIT_LEN) {
				return this;
			}

			//从协议头部后开始接收位元件
			int start = bitCount * singleBit + 8;
			bits[start] = element.getCode();
			bits[start + 1] = (byte) (addr >> 8);
			bits[start + 2] = (byte) addr;

			//统计
			bitElementName.add(element.name() + addr);
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
			if ((bytesCount + singleWord) > MAX_WORD_LEN) {
				return this;
			}

			int start = wordCount * singleWord;
			words[start] = element.getCode();
			words[start + 1] = (byte) (addr >> 8);
			words[start + 2] = (byte) addr;

			//统计
			wordElementName.add(element.name() + addr);
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
			if ((bytesCount + singleWord * 2) > MAX_WORD_LEN) {
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
			wordElementName.add(element.name() + addr);
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
			if ((bytesCount + singleWord * 2) > MAX_WORD_LEN) {
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
			wordElementName.add(element.name() + addr);
			wordType.add(TYPE.REAL);
			wordCount += 2;
			bytesCount += singleWord * 2;
			return this;
		}

		public ReadBuilder readBoolList(List<PLCElement.ElementBOOL> bools) {
			for (PLCElement.ElementBOOL bool : bools) {
//				readBool(bool.)
			}
			return this;
		}

		public ReadBuilder readWORDList(List<PLCElement.ElementWORD> words) {
			return this;
		}

		public ReadBuilder readDWORDList(List<PLCElement.ElementDWORD> dwords) {
			return this;
		}

		public ReadBuilder readREALList(List<PLCElement.ElementREAL> reals) {
			return this;
		}

		public PLCManager build() {
			//判断是否有读元件
			if (bitCount == 0 && wordCount == 0) {
				return new PLCManager(null, null, null, null, null, 0);
			}

			buildHeader();
			byte[] data = addBytes(bits, 8 + bitCount * singleBit, words, wordCount * singleWord);
			return new PLCManager(data, VerifyUtil.calcCrc16(data, 1, data.length - 1), bitElementName,
					wordElementName, wordType, bitCount);
		}

		/**
		 * 构造协议头
		 */
		private void buildHeader() {
			//协议头部
			bits[0] = 0x52;
			bits[1] = 0x01;
			bits[2] = 0x69;
			bits[3] = 0x0B;
			//数据长度
			int count = bitCount + wordCount;
			bits[4] = (byte) (count >> 8);
			bits[5] = (byte) count;
			bits[6] = (byte) (bitCount >> 8);
			bits[7] = (byte) bitCount;
		}
	}

	/*****************************************************************************
	 * 								写元件构造器
	 *****************************************************************************/
	public static final class WriteBuilder {
		/** 去除 2 字节的校验码长度，协议头部内容放入bits数组中 */
		private int MAX_BIT_LEN = MAX_BUFF_LEN - 2;
		/** 去除 8 字节的头部长度，2 字节的校验码长度 */
		private int MAX_WORD_LEN = MAX_BUFF_LEN - 8 - 2;

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
			bits = new byte[MAX_BIT_LEN];
			words = new byte[MAX_WORD_LEN];
		}

		public WriteBuilder writeBool(final PLCElement.BOOL element, final short addr, final boolean value) {
			return this;
		}

		public PLCManager build() {
			//判断是否有写元件
			if (bitCount == 0 && wordCount == 0) {
				return new PLCManager(null, null, null, null, null, 0);
			}

			buildHeader();
			byte[] data = addBytes(bits, 8 + bitCount * singleBit, words, wordCount * singleWord);
			return new PLCManager(data, VerifyUtil.calcCrc16(data, 1, data.length - 1), null, null, null,
					bitCount);
		}

		/**
		 * 构造协议头
		 */
		private void buildHeader() {
			//协议头部
			bits[0] = 0x57;
			bits[1] = 0x01;
			bits[2] = 0x68;
			bits[3] = 0x0B;
			//数据长度
			int count = bitCount + wordCount;
			bits[4] = (byte) (count >> 8);
			bits[5] = (byte) count;
			bits[6] = (byte) (bitCount >> 8);
			bits[7] = (byte) bitCount;
		}
	}
}
