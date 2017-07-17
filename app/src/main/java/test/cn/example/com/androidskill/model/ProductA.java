package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.ProductInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/17.
 */

public class ProductA implements ProductInter {
    @Override
    public void productName() {
        LogUtil.i("生产A产品");
    }
}
