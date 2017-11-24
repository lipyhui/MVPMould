package com.kawakp.kp.kernel.plc.siemens;

import static android.R.attr.host;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.kawakp.kp.kernel.plc.bean.PLCConfig;
import com.kawakp.kp.kernel.plc.kawa.PLCSyncManager;
import com.kawakp.kp.kernel.plc.kawa.SyncElement;
import com.kawakp.kp.kernel.plc.nodave.Nodave;
import com.kawakp.kp.kernel.plc.nodave.PLCinterface;
import com.kawakp.kp.kernel.plc.nodave.TCPConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/11/21
 * 修改人:penghui.li
 * 修改时间:2017/11/21
 * 修改内容:
 *
 * 功能描述:西门子后台服务
 */

public class SiemensService extends Service {
	private final String TAG = "SiemensService";

	/** 基本的 client */
	private static final String SIEMENS_CLIENT_ID = "kawaSiemens";

	/** 针对于 SiemensService 的启动、停止、改变IP action */
	private static final String ACTION_HOST = SIEMENS_CLIENT_ID + ".HOST";
	private static final String ACTION_CONFIG_PATH = SIEMENS_CLIENT_ID + ".CONFIG";
	private static final String ACTION_START = SIEMENS_CLIENT_ID + ".START";
	private static final String ACTION_STOP = SIEMENS_CLIENT_ID + ".STOP";

	/** Host 的标志 */
	private static final String HOST_KEY = "hostKey";

	/** 西门子配置列表路径的标志 */
	private static final String CONFIG_KEY = "configPathKey";

	/** 西门子同步更新时间(最小值为 10)，单位 ms */
	private final int UPDATE_TIME = 10;
	/** 西门子连接设备时间(最小值为 1)，单位 ms */
	private final int CONNECT_TIME = 200;

	/** 一次最多同步元件个数 */
	private final int MAX_NUM = 25;

	/** 西门子配置表在 assets 中的完整路径 */
	private String mConfigPath = "config/SiemensConfig.json";

	/** 西门子设备默认 HOST(IP) */
	private String mHost = "192.168.0.1";

	/** 西门子连接状态 */
	private boolean mConnection = false;

	/** ISO TCP 读写配置 */
	private Socket socket = null;
	private PLCinterface di = null;
	private TCPConnection dc = null;
	private int slot = 1;

	/** 待发送数据缓冲区 */
	private byte[] by;

	/** 读取西门子配置列表 */
	private List<PLCConfig> mConfigs = null;

	/** 同步数据缓存，用于从云下发数据同步到西门子 */
	private Map<String, byte[]> mValue = null;

	/** RxJava 订阅统一管理 */
	private CompositeDisposable mDisposables = null;

	/**
	 * 启动西门子同步服务
	 *
	 * @param ctx 上下文
	 */
	public static void actionStart(Context ctx) {
		Intent i = new Intent(ctx, SiemensService.class);
		i.setAction(ACTION_START);
		ctx.startService(i);
	}

	/**
	 * 停止西门子同步服务
	 *
	 * @param ctx 上下文
	 */
	public static void actionStop(Context ctx) {
		Intent i = new Intent(ctx, SiemensService.class);
		i.setAction(ACTION_STOP);
		ctx.startService(i);
	}

	/**
	 * 设置待连接西门子设备的 Host(IP)，可以动态修改 Host
	 *
	 * @param ctx  上下文
	 * @param host 西门子设备的 IP
	 */
	public static void actionSetHost(Context ctx, String host) {
		Intent i = new Intent(ctx, SiemensService.class);
		i.setAction(ACTION_HOST);
		i.putExtra(HOST_KEY, host);
		ctx.startService(i);
	}

	/**
	 * 设置待连接西门子设备的 Host(IP)，可以动态修改 Host
	 *
	 * @param ctx        上下文
	 * @param configPath 西门子配置列表的路径
	 */
	public static void actionSetAssetsPath(Context ctx, String configPath) {
		Intent i = new Intent(ctx, SiemensService.class);
		i.setAction(ACTION_CONFIG_PATH);
		i.putExtra(CONFIG_KEY, host);
		ctx.startService(i);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		mDisposables = new CompositeDisposable();
		analyConfig();
		initSyncValue();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		if (intent == null) {
			return;
		}

		if (intent.getAction().equals(ACTION_HOST)) {
		} else if (intent.getAction().equals(ACTION_CONFIG_PATH)) {
		} else if (intent.getAction().equals(ACTION_START)) {
			start();
		} else if (intent.getAction().equals(ACTION_STOP)) {
			stop();
		}
	}

