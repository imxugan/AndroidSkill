package test.cn.example.com.androidskill.kotlin.chapter4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter4Demo1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        val array = arrayListOf<String>("abc","nba")
        array.forEach (::println)
        array.filter(String::isNotEmpty)
        val pdf = PdfPrint()
        array.filter(pdf::printPdf)
    }


}

class PdfPrint{
    fun printPdf(name:String):Boolean{
        LogUtil.i(name)
        return true
    }
}
