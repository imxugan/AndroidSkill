package test.cn.example.com.androidskill.art.chapter_one;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * activity的生命周期和启动模式
 */
public class ChapterOneActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charapter_one);
        initView();
        testWindowState();
    }

    private void testWindowState() {
        Window window = getWindow();
        View decorView = window.getDecorView();
        int visibility = decorView.getVisibility();
        WindowManager.LayoutParams attributes = window.getAttributes();
        int type = attributes.type;
        LogUtil.e(type+"   "+"window="+window.hashCode()+"      decorView="+decorView.hashCode()+"  "+"visibility= "+visibility+"   View.VISIBLE="+View.VISIBLE);
    }

    private void initView() {
        Button life = (Button)findViewById(R.id.life);
        life.setOnClickListener(this);
        Button launch_mode = (Button)findViewById(R.id.launch_mode);
        launch_mode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.life:
                startActivity(new Intent(ChapterOneActivity.this,LifeActivity.class));
                break;
            case R.id.launch_mode:
                startActivity(new Intent(ChapterOneActivity.this,LannchModeActivity.class));
                break;
            default:
                break;
        }
    }
}
