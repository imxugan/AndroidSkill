package test.cn.example.com.androidskill;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;

import test.cn.example.com.util.LogUtil;

/**
 * 属性动画LayoutTransition使用演示
 * Created by xgxg on 2017/8/25.
 */
public class PropertyAnimationLayoutTransitionActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ViewGroup ll_contain;
    private int index;
    private GridLayout gridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_layout_transition);
        initView();
    }

    private void initView() {
        Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        CheckBox appearance = (CheckBox) findViewById(R.id.appearance);
        appearance.setOnCheckedChangeListener(this);
        CheckBox change_appearance = (CheckBox) findViewById(R.id.change_appearance);
        change_appearance.setOnCheckedChangeListener(this);
        CheckBox disappear = (CheckBox) findViewById(R.id.disappear);
        disappear.setOnCheckedChangeListener(this);
        CheckBox change_disappear = (CheckBox) findViewById(R.id.change_disappear);
        change_disappear.setOnCheckedChangeListener(this);

        ll_contain = (ViewGroup) findViewById(R.id.ll_contain);
        //创建一个GridLayout
        gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(5);
        ll_contain.addView(gridLayout);
        LayoutTransition layoutTransition = new LayoutTransition();
        gridLayout.setLayoutTransition(layoutTransition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                addBtn();
                break;
            case R.id.change_appearance:
                change_appearance();
                break;
            case R.id.disappear:
                disappear();
                break;
            case R.id.change_disappear:
                change_disappear();
                break;
        }
    }

    private void change_disappear(){

    }

    private void disappear(){

    }

    private void change_appearance(){

    }

    private void addBtn(){
        final Button button = new Button(this);
        button.setText(index++);
        gridLayout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridLayout.removeView(button);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.appearance:
                LogUtil.i("appearance");
                break;
            case R.id.change_appearance:
                LogUtil.i("change_appearance");
                break;

        }
    }
}
