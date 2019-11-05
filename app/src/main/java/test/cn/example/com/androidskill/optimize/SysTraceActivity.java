package test.cn.example.com.androidskill.optimize;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;

public class SysTraceActivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Trace.beginSection("SysTraceActivity");
        try {
            Thread.sleep(3800);
            setContentView(R.layout.activity_systrace);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            Trace.endSection();
        }


    }
}
