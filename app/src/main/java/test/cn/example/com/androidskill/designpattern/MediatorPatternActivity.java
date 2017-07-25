package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.ColleagueAbs;
import test.cn.example.com.androidskill.model.ConcreateMediator;

/**
 *中介者模式
 * Created by xgxg on 2017/7/25.
 */

public class MediatorPatternActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediator_pattern);
        ConcreateMediator mediator = new ConcreateMediator();
        mediator.setColleague();
        ColleagueAbs concreateColleagueA = mediator.getColleagueA();
        ColleagueAbs concreateColleagueB = mediator.getColleagueB();
        concreateColleagueA.change();
        concreateColleagueB.change();
    }


}
