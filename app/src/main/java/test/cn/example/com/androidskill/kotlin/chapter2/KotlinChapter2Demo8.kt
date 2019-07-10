package test.cn.example.com.androidskill.kotlin.chapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter2Demo8 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo8)
        LogUtil.e("变长参数的演示一")
        printHello("1","b","er","dd")
        LogUtil.e("函数中，存在可变参数和非可变参数，kotlin中由于存在具名参数，所以，可变参数不必放到参数列表最后")
        print2("a","s","d",aInt=12)  //由于可变参数不在参数列表最后，需要用具名参赛指定最后一个参数的值

        LogUtil.e("函数中，存在可变参数和非可变参数，kotlin中由于存在具名参数，所以，可变参数不必放到参数列表最后")
        print3(3.0,"1","34","ddds",aInt= 89)

        LogUtil.e("展开符的使用,目前只支持数组，还不支持集合")
        var intArrs = intArrayOf(1,3,5,8)
        print4(3.3,*intArrs,str="hello")

        LogUtil.e("默认参数,由于存在默认参数，第一个参数不需要传，所以，这里要用具名参数指定第二个参数")
        print5(args=*intArrs,str="tesst")

        LogUtil.e("默认参数，默认参数在参数列表最后,使用默认值")
        print6(*intArrs)
        LogUtil.e("默认参数，默认参数在参数列表最后，不使用默认值")
        print6(*intArrs,str="www.baidu.com")

    }

    private fun print6(vararg args:Int,str:String="defaultUrl"){
        for(i in args){
            LogUtil.i(""+i)
        }
        LogUtil.i(str)
    }

    private fun print5(aDouble:Double =3.14,vararg args:Int,str:String){
        LogUtil.i(""+aDouble)
        for (i in args){
            LogUtil.i(""+i)
        }
        LogUtil.i(str)
    }

    private fun print4(aDouble: Double,vararg args:Int,str:String){
        LogUtil.i(""+aDouble)
        for(i in args){
            LogUtil.i(""+i)
        }
        LogUtil.i(str)
    }

    private fun print3(aDouble:Double,vararg args:String,aInt:Int){
        LogUtil.i(""+aDouble)
        for(i in args){
            LogUtil.i(i)
        }
        LogUtil.i(""+aInt)
    }

    private fun print2(vararg args:String,aInt:Int){
        for(i in args){
            LogUtil.i(i)
        }
        LogUtil.i(""+aInt)
    }

    private fun printHello(vararg args:String){
        for(i in args){
            LogUtil.i(i)
        }
    }
}
