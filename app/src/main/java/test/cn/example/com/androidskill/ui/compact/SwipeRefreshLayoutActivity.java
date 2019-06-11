package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2019/6/11.
 */

public class SwipeRefreshLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compact);
        final SwipeRefreshLayout srl = findViewById(R.id.srl);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.shortToast(getApplication(),"刷新了");
                srl.setRefreshing(false);
            }
        });
    }
}
