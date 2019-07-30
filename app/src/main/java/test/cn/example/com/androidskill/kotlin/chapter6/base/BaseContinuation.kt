package test.cn.example.com.androidskill.kotlin.chapter6.base

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext

class BaseContinuation:Continuation<Unit>{
    override val context: CoroutineContext = EmptyCoroutineContext

    override fun resume(value: Unit) {
    }

    override fun resumeWithException(exception: Throwable) {
    }

}