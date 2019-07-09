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

        //无返回值的函数
        sayHi("jack ma",40)

        var sum = add(3,5)
        LogUtil.i(""+sum)

        var result = mulipe(23,2)
        LogUtil.i(""+result)

        var intResult = int2Long(34)
        LogUtil.i(""+intResult)
    }

    fun sayHi(name:String,age:Int):Unit{
        LogUtil.i(name+"            "+age)
    }

    fun add(a:Int,b:Int):Int{
        return a+b
    }

    //函数体中只是表达式，可以这样简写
    fun mulipe(a:Int,b:Int) = a*b

    //匿名函数，但是要用一个变量接收，否则，这个匿名函数无法被调用
    var int2Long = fun(a:Int):Long{
        return a.toLong()
    }
}
