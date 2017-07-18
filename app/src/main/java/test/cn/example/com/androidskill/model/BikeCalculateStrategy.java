package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CalculateStrategyInter;
import test.cn.example.com.util.LogUtil;

/**
 * 共享单车的计价策略
 * Created by xgxg on 2017/7/18.
 */

public class BikeCalculateStrategy implements CalculateStrategyInter {
    @Override
    public void calculatePrice() {
        LogUtil.i("共享单车的计价策略是0.5元起步，超过5公里，另加钱");
    }
}
