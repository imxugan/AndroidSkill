package test.cn.example.com.androidskill.kotlin.chapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

//定义编译期常量
const val THOUSAND = 1000
class KotlinChapter2Demo1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo1)
        //定义给常量
        val aInt = 5
        //定义一个变量
        var bInt = 6

        LogUtil.i(""+THOUSAND+"         "+aInt+"             "+bInt)




    }
}
