package test.cn.example.com.androidskill.kotlin.chapter4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import test.cn.example.com.androidskill.R
import java.io.OutputStream

class KotlinChapter4Demo6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)

        log("MY_LOG",System.out,"123456")

        //调用科里化的写法的函数
        log2("MY_LOG")(System.out)("999999")
        ::log.curried()("MY_LOG")(System.out)("999999")
    }

    fun log(tag:String,target:OutputStream,messaeg:Any?){
        target.write("$tag  $messaeg\n".toByteArray())
    }

    //科里化的写法
    fun log2(tag:String)=fun(target:OutputStream)=fun(message:Any?)= target.write("$tag   $message\n".toByteArray() )

    //科里化的实现
    fun <P1,P2,P3,R> Function3<P1,P2,P3,R>.curried()
            =fun(p1:P1)=fun(p2:P2)=fun(p3:P3)=this.invoke(p1,p2,p3)

}

