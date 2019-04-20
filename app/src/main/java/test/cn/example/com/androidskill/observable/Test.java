package test.cn.example.com.androidskill.observable;

/**
 * Created by xugan on 2019/4/20.
 */

public class Test {
    public static void main(String[] args){
        Observer observer = new ConcreteObserver();
        ConcreteSubject subject = new ConcreteSubject();
        subject.attach(observer);
        subject.change("老板来巡视工位了");
    }
}
