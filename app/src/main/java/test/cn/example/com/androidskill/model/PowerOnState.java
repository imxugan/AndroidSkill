package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.StateInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/21.
 */

public class PowerOnState implements StateInter {
    @Override
    public void preChannel() {
        LogUtil.i("上一个频道");
    }

    @Override
    public void nextChanel() {
        LogUtil.i("下一个频道");
    }

    @Override
    public void turnUp() {
        LogUtil.i("调高音量");
    }

    @Override
    public void turnDown() {
        LogUtil.i("调低音量");
    }
}
