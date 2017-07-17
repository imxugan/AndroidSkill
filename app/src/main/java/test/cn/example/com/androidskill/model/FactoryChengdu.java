package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.FactoryAbstract;
import test.cn.example.com.androidskill.inter.ProductBusInter;
import test.cn.example.com.androidskill.inter.ProductCarInter;
import test.cn.example.com.androidskill.inter.ProductTruckInter;

/**
 * 成都工厂，生产小轿车，货车，公交车
 * Created by xgxg on 2017/7/17.
 */

public class FactoryChengdu implements FactoryAbstract {
    @Override
    public ProductCarInter createProductCar() {
        return new ChangAnCar();
    }

    @Override
    public ProductTruckInter createProductTruck() {
        return new WuLin();
    }

    @Override
    public ProductBusInter createProductBus() {
        return new YangzijiangBus();
    }
}
