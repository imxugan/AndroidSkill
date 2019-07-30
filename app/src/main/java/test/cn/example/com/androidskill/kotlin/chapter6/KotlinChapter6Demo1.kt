package test.cn.example.com.androidskill.kotlin.chapter6

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_kotlin_chapter6_demo1.*
import test.cn.example.com.androidskill.R

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
        findViewById<ImageView>(R.id.iv)
    }

    fun downImage(){

    }
}
