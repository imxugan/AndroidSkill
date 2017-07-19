package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/19.
 */

public class Director2 {
    private AcerBuilder builderInter ;
    public Director2(AcerBuilder builderInter){
        this.builderInter = builderInter;
    }

    public void construct(String board,String diaplay){
        builderInter.setBoard(board);
        builderInter.setOS();
        builderInter.setDisplay(diaplay);

    }

    public ComputerAbs getProduct(){
        return builderInter.create();
    }
}
