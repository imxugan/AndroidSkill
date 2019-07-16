package test.cn.example.com.androidskill.kotlin.chapter4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil
import java.io.OutputStream
import java.nio.charset.Charset

class KotlinChapter4Demo6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)

        log("MY_LOG",System.out,"123456")

        //调用科里化的写法的函数
        log2("MY_LOG")(System.out)("999999")
        ::log.curried()("MY_LOG")(System.out)("999999")

        //只是传入部分参数，得到了一个新的函数吗，这个函数就称为偏函数
        val partial = log2("MY_LOG")(System.out)
        partial("偏函数的输出结果")

        val result = makeStringPartical2("helloworkd".toByteArray(charset("GBK")))
        LogUtil.i(result)

        //这里估计会输出乱码，因为，第一个参数固定是utf-8对传入的bytearray进行解码，但是传入的字符是进行gbk编码的
        val result2 = makeStringPartical1(charset("GBK"))
        LogUtil.i(result2)
    }

    val makeString = fun(byteArray:ByteArray,charset:Charset):String{
        return String(byteArray,charset)
    }



    //实现固定第二个参数的偏函数
    fun <P1,P2,R> Function2<P1,P2,R>.partical2(p2:P2)=fun(p1:P1)=this(p1,p2)
    //创建第二个参数是GBK的偏函数
    val makeStringPartical2 = makeString.partical2(charset("GBK"))

    //创建一个第一个参数默认是 test partical 默认是utf-8编码的    的偏函数
    val makeStringPartical1 = makeString.partical1("test partical 默认是utf-8编码的".toByteArray(charset("utf-8")))

    //实现固定第一个参数的偏函数
    fun <P1,P2,R> Function2<P1,P2,R>.partical1(p1:P1)=fun(p2:P2)=this(p1,p2)



    fun log(tag:String,target:OutputStream,messaeg:Any?){
        target.write("$tag  $messaeg\n".toByteArray())
    }

    //科里化的写法
    fun log2(tag:String)=fun(target:OutputStream)=fun(message:Any?)= target.write("$tag   $message\n".toByteArray() )

    //科里化的实现
    fun <P1,P2,P3,R> Function3<P1,P2,P3,R>.curried()
            =fun(p1:P1)=fun(p2:P2)=fun(p3:P3)=this.invoke(p1,p2,p3)

}

