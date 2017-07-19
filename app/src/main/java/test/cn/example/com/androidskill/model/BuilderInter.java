package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/19.
 */

public interface BuilderInter {
    void setBoard(String board);
    void setOS();
    void setDisplay(String display);

    ComputerAbs create();
}
