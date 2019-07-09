package test.cn.example.com.androidskill.kotlin.chapter1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinDemo3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter3)
        showNull()

        typeCast()

    }

    fun typeCast(){
       var p:Parent = Child()
        if(p is Child){
            LogUtil.i(p.playGame())
        }

        var str:String ? = "abc"
        if(str is String){
            LogUtil.i(""+str.length)
        }

        var parent:Parent = Parent()
        var child:Child ? = parent as? Child //将parent转成Child类型，如果转换失败了，不要抛出异常，返回null
        LogUtil.i(""+child)
    }

    fun showNull(){
        var name:String = getName()
        LogUtil.i("${name} lenght is "+name.length)

        var address = getAddress()
        if(null == address){
            LogUtil.i("${address}")
        }else{
            LogUtil.i("${address} lenght is  "+address.length)
        }
        //上面两句可以整合成下面一句
        LogUtil.i(""+address?.length)  //不为空，直接返回address的长度

        var str:String? = "hell world"  //这句代码表示，str变量有可能为null
        LogUtil.i(""+str!!.length)  //!!表示，已经确定了str肯定不为null

        var address2 = getAddress()?:return //这句的意思是，如果getAddress()返回null，则直接return,否则，将getAddress方法的返回值赋值给我address2变量
        LogUtil.i(""+address2.length)
    }

    fun getName():String{
        return "Trump"
    }

    fun getAddress():String?{
        return null
    }
}
