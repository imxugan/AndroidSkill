package test.cn.example.com.androidskill.my_eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xg.mybus.MyBus;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/4/18.
 */

public class MyEventBusTestActivity2 extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_eventbus2);
        findViewById(R.id.btn_test_mybus_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBus.getInstance().post("123  from  MyEventBusTestActivity2");
            }
        });

        findViewById(R.id.btn_test_mybus_not_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyBus.getInstance().post("456  from  MyEventBusTestActivity2");
                    }
                }).start();
            }
        });
    }
}
