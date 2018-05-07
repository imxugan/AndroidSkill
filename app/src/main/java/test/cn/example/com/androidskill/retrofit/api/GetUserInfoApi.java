package test.cn.example.com.androidskill.retrofit.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import test.cn.example.com.androidskill.base.BaseBean;
import test.cn.example.com.androidskill.retrofit.model.User;

/**
 * Created by xugan on 2018/5/4.
 */

public interface GetUserInfoApi {
    @GET("doctor/doctorinfo/index")
    Call<BaseBean<User>> getUserInfo(@QueryMap Map<String, String> getParams);
}
