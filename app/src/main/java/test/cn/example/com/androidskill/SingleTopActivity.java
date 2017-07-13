package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.util.LogUtil;

/**
 * activity的启动模式之singleTop
 */
public class SingleTopActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);
        LogUtil.i("onCreate");
        Button launch_single_top = (Button)findViewById(R.id.launch_single_top);
        launch_single_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("action.com.android.skill");
                intent.addCategory("category.com.android.skill");

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("onStart");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtil.i("onSaveInstanceState");
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.i("onRestoreInstanceState");
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.e("onNewIntent");
    }
}
