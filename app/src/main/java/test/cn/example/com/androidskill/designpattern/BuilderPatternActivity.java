package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.AcerBuilder;
import test.cn.example.com.androidskill.model.BuilderInter;
import test.cn.example.com.androidskill.model.ComputerAbs;
import test.cn.example.com.androidskill.model.DellBuilder;
import test.cn.example.com.androidskill.model.Director;
import test.cn.example.com.androidskill.model.Director2;
import test.cn.example.com.androidskill.model.HPComputer;
import test.cn.example.com.androidskill.model.MacBook;
import test.cn.example.com.androidskill.model.MacBulder;
import test.cn.example.com.util.LogUtil;

/**
 * builder模式
 * Created by xgxg on 2017/7/19.
 */

public class BuilderPatternActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder_pattern);
        initView();

    }

    private void initView() {
        Button all = (Button) findViewById(R.id.all);
        all.setOnClickListener(this);
        Button except_buildrInter = (Button) findViewById(R.id.except_buildrInter);
        except_buildrInter.setOnClickListener(this);
        Button except_director = (Button) findViewById(R.id.except_director);
        except_director.setOnClickListener(this);
        Button except_director2 = (Button) findViewById(R.id.except_director2);
        except_director2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all:
                //首先创建具体构建者
                BuilderInter macBuilder = new MacBulder();
                //创建指导者
                Director director = new Director(macBuilder);
                director.construct("华硕主板","三星led显示器");
                MacBook computer =  (MacBook) director.getProduct();
                LogUtil.i(computer.toString());
                break;
            case R.id.except_buildrInter:
                //首先创建具体构建者
                AcerBuilder acerBuilder = new AcerBuilder();
                //创建指导者
                Director2 d = new Director2(acerBuilder);
                d.construct("鸿基主板","智冠显示器");
                ComputerAbs acer = d.getProduct();
                LogUtil.i(acer.toString());
                break;
            case R.id.except_director:
                //首先创建具体构建者
                DellBuilder dellBuilder = new DellBuilder();
                dellBuilder.construct("戴尔主板","戴尔显示器");
                ComputerAbs dellComputer = dellBuilder.create();
                LogUtil.i(dellComputer.toString());
                break;
            case R.id.except_director2:
                HPComputer hp = new HPComputer.Builder().board("惠普主板").display("惠普显示器").os().create();
                LogUtil.i(hp.toString());
                break;
            default:
                break;
        }
    }
}
