package test.cn.example.com.androidskill.model;

import java.util.ArrayList;

import test.cn.example.com.androidskill.inter.ObserverInter;
import test.cn.example.com.util.LogUtil;

/**
 * 抽象主题角色类
 * Created by xgxg on 2017/7/14.
 */

public abstract class Subject {
    /**
     * 用来保存注册的观察者对象
     */
    private ArrayList<ObserverInter> observers = new ArrayList<ObserverInter>();
    /**
     * 注册观察者对象
     * @param observer    观察者对象
     */
    public void attach(ObserverInter observer){
        observers.add(observer);
        if(observer instanceof ConcreteObserver){
            ConcreteObserver ob = (ConcreteObserver)observer;
            LogUtil.i("添加观察者："+ob.getName());
        }
    }
    /**
     * 删除观察者对象
     * @param observer    观察者对象
     */
    public void detach(ObserverInter observer){
        if(null != observer && observers.contains(observer)){
            observers.remove(observer);
            if(observer instanceof ConcreteObserver){
                ConcreteObserver ob = (ConcreteObserver)observer;
                LogUtil.i("被移除的观察者："+ob.getName());
            }
        }
    }
    /**
     * 通知所有注册的观察者对象
     */
    protected void notifyObservers(String newState){
        for(ObserverInter ob : observers){
            ob.update(newState);
        }
    }
}
