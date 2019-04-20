package test.cn.example.com.androidskill.observable;

/**
 * 具体观察者
 * Created by xugan on 2019/4/20.
 */

public class ConcreteObserver implements Observer {
    @Override
    public void update(String newObservableState) {
        System.out.println("状态改变了---->>"+newObservableState);
    }
}
