package test.cn.example.com.androidskill.kotlin.chapter3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter3Demo3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter3_demo3)
        val imperialHouse = ImperialPalace()
        LogUtil.i("故宫的花的数量时候  "+imperialHouse.flowers.size)
        LogUtil.i("故宫的房间的数量 "+imperialHouse.houses.size)

        //Kotlin语言中使用"object"修饰静态类，被修饰的类可以使用类名.方法名的形式调用
        LogUtil.i(""+MusicPlay.state)

        LogUtil.e("伴生对象")
        var la = Latitude.Companion.ofDouble(3.3)
        var la2 = Latitude.ofDouble(3.1)
        var la3 = la2.ofLatitude(la);
        LogUtil.i(""+la+"       "+la2+"        "+la3)
        LogUtil.i(""+la.value+"       "+la2.value+"        "+la3.value)
        LogUtil.i(""+Latitude.Companion)


    }
}

