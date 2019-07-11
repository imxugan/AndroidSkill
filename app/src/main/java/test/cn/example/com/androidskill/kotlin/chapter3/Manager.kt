package test.cn.example.com.androidskill.kotlin.chapter3

import test.cn.example.com.util.LogUtil

/**
 * Created by xugan on 2019/7/11.
 */
class Manager :Drive,Write{
    override fun write() {
        LogUtil.i("写毛线")
    }

    override fun drive() {
        LogUtil.i("开车")
    }
}

class SeniorManager(val driver:Drive,val writer:Write):Drive by driver,Write by writer   // by driver  接口代理

class OlderDriver:Drive{
    override fun drive() {
        LogUtil.i("专业老司机")
    }
}

class PPTWritger:Write{
    override fun write() {
        LogUtil.i("专业十年写ppt")
    }

}

interface Drive{
    fun drive()
}

interface Write{
    fun write()
}