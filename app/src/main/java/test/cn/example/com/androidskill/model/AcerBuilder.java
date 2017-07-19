package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/19.
 */

public class AcerBuilder {
    private AcerComputer computer = new AcerComputer();
    public void setBoard(String board){
        computer.setBoard(board);
    }

    public void setOS(){
        computer.setOS();
    }

    public void setDisplay(String display){
        computer.setDisplay(display);
    }

    public ComputerAbs create(){
        return computer;
    }
}
