package test.cn.example.com.androidskill.model;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/20.
 */

public class EntertainmentDeparment extends CompanyAbs {
    public String name;
    public EntertainmentDeparment(String name) {
        this.name = name;
    }

    @Override
    public void operator() {
//        LogUtil.i(""+((super.name == null)?"":null)+":"+this.name);
        LogUtil.i(name);
    }
}
