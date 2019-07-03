package test.cn.example.com.androidskill.art.chapter_seven;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * 属性动画LayoutTransition使用演示
 * Created by xgxg on 2017/8/25.
 */
public class PropertyAnimationLayoutTransitionActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ViewGroup ll_contain;
    private int index;
    private GridLayout gridLayout;
    private LayoutTransition layoutTransition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_layout_transition);
        initView();
//        LayoutTransition 是API Level 11 才出现的。LayoutTransition的动画效果，
//        只有当ViewGroup中有View添加、删除、隐藏、显示的时候才会体现出来。
//
//         LayoutTransition类中主要有五种容器转换动画类型，具体如下：
//
//        LayoutTransition.APPEARING            当一个View在ViewGroup中出现时，对此View设置的动画
//        LayoutTransition.CHANGE_APPEARING     当一个View在ViewGroup中出现时，
//                          对此View对其他View位置造成影响，对其他View设置的动画
//        LayoutTransition.DISAPPEARING         当一个View在ViewGroup中消失时，对此View设置的动画
//        LayoutTransition.CHANGE_DISAPPEARING      当一个View在ViewGroup中消失时，
//                                      对此View对其他View位置造成影响，对其他View设置的动画
//        LayoutTransition.CHANGE       不是由于View出现或消失造成对其他View位置造成影响，
//                                      然后对其他View设置的动画。
//        注意动画到底设置在谁身上，此View还是其他View。
    }

    private void initView() {
        Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        CheckBox appearance = (CheckBox) findViewById(R.id.appearing);
        appearance.setOnCheckedChangeListener(this);
        CheckBox change_appearance = (CheckBox) findViewById(R.id.change_appearing);
        change_appearance.setOnCheckedChangeListener(this);
        CheckBox disappear = (CheckBox) findViewById(R.id.disappearing);
        disappear.setOnCheckedChangeListener(this);
        CheckBox change_disappear = (CheckBox) findViewById(R.id.change_disappearing);
        change_disappear.setOnCheckedChangeListener(this);

        ll_contain = (ViewGroup) findViewById(R.id.ll_contain);
        //创建一个GridLayout
        gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(5);
        ll_contain.addView(gridLayout);
        layoutTransition = new LayoutTransition();
        gridLayout.setLayoutTransition(layoutTransition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                addBtn();
                break;
        }
    }

    private void change_disappearing(boolean isChecked) {
        layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
                isChecked?layoutTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING):null);
    }

    private void disappearing(boolean isChecked) {
        layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING,
                isChecked?layoutTransition.getAnimator(LayoutTransition.DISAPPEARING):null);
    }

    private void change_appearing(PropertyAnimationLayoutTransitionActivity button ,boolean isChecked){
        layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING,
               isChecked?ObjectAnimator.ofFloat(button,"scaleX",1f,1.5f):null);

    }

    private void appearing(boolean isChecked) {
        layoutTransition = new LayoutTransition();
//        layoutTransition.setAnimator(LayoutTransition.APPEARING,
//                isChecked?layoutTransition.getAnimator(LayoutTransition.APPEARING):null);

        LogUtil.i("isChecked========"+isChecked);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "rotationY", 0F, 90F, 0F);
        layoutTransition.setAnimator(LayoutTransition.APPEARING, isChecked?animator1:null);
    }

    private void addBtn(){
        final Button button = new Button(PropertyAnimationLayoutTransitionActivity.this);
        button.setText(""+(index++));
//        int childCount = gridLayout.getChildCount();
//        if(childCount>3){
//            gridLayout.addView(button, new Random().nextInt(childCount));
//        }else {
//            gridLayout.addView(button);
//        }
        gridLayout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridLayout.removeView(button);
            }
        });
        LogUtil.i("?=="+layoutTransition.getAnimator(LayoutTransition.APPEARING));
        gridLayout.setLayoutTransition(layoutTransition);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.appearing:
                appearing(isChecked);
                break;
            case R.id.change_appearing:
                LogUtil.i("this="+this);
                change_appearing(this,isChecked);
                break;
            case R.id.disappearing:
                disappearing(isChecked);
                break;
            case R.id.change_disappearing:
                change_disappearing(isChecked);
                break;

        }
    }


}
