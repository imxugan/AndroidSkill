package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.ProductTruckInter;
import test.cn.example.com.util.LogUtil;

/**
 * 五菱宏光
 *
 * Created by xgxg on 2017/7/17.
 */

public class WuLin implements ProductTruckInter {
    @Override
    public void productName() {
        LogUtil.i("五菱宏光");
    }
}
