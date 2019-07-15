package test.cn.example.com.androidskill.kotlin.chapter4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter4Demo4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        val f = makeFun(5)
        f()
        f()
        f()
        f()

        val fibo = fibonacci()
        fibo()
        fibo()
        fibo()
        fibo()
        fibo()

        val add5 = add(5)
        val result = add5(2)
        LogUtil.i("$result")

        LogUtil.e("斐波那契数，采用Iterator方式实现")
       for(i in fibonacci2()){
           if(i>100)break
           LogUtil.i("$i")
       }
    }

    fun fibonacci2():Iterable<Long>{
        var first = 0L
        var second =1L
        return Iterable {
            object : LongIterator() {
                override fun nextLong(): Long {
                    val result = second
                    second +=first
                    first = second - first
                    return result
                }

                override fun hasNext(): Boolean {
                    return true
                }

            }
        }

    }

    fun add(a:Int):(Int)->Int{
        //函数内部也能够定义类
        class Person
        return fun(y:Int):Int{
            return a+y
        }
    }

    fun fibonacci():()->Unit{
        var first = 0
        var second = 1
        return fun(){
            var result = second
            LogUtil.i("$first + $second = $result")
            second +=first
            first = second - first
        }
    }

    fun makeFun(a:Int):()->Unit{
        var count=a
        return fun (){
            count++
            LogUtil.i("$count")
        }
    }
}
