package test.cn.example.com.androidskill.ui.compact

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_scroller.*
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class ScrollerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroller)
        LogUtil.i("初始化时,当前ll_2控件的scrollX   ${ll_2.scrollX}   当前的父控件的scrollY   ${ll_2.scrollY}")
        my_btn1.setOnClickListener {
           ll.scrollTo(-200,-200)
            LogUtil.i("当前的父控件的scrollX   ${ll.scrollX}   当前的父控件的scrollY   ${ll.scrollY}")
            LogUtil.i("当前ll_2控件的scrollX   ${ll_2.scrollX}   当前的父控件的scrollY   ${ll_2.scrollY}")
        }

        my_btn2.setOnClickListener{
            ll.scrollBy(-200,-200)
            LogUtil.i("当前的父控件的scrollX   ${ll.scrollX}   当前的父控件的scrollY   ${ll.scrollY}")
            LogUtil.i("当前ll_2控件的scrollX   ${ll_2.scrollX}   当前的父控件的scrollY   ${ll_2.scrollY}")
        }

        cvg2.post{
            LogUtil.i("cvg2.width=${cvg2.width}")
        }

        btn_1.setOnClickListener{
            LogUtil.i("btn_1响应了点击事件")
        }

        btn_2.setOnClickListener {
            LogUtil.i("btn_2响应了点击事件")
        }

        btn_3.setOnClickListener {
            LogUtil.i("btn_3响应了点击事件")
        }
    }
}
