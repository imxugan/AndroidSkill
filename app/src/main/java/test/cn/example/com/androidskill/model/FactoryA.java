package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.FactoryInter;
import test.cn.example.com.androidskill.inter.ProductInter;

/**
 * Created by xgxg on 2017/7/17.
 */

public class FactoryA implements FactoryInter {

    @Override
    public ProductInter create() {
        return new ProductA();
    }
}
