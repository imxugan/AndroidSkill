package test.cn.example.com.androidskill.model;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/21.
 */

public abstract class Leader {
    public Leader(String name){
        this.name = name;
    }
    //报销额度
    private int money;
    private String name;
    public Leader nextHandler;
    protected void handleRequest(){
        if(money>getLimitedMoney()){
            LogUtil.i("超过了"+name+"报销金额的上限("+getLimitedMoney()+")");
            if(null != nextHandler){
                nextHandler.handle(money);
            }
        }else {
            LogUtil.i(name+"直接审批报销"+money);
        }

//        if(money>limitMoney){
//            LogUtil.i("我去，你这花销也太大了吧，公司都被你弄黄了");
//        }else {
//            LogUtil.i("老板审批通过");
//        }
    }

    public void setMoney(int money){
        this.money = money;
    }

    protected int getLimitedMoney(){
        return 0;
    }


    public abstract void handle(int money);
}
