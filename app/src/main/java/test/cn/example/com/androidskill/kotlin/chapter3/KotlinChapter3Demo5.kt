package test.cn.example.com.androidskill.kotlin.chapter3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter3Demo5 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        val delegate = Delegates()
        LogUtil.i(""+delegate.hello)
        LogUtil.i(""+delegate.hello2)
        LogUtil.i(""+delegate.hello3)
        delegate.hello3 = "aasbbddsss"
        LogUtil.i(delegate.hello3)
    }
}
