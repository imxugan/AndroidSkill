package test.cn.example.com.androidskill.optimize.bitmap;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;

public class MyProgressBody extends ResponseBody {

    private final String mUrl;
    private final ResponseBody mResponseBody;
    private BufferedSource bufferedSource;
    private MyProgressSource myProgressSource;

    public MyProgressBody(String url, ResponseBody responseBody){
        this.mUrl = url;
        this.mResponseBody = responseBody;
    }

    @Override
    public MediaType contentType() {
        return (null != mResponseBody)?mResponseBody.contentType():null;
    }

    @Override
    public long contentLength() {
        return (null != mResponseBody)?mResponseBody.contentLength():0;
    }

    @Override
    public BufferedSource source() {
        if(null == bufferedSource){
            myProgressSource = new MyProgressSource(mResponseBody.source(),mUrl, mResponseBody);
            bufferedSource = Okio.buffer(myProgressSource);
        }
        return bufferedSource;
    }

}
