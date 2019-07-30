package test.cn.example.com.androidskill.kotlin.chapter6.common

import okhttp3.ResponseBody

/**
 * Created by xugan on 2019/7/29.
 */
object HttpService{
    val service by lazy{
        val retrofit = retrofit2.Retrofit.Builder().baseUrl("http://www.baidu.com")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
    }
}

interface Service{
    @retrofit2.http.GET
    fun getLogo(@retrofit2.http.url finlUrl:String):retrofit2.Call<ResponseBody>
}