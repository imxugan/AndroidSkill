package test.cn.example.com.androidskill.art.chapter_one;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * activity的生命周期
 */
public class LifeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        LogUtil.i("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.i("onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtil.i("onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i("onRestart");
    }
}
