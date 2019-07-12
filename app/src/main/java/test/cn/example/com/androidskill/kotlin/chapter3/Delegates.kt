package test.cn.example.com.androidskill.kotlin.chapter3

import test.cn.example.com.util.LogUtil
import kotlin.reflect.KProperty

/**
 * Created by xugan on 2019/7/12.
 */
class Delegates{
    val hello by lazy {
        "abc"
    }

    var hello2:String by X()    //hello2这个变量由X类进行代理初始化

    var hello3 by X()    //hello3这个变量由X类代理进行初始化
}

class X{
    private var value:String?=null
    operator fun getValue(thisRef:Any?,property:KProperty<*>):String{
        LogUtil.i("getValue:$thisRef -> ${property.name}")
        return value?:""
    }

    operator fun setValue(thisRef:Any?,property:KProperty<*>,value:String){
        LogUtil.i("setValue$thisRef -> ${property.name}=$value")
        this.value = value
    }
}