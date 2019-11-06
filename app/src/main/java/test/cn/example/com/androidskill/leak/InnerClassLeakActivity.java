package test.cn.example.com.androidskill.leak;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class InnerClassLeakActivity extends AppCompatActivity {
    private static Leak sLeak;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_common);
        sLeak = new Leak();
        sLeak.test();

    }

    private class Leak {
        private void test(){
            LogUtil.i("test method execute");
        }
    }
}
