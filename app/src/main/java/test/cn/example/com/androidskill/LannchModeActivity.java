package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * activity的启动模式
 */
public class LannchModeActivity extends AppCompatActivity implements View.OnClickListener {
    Button launch_single_task,launch_single_top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        initView();
    }

    private void initView() {
        launch_single_task = (Button) findViewById(R.id.launch_single_task);
        launch_single_task.setOnClickListener(this);
        launch_single_top = (Button) findViewById(R.id.launch_single_top);
        launch_single_top.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.launch_single_task:
                startActivity(new Intent(LannchModeActivity.this,SingleTaskActivity.class));
                break;
            case R.id.launch_single_top:
                startActivity(new Intent(LannchModeActivity.this,SingleTopActivity.class));
                break;

            default:
                break;
        }
    }

}
