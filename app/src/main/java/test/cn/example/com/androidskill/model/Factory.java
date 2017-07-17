package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.ProductInter;

/**
 * 工厂类
 * 简单工厂模式
 * Created by xgxg on 2017/7/17.
 */

public class Factory {
    public static ProductInter create(String productName){
        switch (productName){
            case "A":
                return new ProductA();
            case "B":
                return new ProductB();
            default:
                return null;
        }
    }
}
