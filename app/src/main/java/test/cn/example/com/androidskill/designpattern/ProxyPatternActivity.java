package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.inter.IUserDao;
import test.cn.example.com.androidskill.model.UserDao;
import test.cn.example.com.androidskill.model.UserDaoProxy;

/**
 * 代理模式
 * Created by xgxg on 2017/7/18.
 */

public class ProxyPatternActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_pattern);
        initView();

    }

    private void initView() {
        Button proxy_static = (Button) findViewById(R.id.proxy_static);
        proxy_static.setOnClickListener(this);
        Button proxy_dynamic = (Button) findViewById(R.id.proxy_dynamic);
        proxy_dynamic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.proxy_static:
                //新建真实对象
                IUserDao userDao = new UserDao();
                //通过真实主题对象构造一个代理对象
                IUserDao userDaoProxy = new UserDaoProxy(userDao);
                userDaoProxy.save();
                break;
            case R.id.proxy_dynamic:
                break;
            default:
                break;
        }
    }
}
