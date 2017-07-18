package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.inter.CalculateStrategyInter;
import test.cn.example.com.androidskill.model.BikeCalculateStrategy;
import test.cn.example.com.androidskill.model.BusCalculateStrategy;
import test.cn.example.com.androidskill.model.SubWayCalculateStrategy;
import test.cn.example.com.androidskill.model.TranficCalculate;

/**
 * 策略模式
 * Created by xgxg on 2017/7/18.
 */

public class StrategyPatternActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_pattern);
        TranficCalculate tranficCalculate = new TranficCalculate();
        CalculateStrategyInter busCalculateStrategy =  tranficCalculate.setCalculateStrategy(new BusCalculateStrategy());
        busCalculateStrategy.calculatePrice();
        CalculateStrategyInter subWayCalculateStrategy =  tranficCalculate.setCalculateStrategy(new SubWayCalculateStrategy());
        subWayCalculateStrategy.calculatePrice();
        CalculateStrategyInter bikeCalculateStrategy =  tranficCalculate.setCalculateStrategy(new BikeCalculateStrategy());
        bikeCalculateStrategy.calculatePrice();

    }
}
