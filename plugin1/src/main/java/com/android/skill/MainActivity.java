package com.android.skill;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                Intent intent = new Intent("com.android.skill.braocastrecevier");
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);
                break;
            case R.id.tv_2:
                Intent intent2 = new Intent("com.android.skill.braocastrecevier2");
                intent2.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent2);
                break;
            case R.id.tv_3:
                Uri uri = Uri.parse("content://test.cn.example.com.androidskill.stub/com.android.skill.plugin_cp_1");
                Cursor queryCurosr = getContentResolver().query(uri, null, null, null, null);

                break;
        }
    }
}
