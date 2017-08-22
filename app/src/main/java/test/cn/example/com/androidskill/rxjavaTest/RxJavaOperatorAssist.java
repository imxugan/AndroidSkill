package test.cn.example.com.androidskill.rxjavaTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * RxJava辅助操作符
 * Created by xgxg on 2017/8/22.
 */

public class RxJavaOperatorAssist extends AppCompatActivity implements View.OnClickListener {
    private Subscriber mSbuscriber;
    private SimpleDateFormat sdf;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_operator_assist);
        initView();
        initSubscriber();
    }

    private void initSubscriber() {
        mSbuscriber = new Subscriber() {
            @Override
            public void onCompleted() {
                LogUtil.i("thread ---"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i(e.toString());
            }

            @Override
            public void onNext(Object o) {
                if(null != sdf){
                    LogUtil.i("thread---"+Thread.currentThread().getName()+"---"+sdf.format(new Date())+"---"+o.toString());
                }else {
                    LogUtil.i("thread---"+Thread.currentThread().getName()+"---"+o.toString());
                }

            }
        };
    }

    private void initView() {
        Button delay = (Button) findViewById(R.id.delay);
        delay.setOnClickListener(this);
        Button delaySubscription = (Button) findViewById(R.id.delaySubscription);
        delaySubscription.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delay:
                delay();
                break;
            case R.id.delaySubscription:
                delaySubscription();
                break;
        }
    }

    private void delaySubscription(){
        if(null == sdf){
            sdf = new SimpleDateFormat("HH:mm:ss");
        }
        //delaySubscription
        //delaySubscription是延迟订阅，这个更好理解，
        // 就是原始Observable该怎么发射消息还是怎么发射，
        // 因为只有订阅之后才会开始发射消息，所以延迟2s。
        LogUtil.i("---delaySubscription---"+sdf.format(new Date()));
        initSubscriber();
        Observable<Integer> ob = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i=0;i<5;i++){
                    if(i>2){
                        subscriber.onError(new Throwable("数据大于2了"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });
        ob.delaySubscription(2, TimeUnit.SECONDS).subscribe(mSbuscriber);
        //打印结果：(第一条打印的数据和第二条打印的数据，时间间隔确实是2秒,说明确实是延时2秒发射数据)
//        08-24 17:06:55.080 1178-1178/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorAssist.java::86::delaySubscription-->>---delaySubscription---17:06:55
//        08-24 17:06:57.110 1178-1362/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorAssist.java::50::onNext-->>thread---RxComputationThreadPool-1---17:06:57---0
//        08-24 17:06:57.110 1178-1362/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorAssist.java::50::onNext-->>thread---RxComputationThreadPool-1---17:06:57---1
//        08-24 17:06:57.110 1178-1362/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorAssist.java::50::onNext-->>thread---RxComputationThreadPool-1---17:06:57---2
//        08-24 17:06:57.120 1178-1362/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorAssist.java::44::onError-->>java.lang.Throwable: 数据大于2了
    }

    private void delay() {
        //RxJava操作符(07-辅助操作)
        // http://blog.csdn.net/xmxkf/article/details/51658445
        //delay：让原始Observable在发射每项数据之前都暂停一段指定的时间段，
        // 结果是Observable发射的数据项在时间上整体延后一段时间
        //注意：delay不会平移onError通知，它会立即将这个通知传递给订阅者，
        // 同时丢弃任何待发射的onNext通知。但是它会平移一个onCompleted通知。
        initSubscriber();
        Observable<Integer> ob = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i=0;i<5;i++){
                    if(i>2){
                        subscriber.onError(new Throwable("数据大于2了"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });
        ob.subscribeOn(Schedulers.computation())
                .delay(2, TimeUnit.SECONDS)
                .subscribe(mSbuscriber);
        //打印结果；
//        08-24 16:49:59.190 17634-17762/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorAssist.java::41::onError-->>java.lang.Throwable: 数据大于2了
        //结果分析：
        //原始Observable会发射3个整数，然后发送onError通知。delay操作符会让
        // 每个发射的数据延迟2s发射出去，但由于原始Observable在2s之内
        // 发射了onError消息，而delay不会延迟onError通知，会立即传递给观察者，
        // 所以马上就结束了。
    }
}
