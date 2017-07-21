package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.TvController;

/**
 * 状态模式
 * Created by xgxg on 2017/7/21.
 */

public class StatePatternActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_pattern);
        TvController tvController = new TvController();
        tvController.turnOn();
        tvController.preChanel();
        tvController.nextChanel();
        tvController.turnUp();
        tvController.turnDown();
        tvController.turnOff();
        tvController.preChanel();
        tvController.nextChanel();
        tvController.turnUp();
        tvController.turnDown();

    }


}
