package test.cn.example.com.androidskill.kotlin.chapter4

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import test.cn.example.com.androidskill.R

class KotlinChapter4 : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn ->startActivity(Intent(this,KotlinChapter4Demo1::class.java))
            R.id.btn1 ->startActivity(Intent(this,KotlinChapter4Demo2::class.java))
            R.id.btn2 ->startActivity(Intent(this,KotlinChapter4Demo3::class.java))
            R.id.btn3 ->startActivity(Intent(this,KotlinChapter4Demo4::class.java))
            R.id.btn4 ->startActivity(Intent(this,KotlinChapter4Demo5::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter4)
        findViewById<View>(R.id.btn).setOnClickListener(this)
        findViewById<View>(R.id.btn1).setOnClickListener(this)
        findViewById<View>(R.id.btn2).setOnClickListener(this)
        findViewById<View>(R.id.btn3).setOnClickListener(this)
        findViewById<View>(R.id.btn4).setOnClickListener(this)
    }
}
