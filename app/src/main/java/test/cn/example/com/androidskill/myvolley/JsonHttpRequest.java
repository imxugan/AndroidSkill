package test.cn.example.com.androidskill.myvolley;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by xugan on 2019/4/24.
 */

public class JsonHttpRequest implements IHttpRequest {
    //访问路径
    private String url;
    //访问传递的参数
    private byte[] data;
    //请求对象的回调接口
    private CallBackListener callBackListener;
    //访问网络的API
    private HttpURLConnection httpURLConnection;



    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override
    public void execute() {
        URL urlTemp = null;
        try {
            urlTemp = new URL(this.url);
            httpURLConnection = (HttpURLConnection) urlTemp.openConnection();
            httpURLConnection.setConnectTimeout(60000);//设置连接超时时间
            httpURLConnection.setUseCaches(false);//不适用缓存
            httpURLConnection.setInstanceFollowRedirects(true);//是成员变量，仅作用于当期函数，设置当前这个对象
            httpURLConnection.setReadTimeout(3000);//相应超时时间
            httpURLConnection.setDoInput(true);//设置这个连接是否可以输出数据
            httpURLConnection.setDoInput(true);//设置这个连接是否可以写入数据
            httpURLConnection.setRequestMethod("POST");//设置这个请求的请求方法
            httpURLConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            httpURLConnection.connect();//建立连接
            //使用字节流返送数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            //高速缓冲字节流
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            //将字节流数组写入缓冲区
            bos.write(data);
            //刷新缓冲区
            bos.flush();
            bos.close();
            outputStream.close();
            //如果响应码为200，代表请求访问成功
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                callBackListener.onSuccess(inputStream);
            }else {
                throw new RuntimeException("请求失败");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");
        }finally {
            //关闭httpURLConnection对象
            httpURLConnection.disconnect();
        }
    }
}
