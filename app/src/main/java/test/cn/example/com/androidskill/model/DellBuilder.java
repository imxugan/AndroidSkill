package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/19.
 */

public class DellBuilder {
    private ComputerAbs dellComputer = new DellComputer();
    public void construct(String board,String display){
        dellComputer.setBoard(board);
        dellComputer.setOS();
        dellComputer.setDisplay(display);
    }

    public ComputerAbs create(){
        return dellComputer;
    }
}
