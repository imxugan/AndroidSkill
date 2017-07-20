package test.cn.example.com.androidskill.inter;

/**
 * Created by xgxg on 2017/7/20.
 */

public interface CompanyInter {
    void add(CompanyInter companyInter);
    void remove(CompanyInter companyInter);
    CompanyInter get(int position);
    void operator();
}
