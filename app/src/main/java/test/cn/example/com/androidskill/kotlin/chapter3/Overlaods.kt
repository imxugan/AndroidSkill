package test.cn.example.com.androidskill.kotlin.chapter3

/**
 * Created by xugan on 2019/7/11.
 */
class Overlaods{
    @JvmOverloads  //这个注解的作用是，在java代码中调用时，就可以不必传参数
    fun caclute(a:Int = 0):Int{
        return a
    }

    fun caclute(a:Int,b:Int):Int{
        return a+b
    }
}