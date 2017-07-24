package test.cn.example.com.androidskill.inter;

/**
 * Created by xgxg on 2017/7/24.
 */

public class RightCommand implements CommandInter {
    private CommandReceiver game;
    public RightCommand(CommandReceiver game){
        this.game = game;
    }
    @Override
    public void execute() {
        game.right();
    }
}
