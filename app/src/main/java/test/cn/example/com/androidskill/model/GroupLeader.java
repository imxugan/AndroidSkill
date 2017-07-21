package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/7/21.
 */

public class GroupLeader extends Leader {
    public GroupLeader(String name) {
        super(name);
    }

    @Override
    protected int getLimitedMoney() {
        return 1000;
    }

    @Override
    public void handle(int money) {
        setMoney(money);
        handleRequest();
    }
}
