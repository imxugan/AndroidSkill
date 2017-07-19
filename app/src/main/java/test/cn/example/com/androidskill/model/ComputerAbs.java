package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/19.
 */

public abstract class ComputerAbs {
    public String board,os,display;
    public void setBoard(String board){
        this.board = board;
    }
    public abstract  void setOS();
    public void setDisplay(String display){
        this.display = display;
    }

    @Override
    public String toString() {
        return board+"---"+os+"---"+display;
    }
}
