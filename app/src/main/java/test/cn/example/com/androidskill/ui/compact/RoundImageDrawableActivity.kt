package test.cn.example.com.androidskill.ui.compact

import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_drawable.my_iv_1
import kotlinx.android.synthetic.main.activity_round_image_drawable.*
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class RoundImageDrawableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_image_drawable)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.dog_bg)
        my_iv_1.setImageDrawable(RoundImageDrawable(bitmap))
        //图片换成p2后，确显示的不是一个原型图片，通过调试发现，p2图片的四周是有透明的间隔的，导致显示的时候不是个亿圆形
        var bitmap5 = BitmapFactory.decodeResource(resources, R.drawable.dog_bg)
        LogUtil.i(bitmap5.width.toString() + "       " + bitmap5.height)
        my_iv_2.setImageDrawable(CircleImageDrawable(bitmap5))
        my_iv_2.post {
            LogUtil.i(my_iv_2.width.toString()+"        "+my_iv_2.height.toString())
            LogUtil.i(my_iv_2.scaleType.toString())
        }
    }
}
