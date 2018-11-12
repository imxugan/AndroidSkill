package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.Boss;
import test.cn.example.com.androidskill.model.Test;
import test.cn.example.com.androidskill.model.Test2;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/18.
 */

public class SingleInstancePatternActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance_pattern);
        initView();
    }

    private void initView(){
        Button btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        Button btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        Button btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                Test t = Test.getInstance();
                LogUtil.i("t="+t);
                break;
            case R.id.btn_2:
                Test2 t2 = Test2.getInstance();
                LogUtil.i("t2="+t2);
                t2 = Test2.getInstance();
                LogUtil.i("t2="+t2);
                break;
            case R.id.btn_3:
                //***********************单例模式的高效写法
                //但是在android不推荐，因为枚举占用的控件比整型大
                Boss boss = SomeThing.INSTANCE.getInstance();
                LogUtil.i(boss+"");
                boss = SomeThing.INSTANCE.getInstance();
                LogUtil.i(boss+"");
                for (SomeThing s : SomeThing.values()) {
                    LogUtil.i("s.toString()=  "+s.toString());
                    LogUtil.i("s.name()=  "+s.name());
                    LogUtil.i("s.ordinal()=  "+s.ordinal());
                }
                break;
        }
    }

    public enum SomeThing {
        /**
         * 1.从Java1.5开始支持;
         * 2.无偿提供序列化机制;
         * 3.绝对防止多次实例化，即使在面对复杂的序列化或者反射攻击的时候;
         */
        INSTANCE;
        private Boss instance;
        SomeThing() {
            instance = new Boss("zhangsan");
        }
        public Boss getInstance() {
            return instance;
        }
    }
}