	private synchronized void start() {
		//防止 Rxjava 无法取消订阅导致
		if (mDisposables == null) {
			return;
		}

		mDisposables.add(
				Observable.just(0)
						.subscribeOn(Schedulers.io())
						.observeOn(Schedulers.io())
						.map(it -> {
							dc = null;
							di = null;

							createSock();
							connection(socket);

							sync();

							return it;
						})
						.subscribe()
		);
	}

	private synchronized void stop() {
		mDisposables.dispose();
		stopConnection();
		dc = null;
		di = null;

		//CONNECT_TIME ms 后再重新启动西门子后台同步服务
		Observable.timer(CONNECT_TIME, TimeUnit.MILLISECONDS)
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io())
				.subscribe(new Observer<Long>() {
					private Disposable mDisposable = null;

					@Override
					public void onSubscribe(@NonNull Disposable d) {
						mDisposable = d;
					}

					@Override
					public void onNext(@NonNull Long aLong) {
						actionStart(getApplicationContext());
						mDisposable.dispose();
					}

					@Override
					public void onError(@NonNull Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});
	}

	/**
	 * 创建 socket
	 *
	 * @return 返回创建的 socket 对象
	 */
	private void createSock() {
		try {
			socket = null;
			socket = new Socket(mHost, 102);
		} catch (IOException e) {
			log("create socket error -----> " + e.toString());
		}
	}

	/**
	 * 连接西门子
	 */
	private void connection(Socket socket) {
		mConnection = false;
		OutputStream oStream = null;
		InputStream iStream = null;
		slot = 1;

		if (socket != null) {
			try {
				oStream = socket.getOutputStream();
			} catch (IOException e) {
				log("get outputStream error -----> " + e.toString());
			}
			try {
				iStream = socket.getInputStream();
			} catch (IOException e) {
				log("get inputStream error -----> " + e.toString());
			}
			di = new PLCinterface(
					oStream,
					iStream,
					"IF1",
					0,
					Nodave.PROTOCOL_ISOTCP);

			dc = new TCPConnection(di, 0, slot);
			int res = dc.connectPLC();
			if (0 == res) {
				mConnection = true;
				log("connection info -----> Connection OK!");
			} else {
				log("connection info -----> No connection!");
			}
		}
	}

	/**
	 * 断开西门子连接
	 */
	private void stopConnection() {
		if (mConnection) {
			mConnection = false;
			dc.disconnectPLC();
			di.disconnectAdapter();
		}
	}

