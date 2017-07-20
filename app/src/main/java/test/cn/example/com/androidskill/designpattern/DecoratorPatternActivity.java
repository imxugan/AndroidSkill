package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.ConcreteComponent;
import test.cn.example.com.androidskill.model.ConcreteDecorator;
import test.cn.example.com.androidskill.model.DecoratorAbs;
import test.cn.example.com.util.LogUtil;

/**
 * 装饰模式
 * Created by xgxg on 2017/7/20.
 */

public class DecoratorPatternActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decorator_pattern);
        ConcreteComponent concreteComponent = new ConcreteComponent();
        concreteComponent.eat();
        LogUtil.i("装饰后的吃饭");
        DecoratorAbs decorator = new ConcreteDecorator(concreteComponent);
        decorator.eat();
    }

}
