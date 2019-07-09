package test.cn.example.com.androidskill.kotlin.chapter2

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter2Demo3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo3)
        var p = Person("lily",10)
        LogUtil.i(p.name+"      "+p.age)
        p.sing("沙漠骆驼")
        LogUtil.i("init a    "+p.a)
        LogUtil.i(""+p.d)
        p.c = X()
        LogUtil.i(""+p.c)
        p.b = "手动赋值进行初始化"
        LogUtil.i(p.b)
    }
}

class Person(var name:String,var age:Int){
   fun sing(music:String){
       LogUtil.i("唱的歌曲是:"+music)
   }

    var a = 0
    get() {
        return field
    }
    set(value) {
        field = value
    }

    lateinit var b:String  //lateinit表示延迟初始化,作用于var修饰的变量
    lateinit var c:X
    val d:X by lazy {     //by lazy是给val 修饰的变量进行延迟初始化
        X()
    }
}

class X
