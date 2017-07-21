package test.cn.example.com.androidskill.model;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/20.
 */

public class ALiCompany extends CompanyAbs {
    public String name;
    public ALiCompany(String name) {
        this.name = name;
    }

    private List<CompanyAbs> companyAbsList = new ArrayList<CompanyAbs>();
    public void add(CompanyAbs companyAbs){
        companyAbsList.add(companyAbs);
    }
    public void remove(CompanyAbs companyAbs){
        companyAbsList.remove(companyAbs);
    }
    public CompanyAbs getChild(int position){
        return companyAbsList.get(position);
    }

    @Override
    public void operator() {
        LogUtil.i(name);
        for (CompanyAbs comAbs:companyAbsList) {
            comAbs.operator();
//            if(comAbs instanceof BigDataDeparment){
//                LogUtil.i(name+":"+((BigDataDeparment)comAbs).name);
//            }else if(comAbs instanceof EntertainmentDeparment){
//                LogUtil.i(name+":"+((EntertainmentDeparment)comAbs).name);
//            }else if(comAbs instanceof TaoBaoCompany){
//                LogUtil.i(name+":"+((TaoBaoCompany)comAbs).name);
//            }

        }
    }
}
