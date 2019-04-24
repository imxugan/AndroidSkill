package test.cn.example.com.androidskill.myvolley;

/**
 * 面向应用层的回调请求接口
 * Created by xugan on 2019/4/24.
 */

public interface IJsonDataListener<T> {
    //请求成功
    public abstract void onSuccess(T t);
    //请求失败
    public abstract void onFailed();
}
