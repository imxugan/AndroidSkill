package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.ConcreteObserver;
import test.cn.example.com.androidskill.model.ConcreteSubject;

/**
 * Created by xgxg on 2017/7/14.
 * 观察着模式
 */

public class ObserverPatternActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_pattern);
        //创建一个观察着
        ConcreteObserver zhangsan = new ConcreteObserver("张三","看nba");
        ConcreteObserver lisi = new ConcreteObserver("李四","看中超");
        //创建被观察的主题
        ConcreteSubject subject = new ConcreteSubject();
        subject.attach(zhangsan);
        subject.attach(lisi);
        subject.changeState("同志们下boss回来啦！！！");
        subject.changeState("同志们下雨啦.......，赶紧收衣服吧");
        subject.detach(zhangsan);
        subject.detach(lisi);
    }
}
