package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.ComponentInter;

/**
 * Created by xgxg on 2017/7/20.
 */

public abstract class DecoratorAbs implements ComponentInter {
    protected ComponentInter componentInter;
    public DecoratorAbs(ComponentInter componentInter){
        this.componentInter = componentInter;
    }


}
