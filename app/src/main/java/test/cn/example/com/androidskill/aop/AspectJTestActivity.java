package test.cn.example.com.androidskill.aop;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Random;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.aop.annotation.Monitored;

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

    @Monitored
    public void shake(){
        try{
            SystemClock.sleep(new Random().nextInt(2000));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Monitored
    public void chat(){
        try{
            SystemClock.sleep(new Random().nextInt(2000));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
