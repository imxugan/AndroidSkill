package test.cn.example.com.androidskill.kotlin.chapter6.common

import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

val now = {
    dateFormat.format(Date(System.currentTimeMillis()))
}

fun log(msg:String) = println("${now()} [${Thread.currentThread().name}]  $msg")