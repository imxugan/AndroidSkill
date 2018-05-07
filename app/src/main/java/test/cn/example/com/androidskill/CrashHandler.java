package test.cn.example.com.androidskill;

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
 *	ClassName:	CrashHandler
 *	Function: 	UncaughtException,当程序发生异常时，该类来捕获异常信息
 */
class CrashHandler implements UncaughtExceptionHandler {
	
	private static final String TAG = "CH";
	/**
	 * 	系统默认的UncaughtException处理类
	 * 	Thread.UncaughtExceptionHandler			:		mDefaultHandler	
	 */
	private UncaughtExceptionHandler mDefaultHandler ;
	/**
	 * 	CrashHandler实例
	 * 	CrashHandler:mInstance	
	 */
	private static CrashHandler mInstance = new CrashHandler() ;
	/**
	 * 	程序的Context对象
	 * 	Context:mContext	
	 */
	private Context mContext ;
	private Map<String, String> infos = new HashMap<String, String>();
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
	
	/**
	 * 	Creates a new instance of CrashHandler.
	 */
	private CrashHandler() {
	}
	/**
	 * 	getInstance:{获取CrashHandler实例，单例模式 }
	 * 	@return 	CrashHandler   
	 * 	@throws 
	 */
	public static CrashHandler getInstance() {
		return mInstance ;
	}
	/**
	 * 	init:{初始化}
	 * 	@param 		paramContext
	 * 	@return 	void   
	 * 	@throws 
	 */
	public void init(Context paramContext) {
		mContext = paramContext ;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler() ;
		Thread.setDefaultUncaughtExceptionHandler(this) ;
	}
	
	/**
	 * 	当UncaughtException发生时会转入该重写的方法处理
	 */
	@Override
	public void uncaughtException(Thread thread , Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}
	
	/** 
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成 
     *  
     * @param ex 
     * @return true：如果处理了该异常信息；否则返回 false 
     */  
    private boolean handleException(Throwable ex) {  
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
	public static String fileDir = Environment.getExternalStorageDirectory()+"/AndroidSkillCrash/";
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
			String fileName = "crash-" + time + "-" + timestamp + ".txt";
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
