package test.cn.example.com.androidskill.kotlin.chapter1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2)
        var meizi = 妹子("温柔","甜美","动人")
        var bog = 帅锅("豪放","殷俊","嘹亮")
        var student = 学生("正直","清秀","清脆")

    }

    class 妹子(var 性格:String,var 长相:String,var 声音:String){
        init{
            LogUtil.i("性格：$性格,长相：$长相，声音:$声音")
        }
    }

    class 帅锅(var 性格:String,var 长相:String,var 声音:String){
        init{
            LogUtil.i("${this.javaClass.simpleName},性格:$性格，长相：$长相，声音:$声音")
        }
    }

    class 学生(性格:String,长相:String,声音:String):人(性格,长相,声音)

    open class 人(var 性格:String ,var 长相:String,var 声音:String){
        init{
            LogUtil.i("${this.javaClass.simpleName},性格:$性格，长相:$长相，声音:$声音")
        }
    }
}
