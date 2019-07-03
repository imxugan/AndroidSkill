package test.cn.example.com.androidskill.art.chapter_nine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.ServiceWorkActivity;

/**
 * 四大组件工作过程
 */
public class ChapterNineActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charapter_nine);
        initView();
    }

    private void initView() {
        Button activity_work = (Button)findViewById(R.id.activity_work);
        activity_work.setOnClickListener(this);
        Button service_work = (Button)findViewById(R.id.service_work);
        service_work.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_work:
//                startActivity(new Intent(ChapterNineActivity.this,LifeActivity.class));
                break;
            case R.id.service_work:
                startActivity(new Intent(ChapterNineActivity.this,ServiceWorkActivity.class));
                break;
            default:
                break;
        }
    }
}
