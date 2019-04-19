package test.cn.example.com.androidskill.aop;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Random;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.aop.annotation.AsyncThread;
import test.cn.example.com.androidskill.aop.annotation.MainThread;
import test.cn.example.com.androidskill.aop.annotation.Monitored;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/19.
 */

public class AspectJTestActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspectj);
        findViewById(R.id.btn_shake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shake();
            }
        });

        findViewById(R.id.btn_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat();
            }
        });
    }

    @AsyncThread
    @Monitored("摇一摇")
    public void shake(){
        LogUtil.i(Thread.currentThread().getName());
        try{
            SystemClock.sleep(new Random().nextInt(2000));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @MainThread
    @Monitored("聊天")
    public void chat(){
        LogUtil.i(Thread.currentThread().getName());
        try{
            SystemClock.sleep(new Random().nextInt(2000));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
