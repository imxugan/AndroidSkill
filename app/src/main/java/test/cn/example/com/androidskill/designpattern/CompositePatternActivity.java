package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.inter.CompanyInter;
import test.cn.example.com.androidskill.model.ALiCompany;
import test.cn.example.com.androidskill.model.BigDataDeparment;
import test.cn.example.com.androidskill.model.EntertainmentDeparment;
import test.cn.example.com.androidskill.model.HRDepartment;
import test.cn.example.com.androidskill.model.TaoBaoCompany;
import test.cn.example.com.androidskill.model.TechnicDepartment;
import test.cn.example.com.androidskill.model.WenKangCompany;
import test.cn.example.com.androidskill.model.YunYiCompany;
import test.cn.example.com.util.LogUtil;

/**
 * 组合模式
 * Created by xgxg on 2017/7/20.
 */

public class CompositePatternActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_pattern);
        initView();
    }

    private void initView() {
        Button transparent_composite = (Button) findViewById(R.id.transparent_composite);
        transparent_composite.setOnClickListener(this);
        Button safe_composite = (Button) findViewById(R.id.safe_composite);
        safe_composite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.transparent_composite:
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
                break;
            case R.id.safe_composite:
//                XiaoMiCompany xiaoMi = new XiaoMiCompany();
//                HongMiCompany hongMi = new HongMiCompany();
//                TechnicDepartment2 te2 = new TechnicDepartment2();
//                HRDepartment2 hr2= new HRDepartment2();
//                hongMi.add(te2);
//                hongMi.operator();
//                xiaoMi.add(hongMi);
//                xiaoMi.add(hr2);
//                xiaoMi.operator();
                LogUtil.i("==========================================");
                ALiCompany aLi = new ALiCompany("阿里集团");
                TaoBaoCompany taoBao = new TaoBaoCompany("淘宝公司");
                BigDataDeparment bigDataDepart = new BigDataDeparment("大数据部");
                EntertainmentDeparment entertainmentDepart = new EntertainmentDeparment("娱乐部");
                bigDataDepart.operator();
                entertainmentDepart.operator();
                taoBao.add(bigDataDepart);
                taoBao.operator();
                aLi.add(taoBao);
                aLi.add(entertainmentDepart);
                aLi.operator();
                break;
            default:
                break;
        }
    }
}
