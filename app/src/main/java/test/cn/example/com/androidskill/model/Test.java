package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/8/18.
 */

public class Test {
    private Test(){}

    private static Test t = new Test();

    public static Test getInstance(){
        return t;
    }
}
