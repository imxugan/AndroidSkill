package test.cn.example.com.androidskill.kotlin.chapter4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class KotlinChapter4Demo3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_chapter4_demo3)

        val MAX_NODE_COUNT = 100000
        val head = ListNode(0)
        var p = head
        for(i in 1 ..MAX_NODE_COUNT){
            p.next = ListNode(i)
            p = p.next!!
        }
        val node = findNode(head,MAX_NODE_COUNT-2)?:null
        LogUtil.i("${node?.value}")
    }

    //这个方法就是尾递归,k可以在方法前面加tailrec关键字，让系统给这个方法进行尾递归优化
   tailrec fun findNode(head:ListNode?,value:Int):ListNode?{
        head?: return null
        if(value == head.value){
            return head
        }else{
            return findNode(head.next,value)
        }
    }

    //这个求阶乘的方法，就不是尾递归，因为，在递归调用方法时，还做了乘n的操作，所以，不是尾递归
    fun factorial(n:Int):Int{
        return n*factorial(n-1)
    }

    //这个方法也不是尾递归调用，因为，最后虽然调用了findTreeNode(rootNode.right,value)。但是，这个
    //调用是在调用了findTreeNode(rootNode.left,value)，也就是说，调用自己后，在次调用了自己，不符合
    //尾递归的调用自己后，不在有其他操作的要求
    fun findTreeNode(rootNode:TreeNode?,value:Int):TreeNode?{
        rootNode?: return null
        if(value == rootNode.value) return rootNode
        return findTreeNode(rootNode.left,value)?:findTreeNode(rootNode.right,value)
    }
}

data class ListNode(val value:Int,var next:ListNode?=null)

data class TreeNode(val value:Int){
    val left:TreeNode ?=null
    val right:TreeNode ?=null
}
