package test.cn.example.com.androidskill.kotlin.chapter4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class KotlinChapter4Demo2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        LogUtil.e("按照java的处理方式，将一个集合中的元素变换后打印")
        val list = listOf<Int>(1,3,5,22,23)
        val newList = ArrayList<Int>()
        list.forEach {
            val newElement = it * 2 +3
            newList.add(newElement)
        }

        newList.forEach {
            LogUtil.i(it.toString())
        }

        LogUtil.e("采用高阶函数的形式,将一个集合中的元素变换后打印")
        val newList2 = list.map { it *2 +3 }
        newList2.forEach {
            LogUtil.i(it.toString())
        }

        LogUtil.e("采用高阶函数，将list集合中的元素转换成double类型的")
        val newList3 = list.map (Int::toDouble)
        newList3.forEach {
            LogUtil.i(it.toString())
        }
        LogUtil.e("高阶函数flatMap的使用")
        val rangList = listOf(1..10,2..5,3..6)
        val flatList = rangList.flatMap { it }
        flatList.forEach {
            LogUtil.i(it.toString())
        }
        LogUtil.e("将rangLIst集合中的元素展开，并将元素转换成string类型")
        val flatList2 = rangList.flatMap { it.map { "no"+it } }
        flatList2.forEach {
            LogUtil.i(it)
        }
        LogUtil.e("将rangList集合中的元素展开，并将元素转换成string类型的另外一种好理解的写法")
        val flatList22 = rangList.flatMap { intRange ->
            intRange.map { intElement ->
                "no$intElement"
            }
        }

        flatList22.forEach {
            LogUtil.i(it)
        }

        LogUtil.e("求rangList集合中的各个元素的和")
        val sum = rangList.flatMap{it}.reduce { acc, i -> acc+i }
        LogUtil.i("$sum")

        LogUtil.e("求集合中的元素的阶乘")
        val listResult4 = (0..6).map (::factorial)
        LogUtil.i("$listResult4")

        LogUtil.e("求集合的中元素的阶乘，并将各个阶乘求和")
        val sumResult =(0..6).map(::factorial).reduce { acc, i -> acc+i }
        LogUtil.i("$sumResult")

        LogUtil.e("求集合的中元素的阶乘，并将各个阶乘求和,并在各个阶乘求和前先加5，用fold高阶函数实现")
        val sumResult2 = (0..6).map (::factorial).fold(5){acc,i->acc+i}
        LogUtil.i("$sumResult2")

        LogUtil.e("给集合中的元素，用逗号分隔开，用fold函数实现")
        LogUtil.i("添加逗号前")
        (0..6).forEach {
            LogUtil.i(it.toString())
        }
        LogUtil.i("添加逗号后")
        val listResult = (0..6).fold(StringBuilder()){acc,i->acc.append(i).append(",")}
        LogUtil.i(listResult.toString())
        LogUtil.e("给集合中的元素添加逗号的另外一种实现方式")
        val listResult2 = (0..6).joinToString(",")
        LogUtil.i(listResult2)
        LogUtil.e("flodRight函数的使用")
        val listResult3 =(0..6).map(::factorial).foldRight(StringBuilder()){i,acc->acc.append(i).append(",")}
        LogUtil.i(listResult3.toString())

        LogUtil.e("filter函数使用，过滤阶乘结果，保留阶乘结果为奇数的结果")
        val listResult5 = (0..6).map (::factorial).filter{it%2==1}
        LogUtil.i(listResult5.toString())

        LogUtil.e("filter函数使用，过滤阶乘结果，保留元素索引为奇数的结果")
        val listResult6 = (0..6).map (::factorial).filterIndexed { index, i -> index%2==1 }
        LogUtil.i(listResult6.toString())

        LogUtil.e("take while函数使用，take while的作用是，一直取结果，直到不满足条件，停止取结果，并将取到的结果保留到集合中")
        val listResult7 = (0..6).map (::factorial).takeWhile { it->it%2==1 }
        LogUtil.i(listResult7.toString())


        LogUtil.i("let函数使用")
        findPerson("",0)?.let{ person->
            LogUtil.i(person.name+"         "+person.age)
            person.work()
        }

        findPerson("jim",19)?.let { (name,age)->
            LogUtil.i(name+"                "+age)
        }

        LogUtil.e("apply函数的使用")
       findPerson("tom",22)?.apply {
            work()
           LogUtil.i(name+"         "+age)
        }

        LogUtil.e("with函数的使用")
        val txtPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.txt";
        val buffer = BufferedReader(FileReader(txtPath))
        with(buffer){
            var line :String ?
            while(true){
                line = readLine()?:break
                LogUtil.i(line)
            }
            close()
        }

        LogUtil.e("读文本文件的另外一种简单方式")
        val buffer2 = BufferedReader(FileReader(txtPath))
        val txtResult = buffer2.readText()
        LogUtil.i(txtResult)

        LogUtil.e("use函数的使用,就不必手动的close")
        BufferedReader(FileReader(txtPath)).use {
            var line:String ?
            while(true){
                line = it.readLine()?:break
                LogUtil.i(line)
            }
        }
    }

    fun findPerson(name:String,age:Int):Person?{
        if(name==null || age==0){
            return null
        }
        return Person(name,age)
    }

    fun factorial(n:Int):Int{
        if(0==n){
            return  1
        }else{
            return (1..n).reduce { acc, i -> acc*i }
        }
    }

}

data class Person(val name:String,val age:Int){
    fun work(){
        LogUtil.i("$name is working")
    }
}
