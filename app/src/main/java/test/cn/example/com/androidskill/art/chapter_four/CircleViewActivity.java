package test.cn.example.com.androidskill.art.chapter_four;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.CircleView;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/1.
 */

public class CircleViewActivity extends AppCompatActivity{
    CircleView circleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        circleView = (CircleView) findViewById(R.id.circleView);
        FrameLayout frameLayout = (FrameLayout) circleView.getParent();
        LogUtil.i(""+frameLayout.getLayoutParams().width);
        LogUtil.i(""+frameLayout.getLayoutParams().height);
        LogUtil.i("FrameLayout.LayoutParams.MATCH_PARENT="+ FrameLayout.LayoutParams.MATCH_PARENT);
        LogUtil.i("FrameLayout.LayoutParams.WRAP_CONTENT="+ FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        circleView.post(new Runnable() {
            @Override
            public void run() {
                int width = circleView.getMeasuredWidth();

                LogUtil.i("tv----width------"+width);

            }
        });
    }
}
