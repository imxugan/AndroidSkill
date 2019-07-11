package test.cn.example.com.androidskill.kotlin.chapter3

/**
 * Created by xugan on 2019/7/11.
 */
class Driver
interface OnMountedListener{
    abstract fun onMounted(driver:Driver)

    abstract fun onUnMounted(driver:Driver)
}

open class MediaPlay

object MusicPlay:MediaPlay(),OnMountedListener{
    override fun onUnMounted(driver: Driver) {

    }

    override fun onMounted(driver: Driver) {

    }

    val state:Int = 0

    fun play(url:String){

    }

    fun stop(){}

}