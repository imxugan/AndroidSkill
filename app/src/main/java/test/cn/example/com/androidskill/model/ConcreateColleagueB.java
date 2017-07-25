package test.cn.example.com.androidskill.model;

import test.cn.example.com.util.LogUtil;

/**
 * 具体的同事B
 * Created by xgxg on 2017/7/25.
 */

public class ConcreateColleagueB extends ColleagueAbs {
    public ConcreateColleagueB(MediatorAbs mediatorAbs) {
        super(mediatorAbs);
    }

    @Override
    public void action() {
        LogUtil.i("ConcreateColleagueB is action");
    }
}
