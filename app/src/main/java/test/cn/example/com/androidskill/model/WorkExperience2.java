package test.cn.example.com.androidskill.model;

/**
 * Created by xugan on 2018/11/12.
 */

public class WorkExperience2 implements Cloneable{
    private String company;//工作的公司
    private String duration;//工作年限
    public void setCompany(String company){
        this.company = company;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public String getCompany(){
        return company;
    }

    public String getDuration(){
        return duration;
    }

    public Object Clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
