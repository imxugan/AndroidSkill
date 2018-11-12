package test.cn.example.com.androidskill.model;

/**
 * Created by xugan on 2018/11/12.
 */

public class Person {
    private Person(){}//私有构造函数,防止外部直接new对象
    private static class PersonHolder{
        private static Person instance = new Person();
    }
    public static Person getInstance(){
        return PersonHolder.instance;
    }
}
