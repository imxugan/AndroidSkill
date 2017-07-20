package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.inter.CompanyInter;
import test.cn.example.com.androidskill.model.HRDepartment;
import test.cn.example.com.androidskill.model.TechnicDepartment;
import test.cn.example.com.androidskill.model.WenKangCompany;
import test.cn.example.com.androidskill.model.YunYiCompany;

/**
 * 组合模式
 * Created by xgxg on 2017/7/20.
 */

public class CompositePatternActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_pattern);
        //首先创建部门
        CompanyInter technicDepartment = new TechnicDepartment();
        technicDepartment.operator();
        CompanyInter hrDepartment = new HRDepartment();
        hrDepartment.operator();
//        hrDepartment.add(technicDepartment);
        CompanyInter yunYiCompany =  new YunYiCompany();
        yunYiCompany.add(technicDepartment);
        yunYiCompany.operator();
        CompanyInter wenKangCompany = new WenKangCompany();
        wenKangCompany.add(yunYiCompany);
        wenKangCompany.add(hrDepartment);
        wenKangCompany.operator();
    }

}
