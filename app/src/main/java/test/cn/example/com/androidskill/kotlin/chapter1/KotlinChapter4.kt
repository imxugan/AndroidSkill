package test.cn.example.com.androidskill.kotlin.chapter1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil
class KotlinChapter4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter4)
        var rang = 0..10
        for(i in rang){
            LogUtil.i(""+i)
        }
        var rang2:IntRange = 0 until 10
        for(i in rang2){
            LogUtil.i(""+i)
        }

        LogUtil.i("rang.isEmpty()   "+rang.isEmpty())
        LogUtil.i(""+(5 in rang))
        LogUtil.i(""+rang.contains(50))

        //数组的使用
        var intOfArray:IntArray  = intArrayOf(1,3,5,7)

        var charOfArray:CharArray = charArrayOf('h','e','r','o')

        var stringArray:Array<String> = arrayOf("aaa","bbb","ccc")

        LogUtil.i("intOfArray.size  "+intOfArray.size)
        for(i in stringArray){
            LogUtil.i(i)
        }

        LogUtil.i(""+charOfArray[1])

        charOfArray[1] = 'a'
        LogUtil.i(""+charOfArray[1])

        LogUtil.i(charOfArray.joinToString(""))

        LogUtil.i(""+intOfArray.slice(1..2))

    }
}
