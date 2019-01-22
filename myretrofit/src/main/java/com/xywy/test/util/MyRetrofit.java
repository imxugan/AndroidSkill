package com.xywy.test.util;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xugan on 2019/1/22.
 */

public class MyRetrofit {
    private static MyRetrofit myRetrofit;
    private Retrofit retrofit ;
    private MyRetrofit(){
        retrofit = new Retrofit.Builder().
                baseUrl("http://www.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static MyRetrofit getInstance(){
        if(null == myRetrofit){
            synchronized (MyRetrofit.class){
                if(null == myRetrofit){
                    myRetrofit = new MyRetrofit();
                }
            }
        }
        return myRetrofit;
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
