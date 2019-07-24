package test.cn.example.com.androidskill.ui.compact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/23.
 */

public class FirstCompatStatusBarActivity extends CompatStatusBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_compat_status_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        int color = 0xffaa66cc;
        toolbar.setBackgroundColor(color);
        setImmersiveStatusBar(false, color);
    }

    public void go(View view) {
        startActivity(new Intent(this, NextActivity.class));
    }
}
