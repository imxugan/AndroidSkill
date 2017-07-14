package test.cn.example.com.androidskill.inter;

/**
 * 抽象观察者角色类
 * Created by xgxg on 2017/7/14.
 */

public interface ObserverInter {
    /**
     * 更新接口
     * @param state    更新的状态
     */
    void update(String state);
}
