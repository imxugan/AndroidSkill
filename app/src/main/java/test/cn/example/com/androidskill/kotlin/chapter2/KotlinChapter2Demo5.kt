package test.cn.example.com.androidskill.kotlin.chapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

const val DEBUG= 0
const val RELEASE = 1
class KotlinChapter2Demo5 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo5)
        LogUtil.i("if表达式")
        var parms = (Math.random()*10).toInt()
        var mode = if(parms>=5){
            DEBUG
        }else{
            RELEASE
        }
        LogUtil.i(""+parms+"        "+mode)

        var mode2 = when{
            parms>5 -> DEBUG
                else -> RELEASE
        }
        LogUtil.i(""+parms+"       "+mode2)

        var state = State.IDLE
        when(state){
            State.IDLE->LogUtil.i("idle 空闲状态")
            State.BUFFERING->LogUtil.i("缓冲状态")
            State.PLAYING->LogUtil.i("播放状态")
            State.PAUSED->LogUtil.i("暂停状态")
            else->{
                LogUtil.i("非法状态")
            }
        }

        var x = 5
        when(x){
            is Int -> LogUtil.i("$x is int 类型")
            in 1..10 -> LogUtil.i("$x is in 1..10")
            !in 1..10 -> LogUtil.i("$x is not in 1..10")
        }
    }

    enum class State{
        IDLE,BUFFERING,PLAYING,PAUSED
    }
}
