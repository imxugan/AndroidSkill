package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.ComponentInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/20.
 */

public class ConcreteComponent implements ComponentInter {
    @Override
    public void eat() {
        LogUtil.i("吃饭");
    }
}
