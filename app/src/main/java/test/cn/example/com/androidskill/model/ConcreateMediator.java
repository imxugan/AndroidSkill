package test.cn.example.com.androidskill.model;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/25.
 */

public class ConcreateMediator extends MediatorAbs {
    private ConcreateColleagueA concreateColleagueA;
    private ConcreateColleagueB concreateColleagueB;
    @Override
    public void colleagueChange(ColleagueAbs colleagueAbs) {
        if(colleagueAbs != null){
            if(colleagueAbs == concreateColleagueA){
                LogUtil.i("concreateColleagueA is change,通知其他同事响应");
                concreateColleagueB.action();
            }else if(colleagueAbs == concreateColleagueB){
                LogUtil.i("concreateColleagueB is change,通知其他同事响应");
                concreateColleagueA.action();
            }
        }
    }

    public void setColleague(){
        concreateColleagueA = new ConcreateColleagueA(this);
        concreateColleagueB = new ConcreateColleagueB(this);
    }

    public ColleagueAbs getColleagueA(){
        return concreateColleagueA;
    }
    public ColleagueAbs getColleagueB(){
        return concreateColleagueB;
    }
}
