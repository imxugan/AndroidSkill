package test.cn.example.com.androidskill.kotlin.chapter6.common

import okhttp3.ResponseBody

/**
 * Created by xugan on 2019/7/29.
 */
object HttpService{
    val service by lazy{
        val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("http://www.imooc.com")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()

        retrofit.create(Service::class.java)
    }

}

interface Service{
    @retrofit2.http.GET
    fun getLogo(@retrofit2.http.Url fileUrl: String): retrofit2.Call<ResponseBody>
}