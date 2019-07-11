package test.cn.example.com.androidskill.kotlin.chapter3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter3Demo2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter3_demo2)
        var s = Student("小明")
        s.work()
        var teacher = Teacher("jack ma ")
        teacher.work()
        var coder = Coder("lei jun ")
        coder.work()

        //接口代理演示
        LogUtil.e("接口代理演示")
        var driver = OlderDriver()
        var writer = PPTWritger()
        var manager = SeniorManager(driver,writer)
        manager.drive()
        manager.write()

        LogUtil.i("接口方法冲突的解决办法演示")
        var test = TestConfilct(1)
        var test2 = TestConfilct(100)
        var test3 = TestConfilct(1000)
        var test4 = TestConfilct(500)
        var result1 = test.x()
        var result2 = test2.x()
        var result3 = test3.x()
        var result4 = test4.x()
        LogUtil.i(""+result1+"      "+result2+"       "+result3+"       "+result4)


    }
}

//定一个抽象类
abstract class Person(open val name:String){
    abstract fun work()
}

class Student(name:String): Person(name) {
    //覆写属性
    override val name:String
    get() = "jim grenn"
    override fun work() {
        LogUtil.i(name+"  学生学习")
    }
}

class Coder(override var name:String):Person(name){
    override fun work() {
        LogUtil.i(name+"  码代码，搬砖")
    }
}

class Teacher(override var name:String):Person(name){
    override fun work() {
        LogUtil.i(name+" 教书")
    }

}
