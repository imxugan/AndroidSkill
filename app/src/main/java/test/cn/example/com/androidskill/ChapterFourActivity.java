package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by xgxg on 2017/8/9.
 * view的工作原理
 */
public class ChapterFourActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charapter_four);
        initView();

    }

    private void initView() {
        Button circleView = (Button)findViewById(R.id.circleView);
        circleView.setOnClickListener(this);
        Button customTitleView01 = (Button)findViewById(R.id.customTitleView01);
        customTitleView01.setOnClickListener(this);
        Button customView01 = (Button)findViewById(R.id.customView01);
        customView01.setOnClickListener(this);
        Button customImageView01 = (Button)findViewById(R.id.customImageView01);
        customImageView01.setOnClickListener(this);
        Button customProgressBar01 = (Button)findViewById(R.id.customProgressBar01);
        customProgressBar01.setOnClickListener(this);
        Button paint_canvas = (Button)findViewById(R.id.paint_canvas);
        paint_canvas.setOnClickListener(this);
        Button customVolumeControlBar = (Button)findViewById(R.id.customVolumeControlBar);
        customVolumeControlBar.setOnClickListener(this);
        Button customContainer = (Button)findViewById(R.id.customContainer);
        customContainer.setOnClickListener(this);
        Button customContainer2 = (Button)findViewById(R.id.customContainer2);
        customContainer2.setOnClickListener(this);
        Button customAttr = (Button)findViewById(R.id.customAttr);
        customAttr.setOnClickListener(this);
        Button viewDragHelper = (Button)findViewById(R.id.viewDragHelper);
        viewDragHelper.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circleView:
                startActivity(new Intent(ChapterFourActivity.this,CircleViewActivity.class));
                break;
            case R.id.customTitleView01:
                startActivity(new Intent(ChapterFourActivity.this,CustomTitleView01Activity.class));
                break;
            case R.id.customView01:
                startActivity(new Intent(ChapterFourActivity.this,CustomViewActivity.class));
                break;
            case R.id.customImageView01:
                startActivity(new Intent(ChapterFourActivity.this,CustomImageViewActivity.class));
                break;
            case R.id.customProgressBar01:
                startActivity(new Intent(ChapterFourActivity.this,CustomProgressBarActivity.class));
                break;
            case R.id.paint_canvas:
                startActivity(new Intent(ChapterFourActivity.this,CustomActivity.class));
                break;
            case R.id.customVolumeControlBar:
                startActivity(new Intent(ChapterFourActivity.this,CustomVolumeControlBar.class));
                break;
            case R.id.customContainer:
                startActivity(new Intent(ChapterFourActivity.this,CustomContainerActivity.class));
                break;
            case R.id.customContainer2:
                startActivity(new Intent(ChapterFourActivity.this,CustomContainer2Activity.class));
                break;
            case R.id.customAttr:
                startActivity(new Intent(ChapterFourActivity.this,CustomAttrActivity.class));
                break;
            case R.id.viewDragHelper:
                startActivity(new Intent(ChapterFourActivity.this,ViewDragHelperLayoutActivity.class));
                break;
            default:
                break;
        }
    }
}
