package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/21.
 */

public class Boss extends Leader {
    public Boss(String name) {
        super(name);
    }

    @Override
    protected int getLimitedMoney() {
        return 100000;
    }

    @Override
    public void handle(int money) {
        setMoney(money);
        handleRequest();
    }

}
