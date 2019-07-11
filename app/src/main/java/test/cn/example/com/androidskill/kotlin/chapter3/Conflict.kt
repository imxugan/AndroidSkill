package test.cn.example.com.androidskill.kotlin.chapter3

import test.cn.example.com.util.LogUtil

/**
 * Created by xugan on 2019/7/11.
 */

open class MyClassA{
    open fun x():Int = 2
}
interface InterB{
    fun x():Int = 10
}

interface InterC{
    fun x():Int = 11
}

class TestConfilct(var inta:Int) :MyClassA(),InterB,InterC{
    override fun x(): Int {
        LogUtil.i("")
        when(inta){
            in 1..10  -> return super<InterB>.x()
            in 11 .. 100 -> return super<InterC>.x()
            in 101 .. 200 -> return super<MyClassA>.x()
            else -> return inta
        }

    }

}