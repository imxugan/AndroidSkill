package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CompanyInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/20.
 */

public class HRDepartment implements CompanyInter {
    @Override
    public void add(CompanyInter companyInter) {
        throw new IllegalArgumentException("不支持该方法");
    }

    @Override
    public void remove(CompanyInter companyInter) {
        throw new IllegalArgumentException("不支持该方法");
    }

    @Override
    public CompanyInter get(int position) {
        throw new IllegalArgumentException("不支持该方法");
    }

    @Override
    public void operator() {
        LogUtil.i("人力资源部");
    }
}
