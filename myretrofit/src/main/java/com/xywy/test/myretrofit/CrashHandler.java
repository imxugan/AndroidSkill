package com.xywy.test.myretrofit;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *  6.0手机上(包括6.0版本)需要动态授权
 * 程序拦截异常
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";
	private static CrashHandler instance;
	private UncaughtExceptionHandler mDefaultHandler;
	private Context mContext;
	private Map<String, String> infos = new HashMap<String, String>();
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);

	/**
	 * 保证只有一个CrashHandler实例
	 */
	private CrashHandler() {
	}

	/**
	 * 获取CrashHandler实例 ,单例模式
	 */
	public static CrashHandler getInstance() {
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}

	/**
	 * 初始化
	 */
	public void init(Context context) {
		mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		LogUtils.e(ex.toString());
		if (AppUtils.isDebug(mContext)){
			Log.e(TAG, Log.getStackTraceString(ex));
		}
		if (!handleException(ex) && mDefaultHandler != null) {

			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			AppUtils.restart(mContext);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 *
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		collectDeviceInfo(mContext);
		saveCatchInfo2File(ex);
		return true;
	}

	/**
	 * 收集设备参数信息
	 *
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		infos.put("versionName", BuildConfig.VERSION_NAME);
		infos.put("versionCode", BuildConfig.VERSION_CODE+"");
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}
	//6.0手机上(包括6.0版本)需要动态授权
	public static String fileDir = Environment.getExternalStorageDirectory()+"/crash_androidskill/";
	/**
	 * 保存错误信息到文件中
	 *
	 * @param ex
	 * @return 返回文件名称, 便于将文件传送到服务器
	 */
	private String saveCatchInfo2File(Throwable ex) {

		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append("=").append(value).append("\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "XYWYIMcrash-" + time + "-" + timestamp + ".txt";
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                String path = "/mnt/sdcard/crash/";
				File dir = new File(fileDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(fileDir + fileName);
				fos.write(sb.toString().getBytes());
				// 发送给开发人员
				sendCrashLog2PM(fileDir + fileName);
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occurred while writing file...", e);
		}
		return null;
	}

	/**
	 * 将捕获的导致崩溃的错误信息发送给开发人员
	 * <p>
	 * 目前只将log日志保存在sdcard 和输出到LogCat中，并未发送给后台。
	 */
	private void sendCrashLog2PM(String fileName) {
		if (!new File(fileName).exists()) {
			Toast.makeText(mContext, "日志文件不存在！", Toast.LENGTH_SHORT).show();
			return;
		}
	}
}
