package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/19.
 */

public class AcerComputer extends ComputerAbs{
    public void setOS(){
        this.os ="window 10";
    }

    @Override
    public String toString() {
        return board+"---"+os+"---"+display;
    }
}
