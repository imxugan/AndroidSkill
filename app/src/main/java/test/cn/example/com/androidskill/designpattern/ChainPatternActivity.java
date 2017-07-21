package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.Boss;
import test.cn.example.com.androidskill.model.GroupLeader;
import test.cn.example.com.androidskill.model.Leader;
import test.cn.example.com.androidskill.model.ManagerLeader;
import test.cn.example.com.util.LogUtil;

/**
 * 责任链模式
 * Created by xgxg on 2017/7/21.
 */

public class ChainPatternActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain_pattern);
        Leader groupLeader = new GroupLeader("组长a");
        Leader managerLeader = new ManagerLeader("经理");
        Boss boss = new Boss("boss");
        groupLeader.nextHandler = managerLeader;
        managerLeader.nextHandler = boss;
        groupLeader.handle(888000);
        LogUtil.e("=========================================");
        groupLeader.handle(88800);
        LogUtil.e("=========================================");
        groupLeader.handle(8880);
        LogUtil.e("=========================================");
        groupLeader.handle(888);
        boss.handle(999);
    }


}
