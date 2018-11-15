package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.view.defineView.hencoder.HenCoderPracticeActivity;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.HenCoderPracticeDrawOneActivity;

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
        Button hencoder = (Button)findViewById(R.id.hencoder);
        hencoder.setOnClickListener(this);
        Button circleView = (Button)findViewById(R.id.circleView);
        circleView.setOnClickListener(this);
        Button customTitleView01 = (Button)findViewById(R.id.customTitleView01);
        customTitleView01.setOnClickListener(this);
        Button customView01 = (Button)findViewById(R.id.customView01);
        customView01.setOnClickListener(this);
        Button customView02 = (Button)findViewById(R.id.customView02);
        customView02.setOnClickListener(this);
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
        Button qqListView = (Button)findViewById(R.id.qqListView);
        qqListView.setOnClickListener(this);
        Button scroll = (Button)findViewById(R.id.scroll);
        scroll.setOnClickListener(this);
        Button verticalLinearLayout = (Button)findViewById(R.id.verticalLinearLayout);
        verticalLinearLayout.setOnClickListener(this);
        Button gestureLockView = (Button)findViewById(R.id.gestureLockView);
        gestureLockView.setOnClickListener(this);
        Button arcMenu = (Button)findViewById(R.id.arcMenu);
        arcMenu.setOnClickListener(this);
        Button flowLayout = (Button)findViewById(R.id.flowLayout);
        flowLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hencoder:
                startActivity(new Intent(ChapterFourActivity.this,HenCoderPracticeActivity.class));
                break;
            case R.id.circleView:
                startActivity(new Intent(ChapterFourActivity.this,CircleViewActivity.class));
                break;
            case R.id.customTitleView01:
                startActivity(new Intent(ChapterFourActivity.this,CustomTitleView01Activity.class));
                break;
            case R.id.customView01:
                startActivity(new Intent(ChapterFourActivity.this,CustomViewActivity.class));
                break;
            case R.id.customView02:
                startActivity(new Intent(ChapterFourActivity.this,CustomViewActivity2.class));
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
            case R.id.qqListView:
                startActivity(new Intent(ChapterFourActivity.this,QQListViewActivity.class));
                break;
            case R.id.scroll:
                startActivity(new Intent(ChapterFourActivity.this,ScrollActivity.class));
                break;
            case R.id.verticalLinearLayout:
                startActivity(new Intent(ChapterFourActivity.this,VerticalLinearLayoutActivity.class));
                break;
            case R.id.gestureLockView:
                startActivity(new Intent(ChapterFourActivity.this,GestureLockViewActivity.class));
                break;
            case R.id.arcMenu:
                startActivity(new Intent(ChapterFourActivity.this,ArcMenuActivity.class));
                break;
            case R.id.flowLayout:
                startActivity(new Intent(ChapterFourActivity.this,FlowLayoutActivity.class));
                break;
            default:
                break;
        }
    }
}
