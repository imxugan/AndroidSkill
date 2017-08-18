package test.cn.example.com.androidskill.rxjavaTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;


/**
 * RxJava
 * Created by xgxg on 2017/8/3.
 */
public class RxJavaTestActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initView();
        //扩展的观察者模式
        //在RxJava中主要有4个角色：
        //Observable
        // Subject
        //Observer
        // Subscriber
        //Observable和Subject是两个“生产”实体，
        // Observer和Subscriber是两个“消费”实体。说直白点Observable对应于观察者模式中的被观察者，
        // 而Observer和Subscriber对应于观察者模式中的观察者。
        // Subscriber其实是一个实现了Observer的抽象类，后面我们分析源码的时候也会介绍到。
        // Subject比较复杂，以后再分析。

        //上一篇文章中我们说到RxJava中有个关键概念：事件。
        // 观察者Observer和被观察者Observable通过subscribe()方法实现订阅关系。
        // 从而Observable 可以在需要的时候发出事件来通知Observer。
    }

    private void initView() {
        Button action = (Button) findViewById(R.id.action);
        action.setOnClickListener(this);
        Button base = (Button) findViewById(R.id.base);
        base.setOnClickListener(this);
        Button base2 = (Button) findViewById(R.id.base2);
        base2.setOnClickListener(this);
        Button operator_convert = (Button) findViewById(R.id.operator_convert);
        operator_convert.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action:
                rxJavaActionTest();
                break;
            case R.id.base:
                rxJavaSimpleTest();
                break;
            case R.id.base2:
                rxJavaSimpleTest2();
                break;
            case R.id.operator_convert:
                startActivity(new Intent(RxJavaTestActivity.this,RxJavaOperatorConvert.class));
                break;

            default:
                break;
        }
    }





    private void rxJavaActionTest() {
        //使用Action1
        Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("aciont1 execute");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LogUtil.i(s);
            }
        });

        //使用Action0
        Observable<String> observable = Observable.just("hello","rxjava");
        //处理next()中的内容
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                LogUtil.i(s);
            }
        };
        //处理onError()中的内容
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LogUtil.i("onErrorAction");
            }
        };
        //处理onCompleted()中的内容
        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                LogUtil.i("completed");
            }
        };
        observable.subscribe(onNextAction,onErrorAction,onCompletedAction);



    }

    private void rxJavaSimpleTest2() {
        Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i=0;i<5;i++){
                    subscriber.onNext("     "+i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i("s====="+s);
            }
        });

    }

    private void rxJavaSimpleTest() {
        //rxjava的使用步骤:
        //第一步：创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i("onNext----"+s);
            }
        };

        //第二步：创建被观察者Observable，Observable.create()方法可以创建Observable，
        //使用create()方法创建Observable需要传递一个OnSubscribe对象，这个对象继承Action1.
        //当观察者订阅我们的Observable时，它作为一个参数传入，并执行call()方法
        Observable<String> observable = Observable.create(new rx.Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i=0;i<5;i++){
                    subscriber.onNext(""+i);
                }
//                subscriber.onCompleted();
                LogUtil.i("--888--");
            }
        });
        //除了create()方法之外，还有just()和from()同样可以创建Observable
        Observable<String> observable2 = Observable.just("hello","hello2","hello3");
        //上面这行代码会依次调用
        //onNext("hello");
        //onNext("hello2");
        //onNext("hello3");
        //onCompleted();

        String[] parameters = {"one","two","three"};

        Observable<String> observable3 = Observable.from(parameters);
        //上面这行代码会依次调用
        //onNext("One");
        //onNext("Two");
        //onNext("Three");
        //onCompleted();

        //第三步：被观察者订阅观察者
        observable.subscribe(observer);
        observable2.subscribe(observer);
        observable3.subscribe(observer);
    }
}