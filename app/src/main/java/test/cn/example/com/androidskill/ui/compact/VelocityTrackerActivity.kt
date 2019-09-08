package test.cn.example.com.androidskill.ui.compact

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import kotlinx.android.synthetic.main.activity_velocity_tracker.*
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class VelocityTrackerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_velocity_tracker)
//        val room: Room? = Room()    // 先实例化一个room，并且room可以为空
//        val room: Room? = null  // 不实例化了，开始room就是空的
//
//        val room: Room = Room()   // 实例化一个room，并且room永远不能为空
//        val room = Room()   // 和上一行代码一样，是KT最常用的简写语法
        var velocityTracker :VelocityTracker ? = null
        tv_1.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                LogUtil.i(""+event!!.action)
               if(null == velocityTracker){
                   velocityTracker = VelocityTracker.obtain()
               }
                velocityTracker!!.addMovement(event)
                velocityTracker!!.computeCurrentVelocity(1000)
                when(event!!.action){
                    MotionEvent.ACTION_MOVE -> tv_1.setText("${velocityTracker!!.xVelocity}   ${velocityTracker!!.yVelocity}")
                    MotionEvent.ACTION_UP -> {
                        velocityTracker!!.recycle()
                        velocityTracker = null
                    }
                }
                return true
            }

        })
    }
}
