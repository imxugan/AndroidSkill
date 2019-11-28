package test.cn.example.com.androidskill.optimize.bitmap;

import java.io.IOException;

import okhttp3.ResponseBody;
import okio.Buffer;
import okio.ForwardingSource;
import okio.Source;
import test.cn.example.com.util.LogUtil;

public class MyProgressSource extends ForwardingSource {
    private ResponseBody mResponseBody;
    private long totalReadLength;
    private long fullLength;
    private int currentProgress;
    private MyProgressListener progressListener;

    public MyProgressSource(Source delegate) {
        super(delegate);
    }

    public MyProgressSource(Source delegate,String url,ResponseBody responseBody){
        this(delegate);
        this.mResponseBody = responseBody;
        fullLength = mResponseBody.contentLength();
        progressListener = MyProgressInterceptor.listeners.get(url);
    }

    @Override
    public long read(Buffer sink, long byteCount) throws IOException {
        long readLength = super.read(sink, byteCount);
        if(-1 == readLength){
            totalReadLength = fullLength;
        }else {
            totalReadLength +=readLength;
        }
        int progress = (int) (100f * totalReadLength/fullLength);
        LogUtil.i("当前进度：    "+currentProgress);
        if(null != progressListener && currentProgress!=progress){
            progressListener.onProgress(progress);
        }

        if(null !=progressListener && currentProgress==fullLength){
            progressListener = null;
        }

        currentProgress = progress;
        return totalReadLength;
    }

}
