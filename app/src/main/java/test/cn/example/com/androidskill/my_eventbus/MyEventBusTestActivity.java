package test.cn.example.com.androidskill.my_eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/4/17.
 */

public class MyEventBusTestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_skin);
        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MyEventBusTestActivity.this,));
            }
        });
    }
}
