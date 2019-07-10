package test.cn.example.com.androidskill.kotlin.chapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter2Demo9 : AppCompatActivity(), View.OnClickListener {
    lateinit var input:String
    lateinit var tv_result:TextView
    lateinit var et:EditText
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_confirm -> compute()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter2_demo9)
        et = findViewById<EditText>(R.id.et)
        findViewById<View>(R.id.btn_confirm).setOnClickListener(this)
        tv_result = findViewById(R.id.tv_result)
    }

    private fun compute(){
        input = et.text.toString().trim()
        if(input != null){
            try {
                val splits = input.split(" ")
                if(splits.size<3){
                    throw IllegalArgumentException("参数个数不对,或者，你确定输入是用一个空格分隔的吗？")
                }
                val left= splits[0].toDouble()
                val op = splits[1].toString()
                val right = splits[2].toDouble()
                LogUtil.i(""+left+"             "+op+"           "+right)
                var result = Operator(op).apply(left,right)
                LogUtil.i(""+result)
                tv_result.setText(""+result)
            }catch (e:UnsupportedOperationException){
                tv_result.setText(e.message)
            }catch (e:IllegalArgumentException){
                tv_result.setText(e.message)
            }catch (e:Exception){
                LogUtil.i(e.message)
                tv_result.setText(e.message)
            }
        }
    }
}

class Operator(op:String){
    val opFun:(left:Double,right:Double)->Double
    init{
        opFun = when(op) {
            "+"->{l,r->l+r}
            "-"->{l,r->l-r}
            "*"->{l,r->l*r}
            "/"->{l,r->l/r}
            "%"->{l,r->l%r}
            else ->{
                throw UnsupportedOperationException("不支持${op}运算符")
            }
        }
    }

    fun apply(left:Double,right:Double):Double{
        return opFun(left,right)
    }
}
