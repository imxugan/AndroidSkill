package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CalculateStrategyInter;

/**
 * 使用策略的环境
 * Created by xgxg on 2017/7/18.
 */

public class TranficCalculate {
    private CalculateStrategyInter mCalculateStrategy;
    public CalculateStrategyInter setCalculateStrategy(CalculateStrategyInter calculateStrategyInter){
        this.mCalculateStrategy = calculateStrategyInter;
        return this.mCalculateStrategy;
    }

    public void calcPrice(){
        mCalculateStrategy.calculatePrice();
    }
}
