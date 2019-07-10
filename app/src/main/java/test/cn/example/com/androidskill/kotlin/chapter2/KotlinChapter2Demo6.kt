package test.cn.example.com.androidskill.kotlin.chapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter2Demo6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo6)

        LogUtil.e("最普通的遍历写法")
        var strArrs = arrayOf<String>("a","b","c","d","e")
        for(i in strArrs){
            LogUtil.i(i)
        }

        LogUtil.e("index,value 遍历写法")
        for((index,value) in strArrs.withIndex()){
            LogUtil.i("$index     $value")
        }

        LogUtil.e("indexValue 遍历写法")
        for(indexValue in strArrs.withIndex()){
            LogUtil.i("${indexValue.index}      ${indexValue.value}")
        }

        //自定义集合和Iterator
        var myIntList = MyIntList()
        myIntList.add(1)
        myIntList.add(2)
        myIntList.add(3)
        for(e in myIntList){
            LogUtil.i(""+e)
        }

        LogUtil.e("while 循环使用")

        var x = 5;
        while(x>0){
            LogUtil.i(""+x)
            x--
        }
        LogUtil.e("do... while 使用")
        var y = 5
        do{
            LogUtil.i(""+y)
            y--
        }while (y>0)

        LogUtil.e("contine使用")
        for(e in strArrs){
            if(e=="c"){
                continue
            }
            LogUtil.i(e)
        }

        LogUtil.e("break 使用")
        for(e in strArrs){
            if(e == "c"){
                break
            }
            LogUtil.i(e)
        }

    }
}

class MyIterator(val iterator: Iterator<Int>){
    operator fun next():Int{
        return iterator.next()
    }

    operator fun hasNext():Boolean{
        return iterator.hasNext()
    }
}

class MyIntList{
    private val list = ArrayList<Int>()

    fun add(int:Int){
        list.add(int)
    }

    fun remove(int:Int){
        list.remove(int)
    }

    operator fun iterator():MyIterator{
        return MyIterator(list.iterator())
    }
}
