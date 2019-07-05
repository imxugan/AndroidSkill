package test.cn.example.com.androidskill.ui.compact.animator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/5.
 */

public class SecondActvity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //允许activity执行转场动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
