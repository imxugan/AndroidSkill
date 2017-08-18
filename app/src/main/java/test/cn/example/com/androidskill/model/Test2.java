package test.cn.example.com.androidskill.model;

/**
 * Created by xgxg on 2017/8/18.
 */

public class Test2 {
    private Test2(){}

    private static Test2 t = null;

    public static Test2 getInstance(){
        if(t == null){
            synchronized (Test2.class){
                if(t == null){
                    t = new Test2();
                }
            }
        }
        return t;
    }
}
