package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.util.LogUtil;

import static java.lang.Thread.currentThread;

/**
 * Created by xgxg on 2017/8/9.
 * hanlder的工作原理以及相关的知识点
 */
public class HandlerActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        initBackThread();//初始化一个子线程handlerThreaad
        initView();
    }

    private void initBackThread() {
        HandlerThread handlerThread = new HandlerThread("test_hadndler_thread"){
            @Override
            protected void onLooperPrepared() {
                super.onLooperPrepared();
                LogUtil.i("当前线程是:   "+currentThread().getName());
            }
        };
        handlerThread.start();//开启线程
        //创建一个handler，并使handlerThread中的loop与这个handler关联
        Handler h = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                LogUtil.i("当前线程是:   "+currentThread().getName());
                updateUi(msg.what,msg.obj.toString());
            }
        };
        //创建消息，并在主线程中这个名为h的handler对象发送消息，
        //由于handler创建的时候，构造方法中传的是子线程的looper，所以
        //当这个名为h的handler对象发送消息后，这个消息会在子线程的消息
        //队列中，被子线程的Looper取出，并在子线程中去处理这个消息，
        //所以handleMessage方法所在的线程是子线程。
        //这种情况就相当于是主线程的handler发送消息到，然后消息在子线程中被处理。
        //平时我们在主线程中创建handler,然后在开一个子线程，在子线程
        //中使用主线程的handler发送消息，然后，消息最后是在主线程中被处理一样。
        //handlerThread只是将这种情况反转了。
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = "hello handlerThread";
        h.sendMessage(msg);

    }

    private void updateUi(final int what,final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(what+"---"+result);
            }
        });
    }

    private void initView() {
        Button handlerThread = (Button)findViewById(R.id.handlerThread);
        handlerThread.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.handlerThread:
                handlerThreadTest();
                break;
            default:
                break;
        }
    }

    private void handlerThreadTest() {

    }
}
