package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TextView debug = (TextView) findViewById(R.id.debug);
        debug.setOnClickListener(this);
        TextView threadPool = (TextView) findViewById(R.id.threadPool);
        threadPool.setOnClickListener(this);
        TextView charapter1 = (TextView) findViewById(R.id.charapter1);
        charapter1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.debug:
                Intent intent_debug = new Intent(MainActivity.this, DebugActivity.class);
                myStartActivity(intent_debug);
                break;
            case R.id.threadPool:
                Intent intent_threadPool = new Intent(MainActivity.this, ThreadPoolActivity.class);
                myStartActivity(intent_threadPool);
                break;
            case R.id.charapter1:
                Intent intent = new Intent(MainActivity.this, ChapterOneActivity.class);
                myStartActivity(intent);
                break;
            default:
                break;
        }
    }

    private void myStartActivity(Intent i){
        startActivity(i);
    }
}
