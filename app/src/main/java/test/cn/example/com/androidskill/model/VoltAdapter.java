package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.Target;

/**
 * Created by xgxg on 2017/7/14.
 */

public class VoltAdapter implements Target{
    private Adaptee adaptee;
    public VoltAdapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }
    @Override
    public int getVolt5() {
        int targetNeedVlot = 0;
        targetNeedVlot = this.adaptee.getVolt220()/44;//这里是模拟如何将220伏的电压转变成5伏
//        return 5;
        return targetNeedVlot;
    }
}
