package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.ProductCarInter;
import test.cn.example.com.util.LogUtil;

/**
 * 长安轿车
 * Created by xgxg on 2017/7/17.
 */

public class ChangAnCar implements ProductCarInter {
    @Override
    public void productName() {
        LogUtil.e("长安铃木");
    }
}