	/**
	 * 西门子 PLC 于 KWAW PLC 数据同步
	 */
	private void sync() {
		//防止未连接西门子或连接失败
		if (dc == null || di == null || !mConnection) {
			stop();
			return;
		}

		//防止没有配置列表
		if (mConfigs == null || mConfigs.size() <= 0) {
			analyConfig();
		}

		//防止没有初始化同步缓存数据
		if (mValue == null || mValue.size() <= 0) {
			initSyncValue();
		}

		for (PLCConfig config : mConfigs) {
			mDisposables.add(
					Observable.just(config)
							.repeatWhen(it -> it.delay(UPDATE_TIME, TimeUnit.MILLISECONDS))
							.retryWhen(it -> it.delay(UPDATE_TIME, TimeUnit.MILLISECONDS))
							.subscribeOn(Schedulers.io())
							.observeOn(Schedulers.io())
							.subscribe(cfg -> {
								//防止连接掉线，掉线停止连接并重连
								if (!socket.getInetAddress().isReachable(1000)) {
									stop();
									return;
								}

								//需要读取西门子的 byte 数
								int length = getSiemensLength(cfg.getType(), cfg.getNum());

								//读取西门子数据
								dc.readBytes(getAreaValue(cfg.getArea()), cfg.getDbNum(),
										getSiemensStart(cfg.getType(), cfg.getStart()), length, by);

								//KAWA 元件写数据控制器
								PLCSyncManager.WriteBuilder write = new PLCSyncManager.WriteBuilder();

								if (cfg.getType().equals("BOOL")) {    //BOOL 类型数据解析同步
									//KAWA 元件类型
									SyncElement element = getElementValue(cfg.getElement());

									//防止不支持的元件
									if (element == null) {
										return;
									}

									//初始化读取西门子数据为0
									int b = 0;
									for (int i = 0; i < cfg.getNum(); i++) {
										int addr = cfg.getStartAddr() + i;

										int position = (i + cfg.getStart()) % 8;

										//读取西门子元件值,每 8 位读一次
										if (position == 0) {
											b = dc.getBYTE();
										}

										byte[] bytes = {(byte) ((b >> position) & 0x01)};

										//同步数据备份
										mValue.put(cfg.getElement() + addr, bytes);
										//数据加入
										write.writeBool(element, addr, bytes[0]);
									}    //end of for (int i = 0; i < cfg.getNum(); i++)

								} else {    //字、字节类型数据解析同步
									int typeSize = getSingleTypeSize(cfg.getType());

									//防止不存在的数据类型
									if (typeSize <= 0) {
										return;
									}

									//KAWA 元件类型
									SyncElement element = getElementValue(cfg.getElement());

									//防止不支持的元件
									if (element == null) {
										return;
									}

									for (int i = 0; i < cfg.getNum(); i++) {
										switch (cfg.getType()) {
											case "WORD": {
												int addr = cfg.getStartAddr() + i;
												//读取西门子元件值
												byte[] bytes = {(byte) dc.getBYTE(), (byte) dc.getBYTE()};
												//同步数据备份
												mValue.put(cfg.getElement() + addr, bytes);
												//数据加入
												write.writeWord(element, addr, bytes);
												break;
											}

											case "DWORD": {
												int addr = cfg.getStartAddr() + i * 2;
												byte[] bytes = {(byte) dc.getBYTE(), (byte) dc.getBYTE(),
														(byte) dc.getBYTE(), (byte) dc.getBYTE()};
												mValue.put(cfg.getElement() + (cfg.getNum() + i), bytes);
												write.writeDWord(element, addr, bytes);
												break;
											}

											case "REAL": {
												int addr = cfg.getStartAddr() + i * 2;
												byte[] bytes = {(byte) dc.getBYTE(), (byte) dc.getBYTE(),
														(byte) dc.getBYTE(), (byte) dc.getBYTE()};
												mValue.put(cfg.getElement() + (cfg.getNum() + i), bytes);
												write.writeReal(element, addr, bytes);
												break;
											}
										}    //end of switch (cfg.getType())
									}    //end of for (int i = 0; i < cfg.getNum(); i++)
								}    //end of if (cfg.getType().equals("BOOL"))

								log("----------------> write start");
								//开始同步(西门子数据写入KAWA)
								write.build().start();
							})
			);
		}    //end of for (PLCConfig cfg : mConfigs)
	}

	/**
	 * 解析 PLC 配置列表
	 */
	private void analyConfig() {
		mConfigs = null;
		mConfigs = new ArrayList<>();

		try {
			List<PLCConfig> configBuffs = Arrays.asList(new Gson().fromJson(readStream(getAssets().open(mConfigPath)),
					PLCConfig[].class));

			for (PLCConfig config : configBuffs) {

				//屏蔽数据个数小于等于0的列表
				if (config.getNum() <= 0) {
					continue;
				}

				//屏蔽不支持的元件类型
				int singleTypeSize = getSingleTypeSize(config.getType());
				if (singleTypeSize <= 0) {
					continue;
				}

				//读取配置列表
				if (config.getNum() > MAX_NUM) {    //数据个数大于最大个数，对配置列表进行分割

					int KAWASingleTypeSize = getKAWASingleTypeSize(config.getType());

					int offset = 0;
					for (int i = 0; i < config.getNum() / MAX_NUM; i++) {
						offset = i * MAX_NUM;
						mConfigs.add(new PLCConfig(config.getType(), config.getArea(), config.getDbNum(),
								config.getStart() + offset * singleTypeSize, MAX_NUM, config.getElement(),
								config.getStartAddr() + offset * KAWASingleTypeSize));
					}

					if (config.getNum() % MAX_NUM > 0) {
						offset += MAX_NUM;
						mConfigs.add(new PLCConfig(config.getType(), config.getArea(), config.getDbNum(),
								config.getStart() + offset * singleTypeSize, config.getNum() - offset,
								config.getElement(), config.getStartAddr() + offset * KAWASingleTypeSize));
					}

				} else {
					mConfigs.add(config);
				}
			}
//			log("read assets = " + mConfigs.toString());
		} catch (Exception e) {
			log("read and analy config list -----> " + e.toString());
		}
	}

