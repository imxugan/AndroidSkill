package test.cn.example.com.androidskill.ui.compact

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Allocation
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_layout_parent_view.*
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil

class LayoutParentViewActiivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = findViewById<View>(android.R.id.content)
        LogUtil.i(rootView?.toString())//android.support.v7.widget.ContentFrameLayout{8d8fea V.E...... ......I. 0,0-0,0 #1020002 android:id/content}
        LogUtil.i(rootView.parent?.toString())//android.support.v7.widget.FitWindowsLinearLayout{71bc2db V.E...... ......I. 0,0-0,0 #7f1000a9 app:id/action_bar_root}
        LogUtil.i(rootView.parent?.parent?.toString())//android.widget.FrameLayout{c92e178 V.E...... ......I. 0,0-0,0}
        LogUtil.i(rootView.parent?.parent?.parent?.toString())//android.widget.LinearLayout{60cc051 V.E...... ......I. 0,0-0,0}
        LogUtil.i(rootView.parent?.parent?.parent?.parent?.toString())//com.android.internal.policy.PhoneWindow$DecorView{5acc0b6 V.E...... R.....ID 0,0-0,0}
        LogUtil.i(rootView.parent?.parent?.parent?.parent?.parent?.toString())//null

        setContentView(R.layout.activity_layout_parent_view)

        val viewParent = root_constraintLayout?.getParent()
        LogUtil.i(viewParent?.toString())//android.support.v7.widget.ContentFrameLayout{8d8fea V.E...... ......I. 0,0-0,0 #1020002 android:id/content}
                                         // 这里的viewParent就是android.R.id.content这个ContentFrameLayout

        //准确获取view的宽高的三种方式
        tv.viewTreeObserver.addOnGlobalLayoutListener (object:ViewTreeObserver.OnGlobalLayoutListener{
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                tv.viewTreeObserver.removeOnGlobalLayoutListener(this)
                LogUtil.i("${tv.width }        ${tv.measuredWidth}")
            }

        } )

        val task = Runnable {
            LogUtil.i("${tv.width}     ${tv.measuredWidth}")
        }

        tv.post(task)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            LogUtil.i("${tv.width}     ${tv.measuredWidth}")
        }
    }
}
