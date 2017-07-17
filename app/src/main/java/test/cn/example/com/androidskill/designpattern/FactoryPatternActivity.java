package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.inter.FactoryAbstract;
import test.cn.example.com.androidskill.inter.FactoryInter;
import test.cn.example.com.androidskill.inter.ProductBusInter;
import test.cn.example.com.androidskill.inter.ProductCarInter;
import test.cn.example.com.androidskill.inter.ProductInter;
import test.cn.example.com.androidskill.inter.ProductTruckInter;
import test.cn.example.com.androidskill.model.Factory;
import test.cn.example.com.androidskill.model.FactoryA;
import test.cn.example.com.androidskill.model.FactoryChengdu;
import test.cn.example.com.util.LogUtil;

/**
 * 工厂模式(简单工厂，工厂方法，抽象工厂)
 * Created by xgxg on 2017/7/17.
 */

public class FactoryPatternActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_pattern);
        initView();

    }

    private void initView() {
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("工厂模式，通过log日志，查看结果");
        Button simple = (Button) findViewById(R.id.simple);
        simple.setOnClickListener(this);
        Button factory_method = (Button) findViewById(R.id.factory_method);
        factory_method.setOnClickListener(this);
        Button abstract_factory = (Button) findViewById(R.id.abstract_factory);
        abstract_factory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.simple:
                //简单工厂模式的工厂类一般是使用静态方法，
                // 通过接收的参数的不同来返回不同的对象实例。
                //不修改代码的话，是无法扩展的。
                LogUtil.i("简单工厂");
                ProductInter productA = Factory.create("A");
                productA.productName();
                ProductInter productB = Factory.create("B");
                productB.productName();
                break;
            case R.id.factory_method:
//                工厂方法是针 对每一种产品提供一个工厂类 。
//                通过不同的工厂实例来创建不同的产品实例。
//                在同一等级结构中， 支持增加任意产品 。
                LogUtil.i("工厂方法");
                FactoryInter factoryA = new FactoryA();
                ProductInter proA = factoryA.create();
                proA.productName();
                FactoryInter factoryB = new FactoryA();
                ProductInter proB = factoryB.create();
                proB.productName();
                break;
            case R.id.abstract_factory:
//                抽象工厂是应对产品族概念的。比如说，每个汽车公司可能要同时
//                生产轿车，货车，客车，那么每一个工厂都要有创建轿车，货车和客车的方法。
//                应对产品族概念而生，增加新的产品线很容易，但是无法增加新的产品。
//                和工厂方法的区别是，抽象工厂往往有多种方法，可以生产多种产品，即产品簇。
                LogUtil.i("抽象工厂");
                FactoryAbstract chengduFactory = new FactoryChengdu();
                ProductCarInter car = chengduFactory.createProductCar();
                car.productName();
                ProductBusInter bus = chengduFactory.createProductBus();
                bus.productName();
                ProductTruckInter truck = chengduFactory.createProductTruck();
                truck.productName();
                break;
        }
    }
}
