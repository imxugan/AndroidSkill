package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.ProductBusInter;
import test.cn.example.com.util.LogUtil;

/**
 * 扬子江客车
 * Created by xgxg on 2017/7/17.
 */

public class YangzijiangBus implements ProductBusInter {
    @Override
    public void productName() {
        LogUtil.e("扬子江客车");
    }
}
