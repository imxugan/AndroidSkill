package test.cn.example.com.androidskill.observable;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象主题
 * Created by xugan on 2019/4/20.
 */

public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer){
        if(!observers.contains(observer)){
            observers.add(observer);
            System.out.println("添加观察者");
        }
    }

    public void detach(Observer observer){
        if(observers.contains(observer)){
            observers.remove(observer);
            System.out.println("删除观察者");
        }
    }


    protected void notifyObserver(String observableState){
        for(Observer observer:observers){
            observer.update(observableState);
        }
    }
}
