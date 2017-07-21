package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.StateInter;
import test.cn.example.com.util.LogUtil;

/**
 * 遥控器
 * Created by xgxg on 2017/7/21.
 */

public class TvController {
    private StateInter stateInter;
    private void setState(StateInter stateInter){
        this.stateInter = stateInter;
    }

    public void turnOn(){
        LogUtil.i("开机");
        setState(new PowerOnState());
    }

    public void turnOff(){
        LogUtil.i("关机");
        setState(new PowerOffState());
    }

    public void preChanel(){
        stateInter.preChannel();
    }

    public void nextChanel(){
        stateInter.nextChanel();
    }

    public void turnUp(){
        stateInter.turnUp();
    }

    public void turnDown(){
        stateInter.turnDown();
    }
}
