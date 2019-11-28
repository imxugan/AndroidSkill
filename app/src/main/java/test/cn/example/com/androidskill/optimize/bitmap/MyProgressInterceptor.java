package test.cn.example.com.androidskill.optimize.bitmap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyProgressInterceptor implements Interceptor {

    static final Map<String,MyProgressListener> listeners = new HashMap<String,MyProgressListener>();

    static void addListener(String url ,MyProgressListener progressListener){
        listeners.put(url,progressListener);
    }

    static void removeListener(String url){
        listeners.remove(url);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        MyProgressBody myProgressBody = new MyProgressBody(request.url().toString(),body);
        Response newResponse = response.newBuilder().body(myProgressBody).build();
        return newResponse;
    }
}
