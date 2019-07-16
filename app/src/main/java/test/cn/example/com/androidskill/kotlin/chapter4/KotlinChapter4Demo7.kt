package test.cn.example.com.androidskill.kotlin.chapter4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil
import java.io.File

class KotlinChapter4Demo7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        val map = HashMap<Char,Int>()
        val path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/1.txt"
        File(path).readText().toCharArray().filterNot(Char::isWhitespace).forEach {
            val count = map[it]
            if(null == count) map[it] = 1
            else map[it] = count +1
        }

        map.forEach{
            LogUtil.i("${it.key}    ${it.value}" )
        }

        LogUtil.e("kotlin风格的写法，一步到位的 写法")
        File(path).readText().filterNot(Char::isWhitespace).groupBy { it }.map {
            LogUtil.i("$it")
            it.key to it.value.size
        }.forEach {
            LogUtil.i("${it.first}   ${it.second}")
        }
    }
}
