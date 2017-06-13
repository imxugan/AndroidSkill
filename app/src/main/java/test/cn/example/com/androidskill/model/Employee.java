package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CallBackInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/6/13.
 */

public class Employee {
    public void work(CallBackInter callBackInter){
        LogUtil.i("工作，工作，工作...");
        LogUtil.i("半个小时后，工作做完了");
        callBackInter.callBack();
    }
}
