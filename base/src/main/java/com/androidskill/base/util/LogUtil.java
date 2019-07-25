package com.androidskill.base.util;

import android.util.Log;

import com.androidskill.base.BuildConfig;


/**
 * Created by xugan on 2019/4/12.
 */

public class LogUtil {
    public final static int LOG_DUBEG = 0;
    public final static int LOG_INFO = 1;
    public final static int LOG_WARN = 2;
    public final static int LOG_ERROR = 3;
    private static String TAG = "MY_LOG";
    private static String INFO = "-->>";
    private static String INIO = "::";
    //测试与线上要修改 动态控制
    private static int DEBUG = BuildConfig.DEBUG ? 0 : 0;

    public final static void e(String message) {
        if (DEBUG <= LOG_ERROR) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            Log.e(TAG,
                    stackTrace.getClassName() + INIO + stackTrace.getLineNumber() + INIO + stackTrace.getMethodName()
                            + INFO + message);
        }
    }

    public final static void i(String message) {
        if (DEBUG <= LOG_INFO) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            Log.i(TAG,
                    stackTrace.getClassName() + INIO + stackTrace.getLineNumber() + INIO + stackTrace.getMethodName()
                            + INFO + message);
        }
    }

    public final static void d(String message) {
        if (DEBUG <= LOG_DUBEG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            Log.d(TAG,
                    stackTrace.getClassName() + INIO + stackTrace.getLineNumber() + INIO + stackTrace.getMethodName()
                            + INFO + message);
        }
    }

    public final static void w(String message) {
        if (DEBUG <= LOG_WARN) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            Log.w(TAG,
                    stackTrace.getClassName() + INIO + stackTrace.getLineNumber() + INIO + stackTrace.getMethodName()
                            + INFO + message);
        }
    }

    public final static void ex(Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG,e.getMessage(), e);
        }
    }
}
