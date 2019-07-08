package test.cn.example.com.androidskill.kotlin.chapter1

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter1)
        var aBoolean = true
        LogUtil.i(""+aBoolean)
        var aInt = 6;
        LogUtil.i(""+aInt);
        var anthorInt= 0xff
        var maxInt:Int = Int.MAX_VALUE
        var minInt:Int = Int.MIN_VALUE
        LogUtil.i(""+anthorInt+"        "+maxInt+"          "+minInt)
        var aLong = 12222222290988888
        var anthorLong = 123
        var maxLong = Long.MAX_VALUE
        var minLong = Long.MIN_VALUE
        LogUtil.i(""+aLong+"            "+anthorLong+"      "+maxLong+"       "+minLong)
        var aFloat = 1.0f
        var anthorFloat = 1e3
        var maxFloat = Float.MAX_VALUE
        var minFloat = Float.MIN_VALUE //只是最小的正浮点数
        LogUtil.i(""+aFloat+"           "+anthorFloat)
        LogUtil.i(""+maxFloat+"           "+minFloat+"     "+(minFloat>0))
        LogUtil.i("最小的浮点数  "+(-Float.MAX_VALUE))
        var aDouble = 12.9;
        var maxDouble = Double.MAX_VALUE
        var minDouble = Double.MIN_VALUE
        LogUtil.i(""+aDouble+"          "+maxDouble+"               "+minDouble+"     minDouble>0 ? "+(minDouble>0))
        LogUtil.i("最小的double型数   "+(-Double.MAX_VALUE))

        var aShort = 1288
        var maxShort = Short.MAX_VALUE
        var minShort = Short.MIN_VALUE
        LogUtil.i(""+aShort+"           "+maxShort+"       "+minShort)

//        var aByte:Byte= 134  //报错，因为134超过了byte表示的范围
        var aByte = 134      //不报错
        var maxByte = Byte.MAX_VALUE
        var minByte = Byte.MIN_VALUE
        LogUtil.i(""+aByte+"            "+maxByte+"         "+minByte)
    }
}
