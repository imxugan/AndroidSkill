package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import test.cn.example.com.androidskill.view.CircleView;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/9.
 * view的工作原理
 */
public class ChapterFourActivity extends AppCompatActivity implements View.OnClickListener{
    CircleView circleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charapter_four);
//        initView();
        circleView = (CircleView) findViewById(R.id.circleView);
        FrameLayout frameLayout = (FrameLayout) circleView.getParent();
        LogUtil.i(""+frameLayout.getLayoutParams().width);
        LogUtil.i(""+frameLayout.getLayoutParams().height);
        LogUtil.i("FrameLayout.LayoutParams.MATCH_PARENT="+ FrameLayout.LayoutParams.MATCH_PARENT);
        LogUtil.i("FrameLayout.LayoutParams.WRAP_CONTENT="+ FrameLayout.LayoutParams.WRAP_CONTENT);
    }

//    private void initView() {
//        Button life = (Button)findViewById(R.id.life);
//        life.setOnClickListener(this);
//        Button launch_mode = (Button)findViewById(R.id.launch_mode);
//        launch_mode.setOnClickListener(this);
//    }


    @Override
    protected void onStart() {
        super.onStart();
        circleView.post(new Runnable() {
            @Override
            public void run() {
                int width = circleView.getMeasuredWidth();

                LogUtil.i("tv----width------"+width);

            }
        });
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.life:
//                startActivity(new Intent(ChapterFourActivity.this,LifeActivity.class));
//                break;
//            case R.id.launch_mode:
//                startActivity(new Intent(ChapterFourActivity.this,LannchModeActivity.class));
//                break;
//            default:
//                break;
//        }
    }
}
