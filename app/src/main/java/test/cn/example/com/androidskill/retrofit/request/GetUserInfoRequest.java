package test.cn.example.com.androidskill.retrofit.request;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import test.cn.example.com.androidskill.base.BaseBean;
import test.cn.example.com.androidskill.common.RequestTool;
import test.cn.example.com.androidskill.retrofit.MyRetrofit;
import test.cn.example.com.androidskill.retrofit.api.GetUserInfoApi;
import test.cn.example.com.androidskill.retrofit.model.User;

/**
 * Created by xugan on 2018/5/4.
 */

public class GetUserInfoRequest{
    private GetUserInfoRequest(){}
    public static GetUserInfoRequest instance = new GetUserInfoRequest();
    public static GetUserInfoRequest getUserInfoRequest(){
        return instance;
    }
    public Call<BaseBean<User>> getUserInfoCall(String id){
        Retrofit retrofit = MyRetrofit.getRetrofit();
        GetUserInfoApi getUserInfoApi = retrofit.create(GetUserInfoApi.class);
        Map map = new HashMap<String,String>();
        Map<String, String> getParams = RequestTool.getCommonParams("1601");
        getParams.put("mode", "1");
        getParams.put("doctor_id", id);
        getParams.put("sign", RequestTool.getSign(getParams));
        return getUserInfoApi.getUserInfo(getParams);
    }
}