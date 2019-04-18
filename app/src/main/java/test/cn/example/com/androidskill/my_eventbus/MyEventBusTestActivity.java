package test.cn.example.com.androidskill.my_eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xg.mybus.MyBus;
import com.xg.mybus.Subscribe;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2019/4/17.
 */

public class MyEventBusTestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_eventbus);
        MyBus.getInstance().register(this);
        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyEventBusTestActivity.this,MyEventBusTestActivity2.class));
            }
        });
        findViewById(R.id.btn_test_mybus_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBus.getInstance().post("123");
            }
        });

        findViewById(R.id.btn_test_mybus_not_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyBus.getInstance().post("456");
                    }
                }).start();
            }
        });
    }

    @Subscribe
    public void getMyBusEvent(String eventData){
        ToastUtils.shortToast(MyEventBusTestActivity.this,eventData+"       "+Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
