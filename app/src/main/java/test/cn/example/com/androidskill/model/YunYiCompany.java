package test.cn.example.com.androidskill.model;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.inter.CompanyInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/20.
 */

public class YunYiCompany implements CompanyInter {
    private List<CompanyInter> companyInters = new ArrayList<CompanyInter>();
    @Override
    public void add(CompanyInter companyInter) {
        companyInters.add(companyInter);
    }

    @Override
    public void remove(CompanyInter companyInter) {
        companyInters.remove(companyInter);
    }

    @Override
    public CompanyInter get(int position) {
        return companyInters.get(position);
    }

    @Override
    public void operator() {
        LogUtil.i("云医子公司");
        for (CompanyInter inter:companyInters) {
            inter.operator();
        }
    }
}
