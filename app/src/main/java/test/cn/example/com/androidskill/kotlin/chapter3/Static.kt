package test.cn.example.com.androidskill.kotlin.chapter3

/**
 * Created by xugan on 2019/7/11.
 */
//创建一个构造函数私有化，并且有一个成员变量value的类
class Latitude private constructor(var value:Double){
    companion object {
        @JvmStatic  //注解的作用是  java代码调用时，直接Latitude.ofDouble(4.0)这样调用
        fun  ofDouble(double:Double):Latitude{
            return Latitude(double)
        }
    }

    fun ofLatitude(latitude:Latitude):Latitude{
        return ofDouble(latitude.value)
    }

    @JvmField   //注解的作用是  java代码调用时，直接Latitude.TAG  这样调用
    val TAG:String = "latitudeTag"
}