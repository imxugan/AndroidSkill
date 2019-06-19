package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/19.
 */

public class DrawerLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//用toolbar替代actionbar

    }
}
