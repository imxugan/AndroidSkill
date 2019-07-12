package test.cn.example.com.androidskill.kotlin.chapter3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter3Demo5 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        val delegate = Delegates()
        LogUtil.i(""+delegate.hello)
        LogUtil.i(""+delegate.hello2)
        LogUtil.i(""+delegate.hello3)
        delegate.hello3 = "aasbbddsss"
        LogUtil.i(delegate.hello3)

        //data class 修饰的类
        LogUtil.i("data class 修饰的类的使用")
        var country = Country(1,"China")
        LogUtil.i("$country")
        LogUtil.i("${country.id}     ${country.name}")
        LogUtil.i("coutnry.component1 =  ${country.component1()}      country.component2=${country.component2()}")
        val (id1,name1) = country
        LogUtil.i("$id1     $name1")

        //自定义类中的component
        var (a,b,c,d,e) = ComponentX()
        LogUtil.i("$a $b $c $d $e")

    }
}

@Poko
data class Country(val id:Int,val name:String)

class ComponentX{
    operator fun component1():String{
        return "您好，我是ted "
    }

    operator fun component2():String{
        return "who "
    }

    operator fun component3():String{
        return "is "
    }

    operator fun component4():String{
        return "ted"
    }

    operator fun component5():Int{
        return 1000
    }
}
