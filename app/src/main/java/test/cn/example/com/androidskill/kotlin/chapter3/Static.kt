package test.cn.example.com.androidskill.kotlin.chapter3

/**
 * Created by xugan on 2019/7/11.
 */
//创建一个构造函数私有化，并且有一个成员变量value的类
class Latitude private constructor(var value:Double){
    companion object {
        fun  ofDouble(double:Double):Latitude{
            return Latitude(double)
        }
    }

    fun ofLatitude(latitude:Latitude):Latitude{
        return ofDouble(latitude.value)
    }
}