package test.cn.example.com.androidskill.ui.compact

import android.graphics.drawable.TransitionDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_drawable.*
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class DrawableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable)
        var level = 100
        my_iv_1.setImageLevel(level)
        mybtn_1.setOnClickListener {
            if(level == 100){
                level = 45
            }else{
                level = 100
            }
            LogUtil.i(""+level)
            my_iv_1.setImageLevel(level)
        }

        val background : TransitionDrawable = my_tv_2.background as TransitionDrawable
        background.startTransition(1000)

        val drawable = my_tv_4.background
        drawable.setLevel(1)

        val drawable_clip = my_tv_5.background
        drawable_clip.setLevel(8000)
    }
}
