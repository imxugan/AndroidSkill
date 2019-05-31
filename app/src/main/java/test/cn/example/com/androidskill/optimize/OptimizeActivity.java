package test.cn.example.com.androidskill.optimize;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.optimize.flatbuffer.FlatBufferActivity;
import test.cn.example.com.androidskill.optimize.hotfix.HotFixActivity;
import test.cn.example.com.androidskill.optimize.httpresponsecache.HttpResponseCacheActivity;
import test.cn.example.com.androidskill.optimize.myhandler.MyHandlerTestActivity;
import test.cn.example.com.androidskill.optimize.process_keep.KeepProcessActivity;
import test.cn.example.com.androidskill.optimize.service_keep.KeepLiveActivity;
import test.cn.example.com.androidskill.optimize.service_keep.MyService;

/**
 * Created by xugan on 2019/5/16.
 */

public class OptimizeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);

        startService(new Intent(this,MyService.class));

        findViewById(R.id.btn_httpResponseCache).setOnClickListener(this);
        findViewById(R.id.btn_flatBuffer).setOnClickListener(this);
        findViewById(R.id.btn_handler).setOnClickListener(this);
        findViewById(R.id.btn_pic).setOnClickListener(this);
        findViewById(R.id.btn_service_keep_live).setOnClickListener(this);
        findViewById(R.id.btn_process_keep_live).setOnClickListener(this);
        findViewById(R.id.btn_hot_fix).setOnClickListener(this);
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
            case R.id.btn_pic:
                startActivity(new Intent(OptimizeActivity.this,PicActivity.class));
                break;
            case R.id.btn_service_keep_live:
                startActivity(new Intent(OptimizeActivity.this,KeepLiveActivity.class));
                break;
            case R.id.btn_process_keep_live:
                startActivity(new Intent(OptimizeActivity.this,KeepProcessActivity.class));
                break;
            case R.id.btn_hot_fix:
                startActivity(new Intent(OptimizeActivity.this,HotFixActivity.class));
                break;
        }
    }
}
