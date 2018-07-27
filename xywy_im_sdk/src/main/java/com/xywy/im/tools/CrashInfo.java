package com.xywy.im.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/7/27.
 */

public class CrashInfo {
    public static void printErrorInfo(Exception ex){
        Map<String, String> infos = new HashMap<String, String>();
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
        LogUtil.i(result);
    }
}
