package test.cn.example.com.androidskill.optimize.splash;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewStub;

import java.lang.ref.WeakReference;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/1.
 */

public class SplashActivityOptimize extends FragmentActivity {
    private Handler mHandler = new Handler();
    private ViewStub viewStub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_optimize_layout);
        viewStub = findViewById(R.id.viewstub);
        final SplashFragment splashFragment = new SplashFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,splashFragment);
        fragmentTransaction.commit();
        //当窗体加载完毕后，加载SplashActivityOptimize的真正要展示的布局
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(Thread.currentThread().getName());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //将viewstub加载进来
                        viewStub.inflate();
                    }
                });
            }
        });

        //2.当窗体加载完毕后，延时2秒执行一个任务，这里延迟2秒的时间，相当于模拟一个动画的执行时间
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(Thread.currentThread().getName()+"   onResume 之后 ");
                mHandler.postDelayed(new DelayRunnable(SplashActivityOptimize.this,splashFragment),2000l);
            }
        });
    }

    static class DelayRunnable implements Runnable{
        //防止内存泄漏,所以采用静态内部类和WeakReference
        private WeakReference<Context> contextWeakReference = null;
        private WeakReference<Fragment> fragmentWeakReference = null;
        public DelayRunnable(Context context,SplashFragment splashFragment){
            contextWeakReference = new WeakReference<Context>(context);
            fragmentWeakReference = new WeakReference<Fragment>(splashFragment);
        }
        @Override
        public void run() {
            Context context = contextWeakReference.get();
            if(null != context){
                if(null != fragmentWeakReference.get()){}
                SplashActivityOptimize activity = (SplashActivityOptimize) context;
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(fragmentWeakReference.get());
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("onResume");
    }
}
