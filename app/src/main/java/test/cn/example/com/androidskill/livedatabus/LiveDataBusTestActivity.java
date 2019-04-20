package test.cn.example.com.androidskill.livedatabus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * activity的生命周期
 */
public class LiveDataBusTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedatabus);
        LogUtil.i("onCreate");
        LiveDataBus.getInstance().with("apple",String.class).observe((LifecycleOwner)LiveDataBusTestActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ToastUtils.shortToast(LiveDataBusTestActivity.this,s);
            }
        });
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_go).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                LiveDataBus.getInstance().with("apple",String.class).postValue("test");
                break;
            case R.id.btn_go:
                startActivity(new Intent(LiveDataBusTestActivity.this,LiveDataBusTestActivity2.class));
                break;
        }
    }
}
