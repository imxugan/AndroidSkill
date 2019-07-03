package test.cn.example.com.androidskill.art.chapter_four;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.DrawView;

/**
 *  Canvas上绘制大量的几何图形，点、直线、弧、圆、椭圆、文字、矩形、多边形、曲线、圆角矩形，等各种形状！
 *  的activity
 * Created by xgxg on 2017/9/6.
 */

public class CustomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        init();
    }

    private void init() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.root);
        DrawView drawView = new DrawView(this);
        drawView.setMinimumHeight(500);
        drawView.setMinimumWidth(500);
        //通知drawView重绘
        drawView.invalidate();
        linearLayout.addView(drawView);
    }
}
