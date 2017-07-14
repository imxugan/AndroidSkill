package test.cn.example.com.androidskill.model;

/**
 * 具体主题角色类
 * Created by xgxg on 2017/7/14.
 */

public class ConcreteSubject extends Subject {
    private String subjectState ;

    public void setSubjectState(String state){
        this.subjectState = state;
    }

    public String getSubjectState(){
        return subjectState;
    }

    public void changeState(String newState){
        this.subjectState = newState;
        notifyObservers(subjectState);
    }

}