	/**
	 * 初始化同步缓存的数据
	 */
	private void initSyncValue() {
		mValue = null;
		mValue = new HashMap<>();

		//缓存同步数据
		for (PLCConfig cfg : mConfigs) {
			for (int i = 0; i < cfg.getNum(); i++) {
				switch (cfg.getType()) {
					case "BOOL":
						mValue.put(cfg.getElement() + cfg.getStartAddr() + i, new byte[]{0});
						break;

					case "WORD":
						mValue.put(cfg.getElement() + cfg.getStartAddr() + i, new byte[]{0, 0});
						break;

					case "DWORD":
					case "REAL":
						mValue.put(cfg.getElement() + cfg.getStartAddr() + i, new byte[]{0, 0, 0, 0});
						break;

					default:
						break;
				}
			}
		}
	}

	/**
	 * 获取所有西门子读写区域
	 *
	 * @param area 西门子读写区域关键字
	 * @return 西门子读写区域
	 */
	private int getAreaValue(String area) {
		switch (area) {
			case "IN":
				return Nodave.INPUTS;

			case "OUT":
				return Nodave.OUTPUTS;

			case "DB":
				return Nodave.DB;

			case "P":
				return Nodave.P;

			case "FLAGS":
				return Nodave.FLAGS;

			case "COUNTER":
				return Nodave.COUNTER;

			case "TIMER":
				return Nodave.TIMER;

			case "ANAINS200":
				return Nodave.ANALOGINPUTS200;

			case "ANAOUT200":
				return Nodave.ANALOGOUTPUTS200;

			case "COUNTER200":
				return Nodave.COUNTER200;

			case "TIMER200":
				return Nodave.TIMER200;

			default:
				return -1;
		}
	}

	/**
	 * 获取西门子读写起始位置
	 *
	 * @param type  元件类型，目前只支持 BOOL、WORD、DWORD、REAL
	 * @param start 西门子配置列表里的起始地址
	 * @return 返回单个元件类型占用的字节数
	 */
	private int getSiemensStart(String type, int start) {
		switch (type) {
			case "BOOL":
				return (int) Math.ceil(start / 8F);

			case "WORD":
			case "DWORD":
			case "REAL":
				return start;

			default:
				return -1;
		}
	}

	/**
	 * 获取西门子读写 byte 个数
	 *
	 * @param type 元件类型，目前只支持 BOOL、WORD、DWORD、REAL
	 * @param num  西门子配置列表里的数据个数
	 * @return 返回单个元件类型占用的字节数
	 */
	private int getSiemensLength(String type, int num) {
		switch (type) {
			case "BOOL":
				return (int) Math.ceil(num / 8F);

			case "WORD":
				return num * 2;

			case "DWORD":
			case "REAL":
				return num * 4;

			default:
				return -1;
		}
	}

	/**
	 * 获取对应 KAWA 元件类型
	 *
	 * @param element KAWA 元件类型标志
	 * @return KAWA 元件类型
	 */
	private SyncElement getElementValue(String element) {
		switch (element) {
			case "X":
				return SyncElement.X;

			case "Y":
				return SyncElement.Y;

			case "M":
				return SyncElement.M;

			case "D":
				return SyncElement.D;

			case "SD":
				return SyncElement.SD;

			case "R":
				return SyncElement.R;

			default:
				return null;
		}
	}

	/**
	 * 获取单个元件类型需要占用的字节数(对应于 KAWA 同步的 PLC 元件地址)
	 *
	 * @param type 元件类型，目前只支持 BOOL、WORD、DWORD、REAL
	 * @return 返回单个元件类型占用的字节数
	 */
	private int getSingleTypeSize(String type) {
		switch (type) {
			case "BOOL":
				return 1;

			case "WORD":
				return 2;

			case "DWORD":
				return 4;

			case "REAL":
				return 4;

			default:
				return -1;
		}
	}

	/**
	 * 获取对于数据类型 KAWA 同步占用元件个数
	 */
	private int getKAWASingleTypeSize(String type) {
		int KAWASingleTypeSize = getSingleTypeSize(type) / 2;

		if (KAWASingleTypeSize < 1) {
			KAWASingleTypeSize = 1;
		}

		return KAWASingleTypeSize;
	}

	/**
	 * 读取数据
	 *
	 * @param inStream 输入数据流
	 * @return 反回读取结果
	 * @throws Exception 读取异常
	 */
	private static String readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toString();
	}

	/**
	 * 打印帮助日志
	 */
	private void log(String message) {
		log(message, null);
	}

	/**
	 * 打印帮助日志
	 */
	private void log(String message, Throwable e) {
		/*if (!APP.isDebug())
			return;*/
		if (e != null) {
			Log.e(TAG, message, e);
		} else {
			Log.i(TAG, message);
		}
	}

	@Override
	public void onDestroy() {
		stop();
		super.onDestroy();
	}

}
