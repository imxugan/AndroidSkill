package com.xywy.test.myretrofit;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    //get请求，参数在url问号之后
    //这种请求类似   http://www.baidu.com/ccc?userId={userId}
    Call<BaseData> getBaiduCCCData(@Query("userId") String userId);

    @GET("ddd")
    // get 请求，参数在url问号之后，并且是多个参数
    // 这种情况类似 http://www.baidu.com/ddd?userId={userId}&age={age}
    Call<BaseData> getBaiduDDDData(@QueryMap Map<String,String> map);

    @POST("eee")
    @FormUrlEncoded
    //post 请求，参数字段名称是 reason 在请求体body中
    Call<BaseData> postBaiduEEEData(@Field("reason") String reason);

    @POST("ggg")
    @FormUrlEncoded
    Call<BaseData> psotBaiduGGGData(@FieldMap Map<String,String> map);

    @POST("fff")
    //post 请求，post的body部分是一个json串
    Call<BaseData> postBaiduFFFData(@Body RequestBody requestBody);
}
