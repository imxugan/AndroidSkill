package test.cn.example.com.androidskill.kotlin.chapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter2Demo2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo2)


        var result = sum(3,23)
        var result2 = sum.invoke(3,2)
        LogUtil.i(""+result+"       "+result2)

        var sumResult = add(1,3)
        var sumResult2 = add.invoke(1,4)
        LogUtil.i(""+sumResult+"            "+sumResult2)

        printHello()

        //数组遍历
        var intArrs = intArrayOf(1,2,3,4,5,6)
        for(i in intArrs){
            LogUtil.i(""+i)
        }

        LogUtil.e("数组遍历的lambda表达式写法一")
        //也可以这样使用lambda表达式
        intArrs.forEach { element->
            LogUtil.i(""+element)
        }

        LogUtil.e("数组遍历的lambda表达式写法二")
        //数组遍历的lambda表达式写法
        intArrs.forEach (){
            LogUtil.i(""+it)
        }

        LogUtil.e("数组遍历Lambda表达时写法三")
        intArrs.forEach {
            LogUtil.i(""+it)
        }

        LogUtil.i("数组遍历用Lambda表达式写法四")
        //传入的函数和需要接受的lambda表达式完全一样，则可以使用函数的引用
        intArrs.forEach (::println)

        LogUtil.e("遍历字符数组")
        var strArrs = arrayOf<String>("1","2","3","4","5","6")
        strArrs.forEach ForEach@{
            if(it == "3"){
                return@ForEach  //只是结束当前这个for循环，而不是结束整个onCreate方法
            }
            LogUtil.i("只是结束指定的for循环的例子  "+it)
        }
        LogUtil.i("继续看下面的例子,查看函数的名称")
        LogUtil.i(""+sum) //Function2<java.lang.Integer, java.lang.Integer, java.lang.Integer>

//        var int23Arrs = intArrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23)
//       hello2 { int23Arrs[0], int23Arrs[1],int23Arrs[2], int23Arrs[3], int23Arrs[4],int23Arrs[5],
//           int23Arrs[6],int23Arrs[7], int23Arrs[8], int23Arrs[9], int23Arrs[10], int23Arrs[11], int23Arrs[12],
//           int23Arrs[13], int23Arrs[14], int23Arrs[15], int23Arrs[16], int23Arrs[17], int23Arrs[18],
//           int23Arrs[19], int23Arrs[20], int23Arrs[21], int23Arrs[22] ->
//            LogUtil.i("$int23Arrs[0],$int23Arrs[1],$int23Arrs[2],$int23Arrs[3],$int23Arrs[4],$int23Arrs[5]" +
//                    ",$int23Arrs[6],$int23Arrs[7],$int23Arrs[8],$int23Arrs[9],$int23Arrs[10],$int23Arrs[11]" +
//                    ",$int23Arrs[12],$int23Arrs[13],$int23Arrs[14],$int23Arrs[15],$int23Arrs[16],$int23Arrs[17]" +
//                    ",$int23Arrs[18],$int23Arrs[19],$int23Arrs[20],$int23Arrs[21],$int23Arrs[22],$int23Arrs[23]")
//          }

        strArrs.forEach {
            if(it=="3"){
                return    //这里return是结束整个lambda表达式，同时，也结束了整个函数，后面的任何语句都不能执行了
            }
            LogUtil.i(it)
        }
        LogUtil.i("遍历字符数组完毕")
    }

    fun hello2(action:(Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int)->Unit){
        action(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22)
    }

    var printHello = {
        LogUtil.i("hello")
    }

    var add = {a:Int,b:Int->
        LogUtil.i("$a+$b=${a+b}")
        a+b
    }

    var sum={a:Int,b:Int->a+b}
}
