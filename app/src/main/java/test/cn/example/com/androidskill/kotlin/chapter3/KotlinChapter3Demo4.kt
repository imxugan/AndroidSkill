package test.cn.example.com.androidskill.kotlin.chapter3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil
import java.lang.StringBuilder

class KotlinChapter3Demo4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        var over = Overlaods()
        var result = over.caclute()
        var result2 = over.caclute(2,3)
        LogUtil.i(""+result+"           "+result2)

        LogUtil.e("对String类进行扩展")
        var str = "abc".multiply(5)
        LogUtil.i(str)
        var result3 = "abc"* 5
        LogUtil.i(result3)

        LogUtil.i("给String类添加扩展属性a")
        LogUtil.i("".a)
        LogUtil.i("${"".b}")
    }

    //对String进行方法的扩展
    fun String.multiply(time:Int):String{
        val sb :StringBuilder = StringBuilder()
        for(i in 0 until time){
            sb.append(this)
        }
        return sb.toString()
    }

    //对操作符进行扩展
    operator fun String.times(int:Int):String{
        val sb:StringBuilder = StringBuilder()
        for (i in 0 until int){
            sb.append(this)
        }
        return sb.toString()
    }

    val String.a:String
        get() = "aaa"

    var String.b:Int
        set(value) {
        }
        get() = 100
}
