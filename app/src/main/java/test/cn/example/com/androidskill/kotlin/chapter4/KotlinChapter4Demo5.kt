package test.cn.example.com.androidskill.kotlin.chapter4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter4Demo5 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        val result = multiply8(add5(3))
        LogUtil.i("$result")
        val add5AndMultiply = add5 andThen multiply8
        val result2 = add5AndMultiply(3)
        LogUtil.i("$result2")

        val add5ComposeMultip = add5 compose multiply8
        val result3 = add5ComposeMultip(3)
        LogUtil.i("$result3")
    }

    infix fun<P1,P2,R>Function1<P2,R>.compose(function:Function1<P1,P2>):Function1<P1,R>{
        return fun(p1:P1):R{
            return this(function(p1))
        }
    }

    val add5 = {i:Int->i+5}

    val multiply8 = {i:Int->i*8}

    infix fun<P1,P2,R> Function1<P1,P2>.andThen(function:Function1<P2,R>):Function1<P1,R>{
        return fun(p:P1):R{
            return function(this(p))
        }
    }

}
