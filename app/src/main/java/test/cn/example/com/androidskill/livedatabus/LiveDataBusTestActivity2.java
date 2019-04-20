package test.cn.example.com.androidskill.livedatabus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.ToastUtils;

/**
 * activity的生命周期
 */
public class LiveDataBusTestActivity2 extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedatabus2);
        LiveDataBus.getInstance().with("apple",String.class).observe((LifecycleOwner)LiveDataBusTestActivity2.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ToastUtils.shortToast(LiveDataBusTestActivity2.this,s);
            }
        });

    }

}
