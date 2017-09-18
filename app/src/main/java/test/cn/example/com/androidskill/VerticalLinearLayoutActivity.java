package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.view.defineView.VerticalLinearLayout;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/14.
 */

public class VerticalLinearLayoutActivity extends AppCompatActivity {

    private View mRl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_linearlayout);
        VerticalLinearLayout verticalLinearLayout = (VerticalLinearLayout) findViewById(R.id.verticalLinearLayout);
        verticalLinearLayout.setOnPageChangeListener(new VerticalLinearLayout.OnPageChangeListener() {
            @Override
            public void onPageChange(int page) {
//                LogUtil.i("page="+page);
//                Toast.makeText(VerticalLinearLayoutActivity.this, "第"+(page+1)+"页", Toast.LENGTH_SHORT).show();
            }
        });

        mRl = findViewById(R.id.rl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRl.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.i("mRl.getHeight()="+mRl.getHeight());
            }
        });
    }
}
