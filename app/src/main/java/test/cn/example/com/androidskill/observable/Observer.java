package test.cn.example.com.androidskill.observable;

/**
 * 抽象观察者
 * Created by xugan on 2019/4/20.
 */

public interface Observer {
    public abstract void update(String obserableState);
}
