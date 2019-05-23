package test.cn.example.com.androidskill.optimize;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.optimize.flatbuffer.FlatBufferActivity;
import test.cn.example.com.androidskill.optimize.httpresponsecache.HttpResponseCacheActivity;
import test.cn.example.com.androidskill.optimize.myhandler.MyHandlerTestActivity;

/**
 * Created by xugan on 2019/5/16.
 */

public class OptimizeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);
        findViewById(R.id.btn_httpResponseCache).setOnClickListener(this);
        findViewById(R.id.btn_flatBuffer).setOnClickListener(this);
        findViewById(R.id.btn_handler).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_httpResponseCache:
                startActivity(new Intent(OptimizeActivity.this,HttpResponseCacheActivity.class));
                break;
            case R.id.btn_flatBuffer:
                startActivity(new Intent(OptimizeActivity.this,FlatBufferActivity.class));
                break;
            case R.id.btn_handler:
                startActivity(new Intent(OptimizeActivity.this,MyHandlerTestActivity.class));
                break;
        }
    }
}
