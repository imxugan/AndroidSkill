package test.cn.example.com.androidskill.inter;

/**
 * 命令请求者
 * Created by xgxg on 2017/7/24.
 */

public class CommandInvoker {
    private CommandInter leftCommand;
    public void setLeftCommand(CommandInter leftCommand){
        this.leftCommand = leftCommand;
    }

    public void executeLeftAction(){
        leftCommand.execute();
    }
}
