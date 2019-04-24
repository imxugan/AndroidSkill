package test.cn.example.com.androidskill.myvolley;

/**
 * Created by xugan on 2019/4/24.
 */

public interface IHttpRequest {
    //设置URL
    public abstract void setUrl(String url);
    //设置请求的数据
    public abstract void setData(byte[] data);
    //设置返回的监听对象
    public abstract void setListener(CallBackListener callBackListener);
    //请求执行的方法
    public abstract void execute();
}
