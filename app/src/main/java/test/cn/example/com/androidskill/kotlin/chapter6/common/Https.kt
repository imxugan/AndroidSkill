package test.cn.example.com.androidskill.kotlin.chapter6.common

/**
 * Created by xugan on 2019/7/29.
 */
object HttpError{
    const val HTTP_ERROR_NO_DATA = 999
    const val HTTP_ERROR_UNKNOWN = 998
}

data class HttpException(val code:Int):Exception()