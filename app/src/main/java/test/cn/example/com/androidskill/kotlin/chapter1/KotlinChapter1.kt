package test.cn.example.com.androidskill.kotlin.chapter1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import test.cn.example.com.androidskill.R

class KotlinChapter1 : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter1)
        findViewById<View>(R.id.btn).setOnClickListener(this)
        findViewById<View>(R.id.btn1).setOnClickListener(this)
        findViewById<View>(R.id.btn2).setOnClickListener(this)
        findViewById<View>(R.id.btn3).setOnClickListener(this)
    }

    public override fun onClick(v: View) {
        when (v.id) {
            R.id.btn -> startActivity(Intent(this, KotlinDemo1::class.java))
            R.id.btn1 -> startActivity(Intent(this, KotlinDemo2::class.java))
            R.id.btn2 -> startActivity(Intent(this, KotlinDemo3::class.java))
            R.id.btn3 -> startActivity(Intent(this, KotlinDemo4::class.java))
        }
    }
}
