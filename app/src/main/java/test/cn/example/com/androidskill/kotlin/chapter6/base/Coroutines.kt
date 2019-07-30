package test.cn.example.com.androidskill.kotlin.chapter6.base

import test.cn.example.com.androidskill.kotlin.chapter6.async.AsyncTask
import test.cn.example.com.androidskill.kotlin.chapter6.common.HttpException
import test.cn.example.com.androidskill.kotlin.chapter6.common.HttpService
import test.cn.example.com.util.LogUtil
import java.lang.Exception
import kotlin.coroutines.experimental.startCoroutine
import kotlin.coroutines.experimental.suspendCoroutine

fun myStartCoroutine(block:suspend()->Unit){
    block.startCoroutine(BaseContinuation())
}

suspend fun startDownLoadImage(url:String) = suspendCoroutine<ByteArray> {
    continuation ->
    LogUtil.i("异步任务开始")
    AsyncTask{
        try {
            LogUtil.i("耗时操作，下载图片")
            val responseBody = HttpService.service.getLogo(url).execute()
            if(responseBody.isSuccessful){
                responseBody.body()?.byteStream()?.readBytes()?.let(continuation::resume)
            }else{
                continuation.resumeWithException(HttpException(responseBody.code()))
            }
        } catch(e: Exception) {
            LogUtil.e(e.message)
            continuation.resumeWithException(e)
        }
    }.execute()

}