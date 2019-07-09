package test.cn.example.com.androidskill.kotlin.chapter2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import test.cn.example.com.androidskill.R

class KotlinChapter2 : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn -> startActivity(Intent(this, KotlinChapter2Demo1::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2)
        findViewById<View>(R.id.btn).setOnClickListener(this)
    }
}
