package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/21.
 */

public class Boss extends Leader {
    public Boss(String name,int money) {
        super(name,money);
    }

    @Override
    public void handle(int money) {
        setMoney(money);
        handleRequest();
    }

}
