package test.cn.example.com.androidskill.optimize;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;

public class TraceActivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        Debug.startMethodTracing("test");
        initView();
    }

    private void initView(){
        try {
            Thread.sleep(3800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Debug.stopMethodTracing();
    }
}
