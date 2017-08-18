package test.cn.example.com.androidskill.designpattern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;

/**
 * Created by xgxg on 2017/7/14.
 * 设计模式
 */

public class DesignPatternActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pattern);
        initView();
    }

    private void initView() {
        Button single_instance = (Button) findViewById(R.id.single_instance);
        single_instance.setOnClickListener(this);
        Button observer = (Button) findViewById(R.id.observer);
        observer.setOnClickListener(this);
        Button adapter = (Button) findViewById(R.id.adapter);
        adapter.setOnClickListener(this);
        Button factory = (Button) findViewById(R.id.factory);
        factory.setOnClickListener(this);
        Button strategy = (Button) findViewById(R.id.strategy);
        strategy.setOnClickListener(this);
        Button proxy = (Button) findViewById(R.id.proxy);
        proxy.setOnClickListener(this);
        Button facade = (Button) findViewById(R.id.facade);
        facade.setOnClickListener(this);
        Button builder = (Button) findViewById(R.id.builder);
        builder.setOnClickListener(this);
        Button decorator = (Button) findViewById(R.id.decorator);
        decorator.setOnClickListener(this);
        Button composite = (Button) findViewById(R.id.composite);
        composite.setOnClickListener(this);
        Button state = (Button) findViewById(R.id.state);
        state.setOnClickListener(this);
        Button chain = (Button) findViewById(R.id.chain);
        chain.setOnClickListener(this);
        Button command = (Button) findViewById(R.id.command);
        command.setOnClickListener(this);
        Button mediator = (Button) findViewById(R.id.mediator);
        mediator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.single_instance:
                startActivity(new Intent(DesignPatternActivity.this,SingleInstancePatternActivity.class));
                break;
            case R.id.observer:
                startActivity(new Intent(DesignPatternActivity.this,ObserverPatternActivity.class));
                break;
            case R.id.adapter:
                startActivity(new Intent(DesignPatternActivity.this,AdapterPatternActivity.class));
                break;
            case R.id.factory:
                startActivity(new Intent(DesignPatternActivity.this,FactoryPatternActivity.class));
                break;
            case R.id.strategy:
                startActivity(new Intent(DesignPatternActivity.this,StrategyPatternActivity.class));
                break;
            case R.id.proxy:
                startActivity(new Intent(DesignPatternActivity.this,ProxyPatternActivity.class));
                break;
            case R.id.facade:
                startActivity(new Intent(DesignPatternActivity.this,FacadePatternActivity.class));
                break;
            case R.id.builder:
                startActivity(new Intent(DesignPatternActivity.this,BuilderPatternActivity.class));
                break;
            case R.id.decorator:
                startActivity(new Intent(DesignPatternActivity.this,DecoratorPatternActivity.class));
                break;
            case R.id.composite:
                startActivity(new Intent(DesignPatternActivity.this,CompositePatternActivity.class));
                break;
            case R.id.state:
                startActivity(new Intent(DesignPatternActivity.this,StatePatternActivity.class));
                break;
            case R.id.chain:
                startActivity(new Intent(DesignPatternActivity.this,ChainPatternActivity.class));
                break;
            case R.id.command:
                startActivity(new Intent(DesignPatternActivity.this,CommandPatternActivity.class));
                break;
            case R.id.mediator:
                startActivity(new Intent(DesignPatternActivity.this,MediatorPatternActivity.class));
                break;
            default:
                break;

        }
    }
}
