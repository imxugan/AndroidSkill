package test.cn.example.com.androidskill.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.cn.example.com.androidskill.BuildConfig;

/**
 * Created by xugan on 2018/5/4.
 */

public class MyRetrofit{
    private Retrofit retrofit = null;

    private MyRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                        .addCallAdapterFactory(RxJavaCallAdapter.create())
                .build();
    }
    private static MyRetrofit myRetrofit = new MyRetrofit();
    public static Retrofit getRetrofit(){
        return myRetrofit.retrofit;
    }

}
