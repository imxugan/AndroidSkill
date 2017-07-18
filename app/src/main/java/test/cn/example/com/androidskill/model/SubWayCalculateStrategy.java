package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CalculateStrategyInter;
import test.cn.example.com.util.LogUtil;

/**
 * 地铁的计价策略
 * Created by xgxg on 2017/7/18.
 */

public class SubWayCalculateStrategy implements CalculateStrategyInter {
    @Override
    public void calculatePrice() {
        LogUtil.i("地铁的计价是3元起步，7元封顶");
    }
}
