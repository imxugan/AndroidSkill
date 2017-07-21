package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.StateInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/21.
 */

public class PowerOffState implements StateInter {
    @Override
    public void preChannel() {
        LogUtil.i("处于关机状态，操作无效");
    }

    @Override
    public void nextChanel() {
        LogUtil.i("处于关机状态，操作无效");
    }

    @Override
    public void turnUp() {
        LogUtil.i("处于关机状态，操作无效");
    }

    @Override
    public void turnDown() {
        LogUtil.i("处于关机状态，操作无效");
    }
}
