package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CalculateStrategyInter;
import test.cn.example.com.util.LogUtil;

/**
 * 具体的公交车的计价策略
 * Created by xgxg on 2017/7/18.
 */

public class BusCalculateStrategy implements CalculateStrategyInter {
    @Override
    public void calculatePrice() {
        LogUtil.e("公交车的计价方式是2元");
    }
}
