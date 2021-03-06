package test.cn.example.com.androidskill.kotlin.chapter3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import test.cn.example.com.androidskill.R

class KotlinChapter3 : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn -> startActivity(Intent(this,KotlinChapter3Demo1::class.java))
            R.id.btn1 ->startActivity(Intent(this,KotlinChapter3Demo2::class.java))
            R.id.btn2 ->startActivity(Intent(this,KotlinChapter3Demo3::class.java))
            R.id.btn3 ->startActivity(Intent(this,KotlinChapter3Demo4::class.java))
            R.id.btn4 ->startActivity(Intent(this,KotlinChapter3Demo5::class.java))
            R.id.btn5 ->startActivity(Intent(this,KotlinChapter3Demo6::class.java))
            R.id.btn6 ->startActivity(Intent(this,KotlinChapter3Demo7::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chpater3)
        findViewById<View>(R.id.btn).setOnClickListener(this)
        findViewById<View>(R.id.btn1).setOnClickListener(this)
        findViewById<View>(R.id.btn2).setOnClickListener(this)
        findViewById<View>(R.id.btn3).setOnClickListener(this)
        findViewById<View>(R.id.btn4).setOnClickListener(this)
        findViewById<View>(R.id.btn5).setOnClickListener(this)
        findViewById<View>(R.id.btn6).setOnClickListener(this)
    }
}
