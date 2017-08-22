package test.cn.example.com.androidskill.rxjavaTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * rxJava创建操作符的使用
 * Created by xgxg on 2017/8/20.
 */

public class RxJavaOperatorCreate extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_operator_create);
        initView();
    }

    private void initView() {
        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(this);
        Button just = (Button) findViewById(R.id.just);
        just.setOnClickListener(this);
        Button from = (Button) findViewById(R.id.from);
        from.setOnClickListener(this);
        Button rang = (Button) findViewById(R.id.rang);
        rang.setOnClickListener(this);
        Button interval = (Button) findViewById(R.id.interval);
        interval.setOnClickListener(this);
        Button timer = (Button) findViewById(R.id.timer);
        timer.setOnClickListener(this);
        Button repeat = (Button) findViewById(R.id.repeat);
        repeat.setOnClickListener(this);
        Button defer = (Button) findViewById(R.id.defer);
        defer.setOnClickListener(this);
        Button empty = (Button) findViewById(R.id.empty);
        empty.setOnClickListener(this);
        Button error = (Button) findViewById(R.id.error);
        error.setOnClickListener(this);
        Button never = (Button) findViewById(R.id.never);
        never.setOnClickListener(this);
    }

    private void never(){
        //创建一个什么都不做的Observable
        Observable observable = Observable.never();
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i("onError");
            }

            @Override
            public void onNext(Object o) {
                LogUtil.i("onNext");
            }
        });
        //打印结果：
        //无

    }

    private void error(){
        // 创建一个什么都不做直接通知错误的Observable,//直接调用onError。这里可以自定义异常
        Observable observable = Observable.error(new NullPointerException("空指针异常"));
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i(e.getMessage());//onError-->>空指针异常
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    private void empty(){
        //empty： 创建一个什么都不做直接通知完成的Observable,直接调用onCompleted
       Observable observable = Observable.empty();
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");//onCompleted
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    private void defer() {
        //defer： 只有当订阅者订阅才创建Observable，为每个订阅创建一个新的Observable。
        // 内部通过OnSubscribeDefer在订阅时调用Func0创建Observable。
        Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just("defer operator");
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");//onCompleted
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i(s);//defer operator
            }
        };
        observable.subscribe(subscriber);
    }

    private void repeat(){
        //Repeat 会将一个Observable对象重复发射，我们可以指定其发射的次数
        Observable<Integer> observable = Observable.just(1,2,3).repeat(2);
        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");//>onCompleted
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.i(""+integer);//1,2,3  ...重复2次
            }
        });
    }

    private void timer(){
        //timer 创建一个在给定的延时之后发射数据项为0的Observable<Long>,内部通过OnSubscribeTimerOnce工作
        Observable<Long> observable = Observable.timer(1,TimeUnit.SECONDS);
        Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");//onCompleted
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.i(""+aLong);// 0
            }
        };
        observable.subscribe(subscriber);
    }

    private void interval(){
        //interval 创建一个按固定时间间隔发射整数序列的Observable
        //interval默认在computation调度器上执行。你也可以传递一个可选的Scheduler参数来指定调度器。
        //interval操作符默认情况下是运行在一个新线程上的，当然你可以通过传入参数来修改其运行的线程
        //Observable.interval(1, TimeUnit.MILLISECONDS)
        //两个参数：
        //第一个参数：代表两个消息发送之间的间隔时间(轮训时间)
        //第二个参数：时间单位：(毫秒，秒，分钟) TimeUtil时间工具类
        Observable observable  = Observable.interval(1,TimeUnit.SECONDS);
        Subscriber subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                LogUtil.i(""+aLong);
            }
        };
        observable.unsafeSubscribe(subscriber);

        //interval的另外一种重载
        //Observable.interval(2,1,TimeUnit.SECONDS);
        //三个参数：
        //第一个参数：代表两个消息发送之间的延时时间
        //第二个参数：代表两个消息发送之间的间隔时间(轮训时间)
        //第三个参数：时间单位：(毫秒，秒，分钟) TimeUtil时间工具类
        Observable observable2  = Observable.interval(2,1,TimeUnit.SECONDS);
    }

    private void rang(){
        //range： 创建一个发射指定范围的整数序列的Observable
        //第一个参数为起始值，
        // 第二个为发送的个数，如果为0则不发送，负数则抛异常
        //range(2,5)表示从2开始发射5个数据,2表示的是起始值，5表示的是个数
        Observable.range(2,5).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.i(""+integer);// 2,3,4,5,6
            }
        });
    }

    private void from() {
        String[] names = {"jim","jack","tom"};
        Observable<String> observable = Observable.from(names);
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i(s);
            }
        };
        observable.subscribe(subscriber);

    }

    private void just() {
        Observable<String> observable = Observable.just("a","b","c","d");
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i(s);
            }
        };
        observable.subscribe(subscriber);
    }

    private void create() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("create operator");
                subscriber.onCompleted();
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LogUtil.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i(""+s);
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create:
                create();
                break;
            case R.id.just:
                just();
                break;
            case R.id.from:
                from();
                break;
            case R.id.rang:
                rang();
                break;
            case R.id.interval:
                interval();
                break;
            case R.id.timer:
                timer();
                break;
            case R.id.repeat:
                repeat();
                break;
            case R.id.defer:
                defer();
                break;
            case R.id.empty:
                empty();
                break;
            case R.id.error:
                error();
                break;
            case R.id.never:
                never();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
