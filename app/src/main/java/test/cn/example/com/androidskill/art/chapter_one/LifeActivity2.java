package test.cn.example.com.androidskill.art.chapter_one;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * activity的生命周期
 */
public class LifeActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        TextView tv = findViewById(R.id.tv);
        tv.setText("LifeActivity2");
        findViewById(R.id.btn).setVisibility(View.GONE);
        LogUtil.i("LifeActivity2    onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("LifeActivity2    onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.i("LifeActivity2    onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("LifeActivity2    onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("LifeActivity2    onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtil.i("LifeActivity2    onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i("LifeActivity2    onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("LifeActivity2    onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i("LifeActivity2    onRestart");
    }
}
