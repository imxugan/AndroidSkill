package test.cn.example.com.androidskill.leak;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class HandlerLeakActivity extends AppCompatActivity {
    private MyHandler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_leak);
        mHandler = new MyHandler();
        mHandler.sendMessageDelayed(mHandler.obtainMessage(100),60000);
    }

    private void test(){
        LogUtil.i("模拟延时任务");
    }

    private class MyHandler extends  Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    test();
                    break;
            }
        }
    }
}
