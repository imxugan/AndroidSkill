package test.cn.example.com.androidskill.model;

/**
 * 抽象中介者
 * Created by xgxg on 2017/7/25.
 */

public abstract class MediatorAbs {
    //某个同事执行了一个action后，通知其他同事
    public abstract void colleagueChange(ColleagueAbs colleagueAbs);
}
