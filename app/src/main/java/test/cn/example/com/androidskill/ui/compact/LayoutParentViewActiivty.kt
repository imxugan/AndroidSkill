package test.cn.example.com.androidskill.ui.compact

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    }
}
