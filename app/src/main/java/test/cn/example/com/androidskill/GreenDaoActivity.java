package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.view.defineView.DrawView;

/**
 *  GreenDao的使用演示
 * Created by xgxg on 2018/7/26.
 */

public class GreenDaoActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        init();
    }

    private void init() {
       findViewById(R.id.addBtn).setOnClickListener(this);
       findViewById(R.id.addAllBtn).setOnClickListener(this);
       findViewById(R.id.deleteBtn).setOnClickListener(this);
       findViewById(R.id.deleteAllBtn).setOnClickListener(this);
       findViewById(R.id.updateBtn).setOnClickListener(this);
       findViewById(R.id.selectBtn).setOnClickListener(this);
       findViewById(R.id.selectAllBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addBtn:

                break;
            case R.id.addAllBtn:

                break;
            case R.id.deleteBtn:

                break;
            case R.id.deleteAllBtn:

                break;
            case R.id.updateBtn:

                break;
            case R.id.selectBtn:

                break;
            case R.id.selectAllBtn:

                break;
            default:
                break;
        }
    }
}
