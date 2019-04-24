package test.cn.example.com.androidskill.myvolley;

/**
 * Created by xugan on 2019/4/24.
 */

public class MyVolley {
    /**
     * 请求方法
     * @param url   请求url
     * @param requestData  请求参数
     * @param responseClazz      接口返回的数据类型
     * @param iJsonDataListener     监听回调的接口
     * @param <T>
     * @param <M>
     */
    public static<T,M> void sendRequest(String url,T requestData,Class<M> responseClazz,IJsonDataListener iJsonDataListener){
        //创建一个网络请求对象(把所有传递进来的参数进行封装)
        IHttpRequest iHttpRequest = new JsonHttpRequest();
        //创建一个请求回调的接口类
        CallBackListener callBackListener = new JsonCallBackListener<>(responseClazz, iJsonDataListener);
        //创建一个网络请求的线程
        HttpTask httpTask = new HttpTask(iHttpRequest,url,requestData,callBackListener);
        //将线程加入到队列
        ThreadManager.getInstance().addTask(httpTask);
    }
}
