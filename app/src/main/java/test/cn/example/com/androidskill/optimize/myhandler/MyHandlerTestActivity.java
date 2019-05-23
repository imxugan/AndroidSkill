package test.cn.example.com.androidskill.optimize.myhandler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/5/21.
 */

public class MyHandlerTestActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhandler);
        MyLooper.prepare();
        final MyHandler myHandler = new MyHandler(){
            @Override
            public void handleMessage(MyMessaga myMessaga) {
                LogUtil.i(myMessaga.toString());
            }
        };
        for(int i=0;i<10;i++){
            new Thread(){
                @Override
                public void run() {
                    while (true){
                        synchronized (UUID.class){
                            MyMessaga myMessaga = new MyMessaga();
                            myMessaga.what = 1;
                            myMessaga.obj = Thread.currentThread().getName()+"   ,   "+UUID.randomUUID().toString();
                            LogUtil.i("send msg     "+myMessaga.toString());
                            myHandler.sendMessage(myMessaga);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }.start();

        }
        MyLooper.loop();
    }
}
