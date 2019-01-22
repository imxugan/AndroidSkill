package com.xywy.test.myretrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by xugan on 2019/1/22.
 */

public interface CommonApi {
    @GET("abc")
    Call<ResponseBody> getBaiduData();

    //Call<BaseData> 如果要使用具体的实体类，则需要要在retrofit中添加GsonConverterFactory.create()创建的Gson数据解析器
    @GET("aaa")
    Call<BaseData> getBaiduAAAData();
}
