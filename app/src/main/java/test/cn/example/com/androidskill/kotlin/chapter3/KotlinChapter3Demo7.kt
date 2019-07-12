package test.cn.example.com.androidskill.kotlin.chapter3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter3Demo7 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter_common)
        LogUtil.i(""+LogLevel.VERBOSE+"     name=${LogLevel.VERBOSE.name}    oridinal=${LogLevel.VERBOSE.ordinal}   id=${LogLevel.VERBOSE.id}")
        LogUtil.i(""+LogLevel.DEBUG+"     name=${LogLevel.DEBUG.name}    oridinal=${LogLevel.DEBUG.ordinal}   id=${LogLevel.DEBUG.id}")
        LogUtil.i(""+LogLevel.INFO+"     name=${LogLevel.INFO.name}    oridinal=${LogLevel.INFO.ordinal}   id=${LogLevel.INFO.id}")
        LogUtil.i(""+LogLevel.WARN+"     name=${LogLevel.WARN.name}    oridinal=${LogLevel.WARN.ordinal}   id=${LogLevel.WARN.id}")
        LogUtil.i(""+LogLevel.ERROR+"     name=${LogLevel.ERROR.name}    oridinal=${LogLevel.ERROR.ordinal}   id=${LogLevel.ERROR.id}")
        LogUtil.i(""+LogLevel.ASSET+"     name=${LogLevel.ASSET.name}    oridinal=${LogLevel.ASSET.ordinal}   id=${LogLevel.ASSET.id}")
        val tag = LogLevel.DEBUG
        LogUtil.i("$tag")
        val values = LogLevel.values()
        //遍历枚举
        LogUtil.e("遍历枚举")
        for(i in values){
            LogUtil.i("$i")
        }
        //通过枚举的名称获取枚举实例
        LogUtil.e("通过枚举的名称，获取枚举的实例")
        val error = LogLevel.valueOf("ERROR")
        LogUtil.i("$error")

    }
}

enum class LogLevel(val id:Int){
    VERBOSE(10),DEBUG(11),INFO(12),WARN(13),ERROR(14),ASSET(50);
    fun getLogLevelId():Int{
        return id
    }

    override fun toString(): String {
        return "$ordinal      $name      $id"
    }
}

//类似枚举的功能
class Loglevel2{
    companion object {
        val VERBOSE=Loglevel2()
        val DEBUG = Loglevel2()
        val INFO = Loglevel2()
        val WARN = Loglevel2()
        val ERROR = Loglevel2()
        val ASSET = Loglevel2()
    }
}
