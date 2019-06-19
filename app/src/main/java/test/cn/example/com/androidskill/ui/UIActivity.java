package test.cn.example.com.androidskill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.ui.compact.DrawerLayoutActivity;
import test.cn.example.com.androidskill.ui.compact.ItemTouchHelperActivity;
import test.cn.example.com.androidskill.ui.compact.LinerLayoutCompactActivity;
import test.cn.example.com.androidskill.ui.compact.ListPopupWindowActivity;
import test.cn.example.com.androidskill.ui.compact.PopMenuActivity;
import test.cn.example.com.androidskill.ui.compact.RecyclerViewActivity;
import test.cn.example.com.androidskill.ui.compact.SwipeRefreshLayoutActivity;
import test.cn.example.com.androidskill.ui.compact.WraperRecyclerView;
import test.cn.example.com.androidskill.ui.compact.WraperRecyclerViewActivity;

/**
 * Created by xugan on 2019/6/11.
 */

public class UIActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                startActivity(new Intent(UIActivity.this,SwipeRefreshLayoutActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(UIActivity.this,ListPopupWindowActivity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(UIActivity.this,PopMenuActivity.class));
                break;
            case R.id.btn_4:
                startActivity(new Intent(UIActivity.this,LinerLayoutCompactActivity.class));
                break;
            case R.id.btn_5:
                startActivity(new Intent(UIActivity.this,RecyclerViewActivity.class));
                break;
            case R.id.btn_6:
                startActivity(new Intent(UIActivity.this,WraperRecyclerViewActivity.class));
                break;
            case R.id.btn_7:
                startActivity(new Intent(UIActivity.this,ItemTouchHelperActivity.class));
                break;
            case R.id.btn_8:
                startActivity(new Intent(UIActivity.this,DrawerLayoutActivity.class));
                break;
        }
    }
}
