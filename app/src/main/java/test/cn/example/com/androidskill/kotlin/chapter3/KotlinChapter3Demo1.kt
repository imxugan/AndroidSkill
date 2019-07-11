package test.cn.example.com.androidskill.kotlin.chapter3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter3Demo1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter3_demo1)

        val computer = Computer()
        val usbInputService = MacMouse()
        computer.addInputDevice(usbInputService)

        val e = E(0)
        LogUtil.i(""+(e is A))
        LogUtil.i(""+(e is B))
        LogUtil.i(""+(e is F))

        val a :A = E(1)
        val b : B= E(2)
        var f :F = E(3)
        LogUtil.i(""+a.i+"      "+b.a+"       "+f)
    }


}

abstract class A{
    var i = 0

    fun helloA(){
        LogUtil.i("抽象类的方法")
    }

    open fun go(){
        LogUtil.i("带open修饰的方法")
    }
}

interface B{
    // var j = 1  //接口中是不允许对属性进行初始化的
    var a:Int
    fun helloB(){              //接口只用的默认实现方法
        LogUtil.i(""+a)
    }

    fun work()         //接口中的抽象方法，没有方法体
}

interface F

class C(override var a: Int) :B{
    override fun work() {
        LogUtil.i("实现了接口B中的work方法")
    }
}

class D: A() {  //由于是继承抽象类A,所以要调用A的构造方法
    override fun go() {    //能够覆写go方法，是因为go方法是open修饰的
        super.go()
    }
}

class E(override var a: Int) : A(),B,F{
    override fun work() {
        LogUtil.i("")
    }
}