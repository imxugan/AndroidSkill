package test.cn.example.com.androidskill.model;


import test.cn.example.com.androidskill.inter.ObserverInter;
import test.cn.example.com.util.LogUtil;

/**
 * 具体观察者角色类
 * Created by xgxg on 2017/7/14.
 */

public class ConcreteObserver implements ObserverInter {
    private String observerState;
    private String name;
    public String getName(){
        return this.name;
    }
    public ConcreteObserver(String name,String state){
        this.observerState = state;
        this.name = name;
        LogUtil.i("观察着："+name+"，当前的转态是"+state);
    }

    @Override
    public void update(String newState) {
        /**
         * 更新观察者的状态，使其与被观察的主题的状态保持一致
         */
        observerState = newState;
        LogUtil.i("观察着："+name+"，当前的转态是"+observerState);
    }
}
