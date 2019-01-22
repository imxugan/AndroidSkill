package com.xywy.test.myretrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by xugan on 2019/1/22.
 */

public interface CommonApi {
    @GET("abc")
    Call<ResponseBody> getBaiduData();

    //Call<BaseData> 如果要使用具体的实体类，则需要要在retrofit中添加GsonConverterFactory.create()创建的Gson数据解析器
    @GET("aaa")
    //简单的get请求(URL中没有参数)
    // 这种方式类似   http://www.baidu.com/aaa
    Call<BaseData> getBaiduAAAData();

    @GET("bbb/{username}")
     //简单的get请求(URL中带有参数)
    // 这种方式类似   http://www.baidu.com/bbb/{ussername}
    //如果ussername传值为jack 则这个请求的url   http://www.baidu.com/bbb/jack
    Call<BaseData> getBaiduBBBData(@Path("username") String name);

    @GET("ccc")
    Call<BaseData> getBaiduCCCData(@Query("userId") String userId);
}
