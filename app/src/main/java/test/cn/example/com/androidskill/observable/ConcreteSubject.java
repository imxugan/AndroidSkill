package test.cn.example.com.androidskill.observable;

/**
 * 具体主题
 * Created by xugan on 2019/4/20.
 */

public class ConcreteSubject extends Subject {

    public void change(String state){
        notifyObserver(state);
    }
}
