package test.cn.example.com.androidskill.art.chapter_seven;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;

/**
 * 属性动画animateLayoutChanges使用演示
 * https://developer.android.com/training/animation/layout.html
 * Created by xgxg on 2017/8/26.
 */
public class PropertyAnimationLayoutChangesActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewGroup ll_contain;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_layout_changes);
        initView();
        //在需要使用布局动画的viewgroup中使用    android:animateLayoutChanges="true"

    }

    private void initView() {
        Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);

        ll_contain = (ViewGroup) findViewById(R.id.ll_contain);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                addBtn();
                break;
        }
    }

    private void addBtn(){
        final Button button = new Button(PropertyAnimationLayoutChangesActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        button.setText(""+(index++));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_contain.removeView(button);
            }
        });
        ll_contain.addView(button);
    }

}
