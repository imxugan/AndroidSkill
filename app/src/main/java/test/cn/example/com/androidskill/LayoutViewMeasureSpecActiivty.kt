package test.cn.example.com.androidskill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_layout_view_measure_spec_actiivty.*

class LayoutViewMeasureSpecActiivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_view_measure_spec_actiivty)
        val text = tv.text
        val task = Runnable {
            if(View.MeasureSpec.EXACTLY == ll_root.specMode_width){
                tv.setText("${text}    MeasureSpec.EXACTLY")
            }else if(View.MeasureSpec.AT_MOST == ll_root.specMode_width){
                tv.setText("${text}    MeasureSpec.AT_MOST")
            }else{
                tv.setText("${text}    MeasureSpec.UNSPECIFIED")
            }
        }
        tv.post(task)
        //由于ll_root的宽度是match_parent，自身的宽度的测量模式是EXACTLY，可以反向推到出，
        //ll_root的父控件的测量模式是EXACTLY

    }
}
