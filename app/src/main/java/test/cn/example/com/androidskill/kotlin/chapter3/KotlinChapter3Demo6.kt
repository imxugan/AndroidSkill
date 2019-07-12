package test.cn.example.com.androidskill.kotlin.chapter3

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter3Demo6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        LogUtil.i("静态内部类创建对象的方式")
        val staticInner = Outter.StaticInner()
        staticInner.show()
        LogUtil.i("非静态内部类创建对象的方式")
        val myInner = Outter().MyInner()
        myInner.show()

        //匿名内部类
        val myView = MyView()
        //下面这个 object 部分就是匿名内部类，可以看到匿名内部类可以继承一个类，并实现接口
        myView.myOnClickListener = object :Outter(),View.OnClickListener, mOnClickListener {
            override fun onClick(v: View?) {
                LogUtil.i("")
            }

        }


    }
}

interface mOnClickListener

class MyView{
    var myOnClickListener:mOnClickListener ?= null
}

open class Outter{
    val a:Int = 0
    //默认StaticInner这个类是静态内部类
    class StaticInner{
        val a:Int = 1
        fun show(){
            //静态内部类是无法访问外部类的非静态成员
            LogUtil.i("注意两个a的值   ${a}         ${this@StaticInner.a}")
        }
    }

    //MyInner类是非静态内部类,因为前面加了inner 关键字
    inner class MyInner{
        val a:Int = 2
        fun show(){
            LogUtil.i("注意两个a的值  ${this@Outter.a}        ${this@MyInner.a}")
        }
    }
}
