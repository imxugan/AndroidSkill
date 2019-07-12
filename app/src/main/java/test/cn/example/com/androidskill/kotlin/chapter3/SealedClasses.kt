package test.cn.example.com.androidskill.kotlin.chapter3

/**
 * Created by xugan on 2019/7/12.
 */
sealed class PlayerCmd

class Play(val url:String,val position:Int):PlayerCmd()

class Seek(val url:String,val position:Int):PlayerCmd()

class Pause:PlayerCmd()

class Resume:PlayerCmd()

class Stop:PlayerCmd()