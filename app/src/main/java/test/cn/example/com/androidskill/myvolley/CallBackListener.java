package test.cn.example.com.androidskill.myvolley;

import java.io.InputStream;

/**
 * 请求回调的接口类(内部使用)
 * Created by xugan on 2019/4/24.
 */

public interface CallBackListener{
    //请求成功
    public abstract void onSuccess(InputStream inputStream);
    //请求失败
    public abstract void onFailed();
}
