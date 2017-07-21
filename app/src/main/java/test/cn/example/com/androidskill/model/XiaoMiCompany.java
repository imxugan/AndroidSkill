package test.cn.example.com.androidskill.model;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.inter.CompanyInter2;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/20.
 */

public class XiaoMiCompany implements CompanyInter2 {
    private List<CompanyInter2> companyInter2s = new ArrayList<CompanyInter2>();
    public void add(CompanyInter2 companyInter){
        companyInter2s.add(companyInter);
    }
    public void remove(CompanyInter2 companyInter){
        companyInter2s.remove(companyInter);
    }
    public CompanyInter2 getChild(int position){
        return companyInter2s.get(position);
    }
    @Override
    public void operator() {
        LogUtil.i("小米公司");
        for (CompanyInter2 inter2:companyInter2s) {
            inter2.operator();
        }
    }
}
