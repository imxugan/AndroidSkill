package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/21.
 */

public class ManagerLeader extends Leader {
    public ManagerLeader(String name,int money) {
        super(name,money);
    }

    @Override
    public void handle(int money) {
        setMoney(money);
        handleRequest();
    }

}
