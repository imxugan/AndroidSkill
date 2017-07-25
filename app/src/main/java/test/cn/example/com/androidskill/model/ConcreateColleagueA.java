package test.cn.example.com.androidskill.model;

import test.cn.example.com.util.LogUtil;

/**
 * 具体的同事A
 * Created by xgxg on 2017/7/25.
 */

public class ConcreateColleagueA extends ColleagueAbs {
    public ConcreateColleagueA(MediatorAbs mediatorAbs) {
        super(mediatorAbs);
    }

    @Override
    public void action() {
        LogUtil.i("ConcreateColleagueA is action");
    }
}
