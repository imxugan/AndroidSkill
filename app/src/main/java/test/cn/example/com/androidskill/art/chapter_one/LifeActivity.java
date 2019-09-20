package test.cn.example.com.androidskill.art.chapter_one;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
        testWindowState();
    }

    private void testWindowState() {
        Window window = getWindow();
        View decorView = window.getDecorView();
        int visibility = decorView.getVisibility();
        WindowManager.LayoutParams attributes = window.getAttributes();
        int type = attributes.type;
        LogUtil.e(type+"   "+"window="+window.hashCode()+"      decorView="+decorView.hashCode()+"  "+"visibility= "+visibility+"   View.VISIBLE="+View.VISIBLE);
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
