package test.cn.example.com.androidskill.kotlin.chapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter2Demo4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo4)
        var c1 = Complex(2.0,3.0)
        var c2 = Complex(4.0,2.2)
        var c3 = Complex(3.0,4.0)
        var result = c1 + c2
        LogUtil.i(""+result)
        var result2 = c1 + 5
        LogUtil.i(""+result2)
        var result3 = c3.invoke()
        LogUtil.i(""+result3)

        var result4 = c3 + "hello"
        LogUtil.i(""+result4)

        LogUtil.e("in 运算符的使用")
        // in  运算符的使用
        var intArr = intArrayOf(1,3,4,5)
        if(3 in intArr){
            LogUtil.i("3 is in intarr 中的index 是${intArr.indexOf(3)}")
        }

        LogUtil.e("中缀表达式")
        var booleResult = Book() on Desk()
        LogUtil.i(""+booleResult)
    }
}

class Book{
    //如果函数只有一个参数，并且用infix修饰，那么这个函数就是中缀函数
    infix fun on(any:Any):Boolean{  //dsl 不推荐使用，毕竟on 的运算股则，每个人实现不一样，可读性不强
        return false
    }
}

class Desk

//定义一个复数类
class Complex(var real:Double,var imaginary:Double){
    //自定义 + 操作符
    operator fun plus(other:Complex):Complex{
        return Complex(real+other.real,imaginary+other.imaginary)
    }

    //运算符 重载, 与返回值无关，意思是说，如果定义相同的方法名称和参数类型，
    // 但是返回值不同，是不可以的
    operator fun plus(other:Int):Complex{
        return Complex(real+other,imaginary)
    }

    operator fun plus(any:Any):Int{
        return real.toInt()
    }

    operator fun invoke():Double{
        return Math.hypot(real,imaginary)
    }

    //重写toString方法
    override fun toString(): String {
        return "$real+${imaginary}i"
    }
}
