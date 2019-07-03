package test.cn.example.com.androidskill.art.chapter_four;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.fragment.CustomContainerFragment1;
import test.cn.example.com.androidskill.fragment.CustomContainerFragment2;
import test.cn.example.com.androidskill.fragment.CustomContainerFragment3;
import test.cn.example.com.androidskill.fragment.CustomContainerFragment4;

/**
 * Created by xgxg on 2017/9/8.
 */

public class CustomContainerActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtn_1;
    private Button mBtn_2;
    private Button mBtn_3;
    private Button mBtn_4;

    private CustomContainerFragment1 fragment1;
    private CustomContainerFragment2 fragment2;
    private CustomContainerFragment3 fragment3;
    private CustomContainerFragment4 fragment4;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_container);
        initView();
        mFragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    private void initView() {
        mBtn_1 = (Button) findViewById(R.id.btn_1);
        mBtn_2 = (Button) findViewById(R.id.btn_2);
        mBtn_3 = (Button) findViewById(R.id.btn_3);
        mBtn_4 = (Button) findViewById(R.id.btn_4);
        mBtn_1.setOnClickListener(this);
        mBtn_2.setOnClickListener(this);
        mBtn_3.setOnClickListener(this);
        mBtn_4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                setTabSelection(0);
                break;
            case R.id.btn_2:
                setTabSelection(1);
                break;
            case R.id.btn_3:
                setTabSelection(2);
                break;
            case R.id.btn_4:
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index) {
        //每次选中前先清除选中的状态
        clearSelection();
        //开启事务管理
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //先隐藏所有的fragment,防止多个fragmetn出现在界面上
        hideFragmetns(transaction);
        switch (index){
            case 0:
                mBtn_1.setTextColor(Color.RED);
                if(null == fragment1){
                    //如果不存在，则创建并添加到界面
                    fragment1 = new CustomContainerFragment1();
                    transaction.add(R.id.content,fragment1);
                }else {
                    //存在，就显示到界面
                    transaction.show(fragment1);
                }
                break;
            case 1:
                mBtn_2.setTextColor(Color.RED);
                if(null == fragment2){
                    //如果不存在，则创建并添加到界面
                    fragment2 = new CustomContainerFragment2();
                    transaction.add(R.id.content,fragment2);
                }else {
                    //存在，就显示到界面
                    transaction.show(fragment2);
                }
                break;
            case 2:
                mBtn_3.setTextColor(Color.RED);
                if(null == fragment3){
                    fragment3 = new CustomContainerFragment3();
                    transaction.add(R.id.content,fragment3);
                }else {
                    transaction.show(fragment3);
                }
                break;
            case 3:
            default:
                mBtn_4.setTextColor(Color.RED);
                if(null == fragment4){
                    fragment4 = new CustomContainerFragment4();
                    transaction.add(R.id.content,fragment4);
                }else {
                    transaction.show(fragment4);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragmetns(FragmentTransaction transaction) {
        if(null !=fragment1){
            transaction.hide(fragment1);
        }

        if(null != fragment2){
            transaction.hide(fragment2);
        }

        if(null != fragment3){
            transaction.hide(fragment3);
        }

        if(null != fragment4){
            transaction.hide(fragment4);
        }
    }

    private void clearSelection() {
        mBtn_1.setTextColor(Color.BLACK);
        mBtn_2.setTextColor(Color.BLACK);
        mBtn_3.setTextColor(Color.BLACK);
        mBtn_4.setTextColor(Color.BLACK);
    }
}
