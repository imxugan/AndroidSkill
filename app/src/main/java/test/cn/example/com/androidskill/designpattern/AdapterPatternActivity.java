package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.inter.Target;
import test.cn.example.com.androidskill.model.Adaptee;
import test.cn.example.com.androidskill.model.VoltAdapter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/14.
 * 适配器模式
 */

public class AdapterPatternActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_pattern);
        initView();
        //只是演示了对象适配器
        Adaptee adaptee = new Adaptee();
        LogUtil.i("原电压是："+adaptee.getVolt220());
        Target t = new VoltAdapter(adaptee);
        LogUtil.i("适配后的电压是："+t.getVolt5());
    }

    private void initView() {
       TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("适配器模式，结果，看log日志");

    }
}
