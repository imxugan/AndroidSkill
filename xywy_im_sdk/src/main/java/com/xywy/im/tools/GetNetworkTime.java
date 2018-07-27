package com.xywy.im.tools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 读取网络时间
 * Created by xugan on 2018/7/27.
 */

public class GetNetworkTime {

    public static long getWebsiteDatetime(String webUrl){
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            return  uc.getDate();// 读取网站日期时间
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
