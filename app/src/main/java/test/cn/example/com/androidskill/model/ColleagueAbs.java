package test.cn.example.com.androidskill.model;

/**
 * 抽象同事类
 * Created by xgxg on 2017/7/25.
 */

public abstract class ColleagueAbs {

    private MediatorAbs mediatorAbs;
    public ColleagueAbs(MediatorAbs mediatorAbs){
        this.mediatorAbs = mediatorAbs;
    }

    public void change(){
        //调用中介者的colleagueChange方法来通知其他同事
        mediatorAbs.colleagueChange(this);
    }

    //同事的抽象行为
    protected abstract void action();
}
