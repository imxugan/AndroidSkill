package test.cn.example.com.androidskill.hook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.util.LogUtil;

public class PlugActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("插件onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("插件onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("插件onDestroy");
    }
}
