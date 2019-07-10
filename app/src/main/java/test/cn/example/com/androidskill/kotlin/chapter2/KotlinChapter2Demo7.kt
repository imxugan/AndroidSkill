package test.cn.example.com.androidskill.kotlin.chapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.androidskill.R.drawable.test2
import test.cn.example.com.util.LogUtil
import java.lang.reflect.Executable

class KotlinChapter2Demo7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo7)
        var str = "hello world"
        try {
            LogUtil.i(str.substring(0,12))
        }catch (e:Exception){
            LogUtil.i(e.message)
        }finally {
            LogUtil.i("finally 执行了")
        }

        //注意观察两个函数catch部分的区别
        test()

        test2()

        test3()

        test4()

        LogUtil.e("finally中加了return 和未加return的区别")
        var result  = test5()

        LogUtil.i(""+result)

        var result6 = test6()
        LogUtil.i(""+result6)
    }

    private fun test6():Int{
        var x = 1
        var y = 0
        return try {
            x/y
        }catch (e:Exception){
            LogUtil.i(e.message)
            0
        }finally {
            100
        }
    }

    private fun test5():Int{
        var x = 1
        var y = 0
        return try {
            x/y
        }catch (e:Exception){
            LogUtil.i(e.message)
            0
        }finally {
            return 100
        }
    }

    private fun test4(){
        LogUtil.e("带返回结果的try{}finally{}示例四")
        var x = 1
        var y =1
        var result = try {
            x/y
        }catch (e:Exception){

        }finally {
            LogUtil.i("先执行finally 在返回x/y的结果")
            1000
        }
        LogUtil.i(""+result)
    }

    private fun test3(){
        LogUtil.e("带返回结果的try{}finally{}示例三")
        var x = 1
        var y =0
        var result = try {
            x/y
        }catch (e:Exception){

        }finally {
            LogUtil.i("先执行finally 在返回结果")
            1000
        }
        LogUtil.i(""+result)
    }

    private fun test2(){
        LogUtil.e("带返回结果的try{}catch(){}finally示例二")
        var x = 1
        var y =0
        var result = try {
            x/y
        }catch (e:Exception){
            0
            LogUtil.i(e.message)
        }finally {
            100
            LogUtil.i("finally 最后的执行顺序是...")
            200
        }
        LogUtil.i(""+result)  //最后result的结果是   kotlin.Unit
    }

    private fun test() {
        LogUtil.e("带返回结果的try{}catch(){}finally{}示例一")
        var x = 1
        var y = 0
        var result = try {
            x / y
        } catch (e: Exception) {
            0
            LogUtil.i(e.message)
            20
        } finally {
            100
            LogUtil.i("finally 最后的执行顺序是...")
            200
        }
        LogUtil.i("" + result)  //最后result的结果是  20
    }
}
