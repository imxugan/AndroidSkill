package test.cn.example.com.androidskill.leak;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;


public class ThreadAboutLeakActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_about_leak);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60*1000*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        new MyThread().start();

    }

    private static class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(60*1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
