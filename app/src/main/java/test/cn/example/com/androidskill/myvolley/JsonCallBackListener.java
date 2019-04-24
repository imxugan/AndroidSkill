package test.cn.example.com.androidskill.myvolley;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 内部回调接口的实现类
 * Created by xugan on 2019/4/24.
 */

public class JsonCallBackListener <T> implements CallBackListener {
    //返回的对象类型(请求路径之后返回的对象类型)
    private Class<T> responseClazz;
    //应用层的回调接口
    private IJsonDataListener iJsonDataListener;
    //创建一个线程切换对象
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonCallBackListener(Class<T> responseClazz,IJsonDataListener iJsonDataListener){
        this.iJsonDataListener = iJsonDataListener;
        this.responseClazz = responseClazz;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //将字节流转成对象
        String content = getContent(inputStream);
        Gson gson = new Gson();
        final T t = gson.fromJson(content, responseClazz);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                iJsonDataListener.onSuccess(t);
            }
        });
    }

    @Override
    public void onFailed() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                iJsonDataListener.onFailed();
            }
        });
    }

    private String getContent(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != br){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
