package test.cn.example.com.androidskill.model;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/12.
 */

public class Resume2 implements Cloneable{
    private String name2;
    private int age2;
    private WorkExperience2 workExperience2;
    public void setPersonInfo2(String name2,int age2,String company){
        this.name2 = name2;
        this.age2 = age2;
        workExperience2 = new WorkExperience2();
        workExperience2.setCompany(company);
    }

    public void display(){
        LogUtil.i("name2="+name2+"      age2="+age2);
        LogUtil.i("this.workExperience2="+this.workExperience2+"    "+this.workExperience2.getCompany());
    }

    public Object Clone() throws CloneNotSupportedException {
        Resume2 resume2 = (Resume2) super.clone();
        resume2.workExperience2 = (WorkExperience2) workExperience2.Clone();
        return resume2;
    }
}
