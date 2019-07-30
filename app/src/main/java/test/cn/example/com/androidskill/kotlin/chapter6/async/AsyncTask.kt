package test.cn.example.com.androidskill.kotlin.chapter6.async

import java.util.concurrent.Executors

private val pool by lazy{
    Executors.newCachedThreadPool()
}

class AsyncTask(val bolck:()->Unit){
    fun execute() = pool.execute(bolck)
}