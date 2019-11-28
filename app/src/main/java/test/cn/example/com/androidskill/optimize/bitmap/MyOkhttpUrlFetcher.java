package test.cn.example.com.androidskill.optimize.bitmap;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import test.cn.example.com.util.LogUtil;

public class MyOkhttpUrlFetcher implements DataFetcher<InputStream> {
    private GlideUrl mGlideUrl;
    private InputStream stream;
    private volatile boolean isCancelled;
    private OkHttpClient mOkHttpClient;
    private ResponseBody responseBody;

    MyOkhttpUrlFetcher(GlideUrl glideUrl,OkHttpClient okHttpClient){
        this.mGlideUrl = glideUrl;
        mOkHttpClient = okHttpClient;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        //注意，这里使用的是mGlideUrl.toStringUrl()而不是mGlideUrl.toString()
        LogUtil.i(mGlideUrl.toStringUrl());
        Request.Builder requestBuilder = new Request.Builder().url(mGlideUrl.toStringUrl());

        for (Map.Entry<String, String> headerEntry : mGlideUrl.getHeaders().entrySet()) {
            requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
        }

        Request request = requestBuilder.build();
        if(isCancelled){
            return null;
        }
        LogUtil.i("当前线程   "+Thread.currentThread().getName());
        Response response = mOkHttpClient.newCall(request).execute();
        responseBody = response.body();
        if(!response.isSuccessful() || (null== responseBody) ){
            throw new IOException("Request failed " + response.code() + ": " + response.message());
        }
        stream = ContentLengthInputStream.obtain(responseBody.byteStream(), responseBody.contentLength());
        //记得return stream
        return stream;
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // Ignore
            }
        }

        if(null != responseBody){
            responseBody.close();
        }
    }

    @Override
    public String getId() {
        return mGlideUrl.getCacheKey();
    }

    @Override
    public void cancel() {
        // TODO: we should consider disconnecting the url connection here, but we can't do so directly because cancel is
        // often called on the main thread.
        isCancelled = true;
    }
}
