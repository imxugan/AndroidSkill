package test.cn.example.com.androidskill.inter;

/**
 * Created by xgxg on 2017/7/24.
 */

public class LeftCommand implements CommandInter {
    private CommandReceiver game;
    public LeftCommand(CommandReceiver game){
        this.game = game;
    }
    @Override
    public void execute() {
        game.left();
    }
}
