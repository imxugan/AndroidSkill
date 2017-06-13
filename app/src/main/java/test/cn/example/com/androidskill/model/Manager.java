package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CallBackInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/6/13.
 */

public class Manager implements CallBackInter{
    public Manager(Employee employee){
        employee.work(this);
    }
    @Override
    public void callBack() {
        LogUtil.i("老板，我的工作做完了");
    }
}
