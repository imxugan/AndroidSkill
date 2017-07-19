package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.PhoneInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/19.
 */

public class PhoneImpl implements PhoneInter {
    @Override
    public void open() {
        LogUtil.i("打开电话");
    }

    @Override
    public void dail() {
        LogUtil.i("打电话");
    }

    @Override
    public void close() {
        LogUtil.i("关闭电话");
    }
}
