package test.cn.example.com.androidskill.optimize.handler_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/5/31.
 */

public class HandlerThreadActivity extends AppCompatActivity {

    private TextView tv;
    private HandlerThread handlerThread = new HandlerThread("fetching_thread");
    private Handler fetchingHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        tv = findViewById(R.id.tv);
        handlerThread.start();//记得启动线程，否则会报错
        fetchingHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SystemClock.sleep(500);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("石油的汇率"+new Random().nextInt(10));
                    }
                });
                fetchingHandler.sendEmptyMessage(1);
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchingHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handlerThread.quit();
    }
}
