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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter4)
        findViewById<View>(R.id.btn).setOnClickListener(this)
    }
}
