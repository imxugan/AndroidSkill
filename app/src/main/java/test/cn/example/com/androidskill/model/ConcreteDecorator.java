package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.ComponentInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/20.
 */

public class ConcreteDecorator extends DecoratorAbs {
    public ConcreteDecorator(ComponentInter componentInter){
            super(componentInter);
    }
    @Override
    public void eat() {
        LogUtil.i("饭前洗手");
        componentInter.eat();
        LogUtil.i("饭后漱口");
    }
}
