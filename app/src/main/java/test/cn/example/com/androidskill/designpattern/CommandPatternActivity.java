package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.inter.CommandInter;
import test.cn.example.com.androidskill.inter.CommandReceiver;
import test.cn.example.com.androidskill.inter.LeftCommand;
import test.cn.example.com.androidskill.inter.CommandInvoker;
import test.cn.example.com.androidskill.inter.RightCommand;

/**
 *命令行模式
 * Created by xgxg on 2017/7/21.
 */

public class CommandPatternActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_pattern);
        //接受者
        CommandReceiver commandReceiver = new CommandReceiver();
        //命令对象
        CommandInter leftCommandInter = new LeftCommand(commandReceiver);
        CommandInter rightCommandInter = new RightCommand(commandReceiver);
        //命令请求者
        CommandInvoker commandInvoker = new CommandInvoker();
        commandInvoker.setLeftCommand(leftCommandInter);
        //执行命令
        commandInvoker.executeLeftAction();
    }


}
