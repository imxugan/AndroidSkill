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



//        基本概念 (http://www.jianshu.com/p/5e93c9101dc5)
//        Observable：发射源，英文释义“可观察的”，在观察者模式中称为“被观察者”或“可观察对象”；
//
//        Observer：接收源，英文释义“观察者”，没错！就是观察者模式中的“观察者”，可接收Observable、Subject发射的数据；
//
//        Subject：Subject是一个比较特殊的对象，既可充当发射源，也可充当接收源，为避免初学者被混淆，
//        本章将不对Subject做过多的解释和使用，重点放在Observable和Observer上，先把最基本方法的使用学会，
//        后面再学其他的都不是什么问题；
//
//        Subscriber：“订阅者”，也是接收源，那它跟Observer有什么区别呢？Subscriber实现了Observer接口，
//        比Observer多了一个最重要的方法unsubscribe( )，用来取消订阅，当你不再想接收数据了，
//        可以调用unsubscribe( )方法停止接收，
//        Observer 在 subscribe() 过程中,最终也会被转换成 Subscriber 对象，一般情况下，
//        建议使用Subscriber作为接收源；
//
//        Subscription ：Observable调用subscribe( )方法返回的对象，同样有unsubscribe( )方法，
//         可以用来取消订阅事件；
//
//        Action0：RxJava中的一个接口，它只有一个无参call（）方法，且无返回值，同样还有Action1，Action2...Action9等，
//        Action1封装了含有 1 个参的call（）方法，即call（T t），
//        Action2封装了含有 2 个参数的call方法，即call（T1 t1，T2 t2），以此类推；
//
//        Func0：与Action0非常相似，也有call（）方法，但是它是有返回值的，
//        同样也有Func0、Func1...Func9;

    }

    private void initView() {
        Button base = (Button) findViewById(R.id.base);
        base.setOnClickListener(this);
        Button create_operator = (Button) findViewById(R.id.create_operator);
        create_operator.setOnClickListener(this);
        Button action = (Button) findViewById(R.id.action);
        action.setOnClickListener(this);
        Button base2 = (Button) findViewById(R.id.base2);
        base2.setOnClickListener(this);
        Button operator_convert = (Button) findViewById(R.id.operator_convert);
        operator_convert.setOnClickListener(this);
        Button operator_filter = (Button) findViewById(R.id.operator_filter);
        operator_filter.setOnClickListener(this);
        Button operator_combine = (Button) findViewById(R.id.operator_combine);
        operator_combine.setOnClickListener(this);
        Button operator_assist = (Button) findViewById(R.id.operator_assist);
        operator_assist.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.base:
                rxJavaSimpleTest();
                break;
            case R.id.create_operator:
                rxJavaCreateOperatorTest();
                break;
            case R.id.action:
                rxJavaActionTest();
                break;
            case R.id.base2:
                rxJavaSimpleTest2();
                break;
            case R.id.operator_convert:
                startActivity(new Intent(RxJavaTestActivity.this,RxJavaOperatorConvert.class));
                break;
            case R.id.operator_filter:
                startActivity(new Intent(RxJavaTestActivity.this,RxJavaOperatorFilter.class));
                break;
            case R.id.operator_combine:
                startActivity(new Intent(RxJavaTestActivity.this,RxJavaOperatorCombine.class));
                break;
            case R.id.operator_assist:
                startActivity(new Intent(RxJavaTestActivity.this,RxJavaOperatorAssist.class));
                break;
            default:
                break;
        }
    }

    private void rxJavaCreateOperatorTest(){
        startActivity(new Intent(RxJavaTestActivity.this,RxJavaOperatorCreate.class));
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

        //基础学习加强
        //1.创建观察者对象
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i("onError");
            }

            @Override
            public void onNext(String s) {
                LogUtil.i("onNext---s="+s);
            }
        };

        //2.创建被观察者对象
        Observable<String> observable4 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("test");
            }
        });

        //让被观察者订阅观察者
        observable4.subscribe(subscriber);

        Observable<String> observable5 = Observable.just("a","s","d");
        observable5.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i("observable5--------"+s);
            }
        });
        String[] arr = {"x","y","z"};
        Observable<String> observable16 = Observable.from(arr);
        observable16.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i("--------"+s);
            }
        });

    }
}
