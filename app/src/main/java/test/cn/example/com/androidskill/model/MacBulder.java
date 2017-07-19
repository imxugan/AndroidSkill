package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/19.
 */

public class MacBulder implements BuilderInter {
    private ComputerAbs computerAbs = new MacBook();
    @Override
    public void setBoard(String board) {
        computerAbs.setBoard(board);
    }

    @Override
    public void setOS() {
        computerAbs.setOS();
    }

    @Override
    public void setDisplay(String display) {
        computerAbs.setDisplay(display);
    }

    @Override
    public ComputerAbs create() {
        return computerAbs;
    }

}
