package test.cn.example.com.androidskill.kotlin.chapter6

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_kotlin_chapter6_demo1.*
import test.cn.example.com.androidskill.R
import test.cn.example.com.androidskill.kotlin.chapter6.base.myStartCoroutine
import test.cn.example.com.androidskill.kotlin.chapter6.base.startDownLoadImage
import test.cn.example.com.androidskill.kotlin.chapter6.common.log
import test.cn.example.com.util.LogUtil
import java.nio.ByteBuffer

class KotlinChapter6Demo1 : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn1 -> downImage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter6_demo1)
        findViewById<View>(R.id.btn1).setOnClickListener(this)
        btn.text = "不用findviewByid?"
    }

    fun downImage(){
        LogUtil.i("协程之前")
        myStartCoroutine{
            LogUtil.i("协程开始")
            val imageData:ByteArray = startDownLoadImage("http://www.imooc.com/static/img/index/logo.png?t=1.1")
            LogUtil.i("拿到图片   imageData="+imageData)
            val array = ByteBuffer.wrap(imageData).array()
            val decodeByteArray = BitmapFactory.decodeByteArray(array, 0, array.size)
            runOnUiThread {
                iv.setImageBitmap(decodeByteArray)
            }

        }
        LogUtil.i("协程之后")
    }
}
