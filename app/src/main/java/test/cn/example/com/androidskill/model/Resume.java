package test.cn.example.com.androidskill.model;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/12.
 */

public class Resume implements Cloneable{
    private String name;
    private int age;
    public WorkExperience workExperience;
    public void setPersonInfo(String name,int age,String company){
        this.name = name;
        this.age = age;
        workExperience = new WorkExperience();
        workExperience.setCompany(company);
    }

    public void display(){
        LogUtil.i("name="+this.name+"    age="+this.age);
        LogUtil.i(this.workExperience+"    "+this.workExperience.getCompany());
    }

    public Object Clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
