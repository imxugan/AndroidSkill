package test.cn.example.com.androidskill.rxjavaTest;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

import static test.cn.example.com.util.LogUtil.i;

/**
 * RxJava过滤操作符使用
 * Created by xgxg on 2017/8/21.
 *
 * RxJava2.x使用以及操作符详解
 * https://blog.csdn.net/u011200604/article/details/72934661
 *
 * RxJava2 Flowable first 、last (过滤操作符)
 * https://blog.csdn.net/weixin_36709064/article/details/82957330
 *
 */

public class RxJavaOperatorFilter extends AppCompatActivity implements View.OnClickListener{
    private Observer mSubscriber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_operator_filter);
        initView();
        initSubscribe();
    }

    private void initView(){
        Button deBounce = (Button) findViewById(R.id.debounce);
        deBounce.setOnClickListener(this);
        Button distinct = (Button) findViewById(R.id.distinct);
        distinct.setOnClickListener(this);
        Button elementAt = (Button) findViewById(R.id.elementAt);
        elementAt.setOnClickListener(this);
        Button filter = (Button) findViewById(R.id.filter);
        filter.setOnClickListener(this);
        Button first = (Button) findViewById(R.id.first);
        first.setOnClickListener(this);
        Button ignoreElements = (Button) findViewById(R.id.ignoreElements);
        ignoreElements.setOnClickListener(this);
        Button last = (Button) findViewById(R.id.last);
        last.setOnClickListener(this);
        Button ofType = (Button) findViewById(R.id.ofType);
        ofType.setOnClickListener(this);
        Button sample = (Button) findViewById(R.id.sample);
        sample.setOnClickListener(this);
        Button throttleFirst = (Button) findViewById(R.id.throttleFirst);
        throttleFirst.setOnClickListener(this);
        Button single = (Button) findViewById(R.id.single);
        single.setOnClickListener(this);
        Button skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(this);
        Button skipLast = (Button) findViewById(R.id.skipLast);
        skipLast.setOnClickListener(this);
        Button take = (Button) findViewById(R.id.take);
        take.setOnClickListener(this);
        Button takeLast = (Button) findViewById(R.id.takeLast);
        takeLast.setOnClickListener(this);
        Button takeLastBuffer = (Button) findViewById(R.id.takeLastBuffer);
        takeLastBuffer.setOnClickListener(this);



    }

    private void initSubscribe(){
        mSubscriber = new Observer() {
            @Override
            public void onError(Throwable e) {
                i(""+e.toString());
            }

            @Override
            public void onComplete() {
                i("onCompleted");
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                i(""+o.toString());
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.debounce:
                deBounce();
                break;
            case R.id.distinct:
                distinct();
                break;
            case R.id.elementAt:
                elementAt();
                break;
            case R.id.filter:
                filter();
                break;
            case R.id.first:
                first();
                break;
            case R.id.ignoreElements:
                ignoreElements();
                break;
            case R.id.last:
                last();
                break;
            case R.id.ofType:
                ofType();
                break;
            case R.id.sample:
                sample();
                break;
            case R.id.throttleFirst:
                throttleFirst();
                break;
            case R.id.single:
                single();
                break;
            case R.id.skip:
                skip();
                break;
            case R.id.skipLast:
                skipLast();
                break;
            case R.id.take:
                take();
                break;
            case R.id.takeLast:
                takeLast();
                break;
            case R.id.takeLastBuffer:
//                takeLastBuffer();//rxjava2中去掉了
                break;
        }
    }

//    private void takeLastBuffer(){
//        //takeLastBuffer(count)操作符与takeLast(count)操作符类似，
//        // 唯一不同就是takeLastBuffer(count)将最后的那些数据项收集到一个list集合中
//        // 提交这个集合给订阅者
//        initSubscribe();
//        Observable.range(1,5).takeLastBuffer(3).subscribe(mSubscriber);
//        //打印结果：
////        08-23 16:40:01.790 10072-10072/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::87::onNext-->>[3, 4, 5]
//
//
//        //takeLastBuffer(long,TimeUnit)操作符与takeLast(long,TimeUnit)操作符类似，
//        // 唯一不同就是将在最后时间段Long中数据项收集到一个list集合中，
//        //将这个集合提交给了订阅者，可以指定运行的线程。
//        LogUtil.i("---takeLastBuffer(long,TimeUnit)---");
//        initSubscribe();
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i=0;i<9;i++){
//                    emitter.onNext(i);
//                    SystemClock.sleep(100);
//                }
//                emitter.onComplete();
//            }
//        }).takeLastBuffer(300,TimeUnit.MILLISECONDS).subscribe(mSubscriber);
//        //打印结果：(要看到打印结果，一定要手动调用subscriber.onCompleted(); 否则是看不到打印结果的)
////        08-23 17:41:07.910 3294-3294/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::88::onNext-->>[7, 8]
//    }

    private void takeLast(){
        //takeLast(count)操作符对于源Observable发射的数据项，
        // 提取后面的count项数据提交给订阅者，忽略前面的
        initSubscribe();
        Observable.range(2,5).takeLast(3).subscribe(mSubscriber);
        //打印结果：
//        08-23 16:10:28.430 14573-14573/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::85::onNext-->>4
//        08-23 16:10:28.430 14573-14573/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::85::onNext-->>5
//        08-23 16:10:28.430 14573-14573/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::85::onNext-->>6


        //takeLast(long,TimeUnit)操作符对于源Obsrvable发射的数据项，
        // 提取后面long时间段里的数据项提交给订阅者，忽略前面的，可以指定线程
        LogUtil.i("---takeLast(long,TimeUnit)---");
        initSubscribe();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                if(emitter.isDisposed()){
                    return;
                }
                for(int i =0;i<9;i++){
                    SystemClock.sleep(100);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.computation())
                .takeLast(300,TimeUnit.MILLISECONDS)
                .subscribe(mSubscriber);
        //打印结果：(要看到打印结果，一定要手动调用subscriber.onCompleted(); 否则是看不到打印结果的)
//        08-23 17:35:37.680 30049-30154/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::88::onNext-->>6
//        08-23 17:35:37.690 30049-30154/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::88::onNext-->>7
//        08-23 17:35:37.690 30049-30154/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::88::onNext-->>8

//        其他 takeLast() API：
//
//        public final Observable<T> takeLast(int count, long time, TimeUnit unit);
//
//        public final Observable<T> takeLast(int count, long time, TimeUnit unit, Scheduler scheduler);
//
//        public final Observable<T> takeLast(long time, TimeUnit unit);
//
//        public final Observable<T> takeLast(long time, TimeUnit unit, Scheduler scheduler);

    }

    private void take(){
        //take(count)操作符对于源Observable发射的数据项，
        // 提取前面的count项数据提交给订阅者，忽略后面的
        initSubscribe();
        Observable.just(1,3,4,5).take(2).subscribe(mSubscriber);
        //打印结果
//        08-23 16:02:39.350 6497-6497/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::83::onNext-->>1
//        08-23 16:02:39.350 6497-6497/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::83::onNext-->>3


        //take(long,TimeUnit)操作符对于源Obsrvable发射的数据项，
        // 提取前面long时间段里的数据项提交给订阅者，忽略后面的，可以指定线程
        LogUtil.i("---take(long,TimeUnit)---");
        initSubscribe();
        Observable.interval(100,TimeUnit.MILLISECONDS).take(300,TimeUnit.MILLISECONDS).subscribe(mSubscriber);
        //打印结果；
//        08-23 16:07:17.070 11577-11686/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::83::onNext-->>0
//        08-23 16:07:17.160 11577-11686/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::83::onNext-->>1
    }

    private void skipLast(){
        //skipLast(count) 对于源Observable发射的数据项，省略最后count项，
        // 将前面的数据项提交给订阅者，如果省略的count项大于源数据的数据量，则不返回任何值，
        //onCompleted方法会调用
        initSubscribe();
        Observable.just(1,2,3).skipLast(2).subscribe(mSubscriber);
        //打印结果：
//        08-23 15:37:50.890 16339-16339/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::81::onNext-->>1


        //skipLast(long,TimeUnit)对于原Observalbe发射的数据项，省略最后long时间段的数据项，
        // 将之前的数据提交给订阅者，可以指定执行线程
        initSubscribe();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                if(emitter.isDisposed()){
                    return;
                }
                try{
                    for(int i =0;i<9;i++){
                        emitter.onNext(i);
                        Thread.sleep(100);
                    }
                }catch (InterruptedException e){
                    emitter.onError(e);
                }
            }
        }).skipLast(220,TimeUnit.MILLISECONDS).subscribe(mSubscriber);
        //打印结果：
//        08-23 15:53:15.610 24673-24673/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::81::onNext-->>0
//        08-23 15:53:15.710 24673-24673/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::81::onNext-->>1
//        08-23 15:53:15.850 24673-24673/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::81::onNext-->>2
//        08-23 15:53:15.950 24673-24673/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::81::onNext-->>3
//        08-23 15:53:15.950 24673-24673/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::81::onNext-->>4
//        08-23 15:53:16.050 24673-24673/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::81::onNext-->>5
        //打印结果分析：
        //从最后一个数据8开始，向前推进220毫秒的数字是5，所以，将5以后的数据忽略掉,只打印包含5之前的数据
    }

    private void skip(){
        //skip(count) 对于源Observable发射的数据项，跳过前count项，将后面的数据项提交给订阅者
        //skip(long,TimeUnit)对于原Observalbe发射的数据项，跳过long前的数据项，
        // 将之后的数据提交给订阅者，可以指定执行线程
        initSubscribe();
        Observable.just(1,3,5,5,6).skip(2).subscribe(mSubscriber);
        //打印结果：
//        08-23 15:22:58.460 1944-1944/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>5
//        08-23 15:22:58.460 1944-1944/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>5
//        08-23 15:22:58.460 1944-1944/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>6


        //skip(long,TimeUnit)对于原Observalbe发射的数据项，跳过long前的数据项，
        LogUtil.i("---skip(long,TimeUnit)---");
        initSubscribe();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                if(emitter.isDisposed()){
                    return;
                }
                try{
                    for(int i=0;i<9;i++){
                        emitter.onNext(i);
                        Thread.sleep(100);
                    }
                }catch (InterruptedException e){
                    emitter.onError(e);
                }
            }
        }).skip(220,TimeUnit.MILLISECONDS).subscribe(mSubscriber);
        //打印结果：220表示发射220毫秒后的数据
//        08-23 15:28:55.960 7304-7304/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>3
//        08-23 15:28:56.060 7304-7304/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>4
//        08-23 15:28:56.170 7304-7304/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>5
//        08-23 15:28:56.270 7304-7304/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>6
//        08-23 15:28:56.370 7304-7304/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>7
//        08-23 15:28:56.470 7304-7304/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::79::onNext-->>8
    }

    private void single(){
        //single()操作符：在源Observable只发射一个数据项的时候，
        // single()操作符会将这个数据提交给订阅者，
        // 大于1个就抛Sequence contains too many elements的异常，
        // 不是正好是一个数据项就会抛异常
//        single(Func1)操作符是对源Observable发射的数据项进行判断，
//        如果符合条件的数据数量大于1就会抛异常。不是正好是一个数据项就会抛异常
//        也有设置默认值得api，默认不在任何特定的调度器上执行
        initSubscribe();
        Observable.just(1,3,5).singleOrError().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 14:54:29.240 7605-7605/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::72::onError-->>java.lang.IllegalArgumentException: Sequence contains too many elements


        //single(Func1)操作符是对源Observable发射的数据项进行判断，
        // 如果符合条件的数据数量大于1就会抛异常。不是正好是一个数据项就会抛异常
//        也有设置默认值得api，默认不在任何特定的调度器上执行
        initSubscribe();
        Observable.just(1,3,5).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer>5;
            }
        }).singleOrError().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(""+integer);
            }
        });
        //打印结果：
//        08-23 14:57:20.610 10681-10681/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::72::onError-->>java.util.NoSuchElementException: Sequence contains no elements

//        First,FirstOrDefault,Single,SingleOrDefault的区别
//        http://www.cnblogs.com/firstcsharp/archive/2013/02/27/2935833.html
//        操作符           如果源序列是空的        源序列只包含一个元素     源序列包含多个元素
//
//        First            抛异常                  返回该元素               返回第一个元素
//
//        FirstOrDefault   返回default(TSource)    返回该元素               返回第一个元素
//
//        Last             抛异常                  返回该元素               返回最后一个元素
//
//        LastOrDefault    返回default(TSource)    返回该元素               返回最后一个元素
//
//        Single           抛异常                  返回该元素               抛异常
//
//        SingleOrDefault  返回default(TSource)    返回该元素               抛异常

        //singleOrDefault(T)，如果源序列是空的，则返回default，
        //如果，源序列只包含一个元素，则返回该元素，
        //如果源序列包含多个元素，则抛异常
        LogUtil.i("---singleOrDefault(T)---");
        initSubscribe();
        Observable.just(1, 3,5).single(9).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 15:09:58.760 14815-14815/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::72::onError-->>java.lang.IllegalArgumentException: Sequence contains too many elements

        //singleOrDefault(T,Func1)
        //如果源序列是空的，则返回default，
        //如果，源序列只包含满足条件的一个元素，则返回该元素，
        //如果源序列包含多个元素，则抛异常
        LogUtil.i("---singleOrDefault(T,Func1)---");
        initSubscribe();
        Observable.just(1,3).single(9).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer>3;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 15:18:32.940 28823-28823/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::77::onNext-->>9
    }

    private void throttleFirst(){
        //操作符是throttleFirst,它采样的是采样时间间隔中第一项数据
        //sample操作符对Observable发射的数据定时进行采样，采的是自从上一次采样以来，
        // Observable最近发射的一项数据，也就是这段时间间隔中发射器发射的第一个数据项
        //具体的理解这个第一项数据，就是从上次采样时间开始计时，从这个时间点后，发射器发射的第一个数据。
        //和sample的区别是，sample取的是时间间隔的最后一项，而throttleFirst取的是时间间隔的第一项
        initSubscribe();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                if(emitter.isDisposed()){
                    return;
                }
                try{
                    for(int i =0;i<9;i++){
                        emitter.onNext(i);
                        Thread.sleep(100);
                    }
                }catch (InterruptedException e){
                    emitter.onError(e);
                }
            }
        }).throttleFirst(270,TimeUnit.MILLISECONDS).subscribe(mSubscriber);
        //打印结果：
//        08-23 14:44:00.430 29343-29343/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::75::onNext-->>0
//        08-23 14:44:00.730 29343-29343/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::75::onNext-->>3
//        08-23 14:44:01.030 29343-29343/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::75::onNext-->>6
    }

    private void sample(){
        //sample操作符对Observable发射的数据定时进行采样，采的是自从上一次采样以来，
        // Observable最近发射的一项数据，也就是这段时间间隔中最后一个数据项。
        // 如果自上一次采样以来，源Observable没有发射任何数据，
        // sample操作符返回的Observable在此段时间也不会发射任何数据
//        默认在computation调度器上执行，但是可以指定它执行的线程 sample(long,TimeUnit,Scheduler)
//        与之对应的操作符是throttleFirst,它采样的是采样时间间隔中第一项数据，
//        在最后一个时间段会发射最后一个数据项
        initSubscribe();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                if(emitter.isDisposed()){
                    return;
                }
                try {
                    for(int i=0;i<9;i++){
                        emitter.onNext(i);
                        Thread.sleep(100);
                    }
                }catch (InterruptedException e){
                    emitter.onError(e);
                }
            }
        })
                .sample(270,TimeUnit.MILLISECONDS)
                .subscribe(mSubscriber);
        //打印结果：
//        08-23 14:46:13.790 29343-32340/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::75::onNext-->>2
//        08-23 14:46:14.060 29343-32340/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::75::onNext-->>5
//        08-23 14:46:14.330 29343-32340/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::75::onNext-->>8
        //可以结合下图来理解打印结果
//        time 0------1------2------3------4------5------6------7------8------9------10-----
//        data      0------1------2------3------4------5------6------7------8------9------10-----


    }

    private void ofType(){
        //ofType 操作符类似于filter操作符，区别在于ofType按照数据项的类型进行过滤，
        // 默认不在任何特定的调度器上执行
        initSubscribe();
        Observable.just(1,"hello world",true).ofType(String.class).subscribe(mSubscriber);
        //打印结果：
//        08-23 13:56:16.400 19743-19743/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::71::onNext-->>hello world
    }

    private void last(){
        //last()操作符与first（）操作符相反，只提交最后一个数据项给订阅者，
        // 如果只是作为过滤操作符，最好使用takeLast(1),
//        官方文档解释说：first()操作符和last()操作符在某些实现中会返回一个阻塞函数。
//        与first()操作符系列对应，也有last(Func1)、lastOrDefault(T）、lastOrDefault(T,Func1)
        LogUtil.i("---last()---");
        initSubscribe();
        Integer[] arr = new Integer[]{1,2,2,3,5,6,5};
        Observable.fromArray(arr).lastElement().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 13:45:06.430 8794-8794/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::69::onNext-->>5

        //last(Func1)操作符是提交最后一项符合自定义条件的数据,
        // 注意只返回一项数据，不是所有满足条件的数据都返回
        LogUtil.i("---last(Func1)---");
        initSubscribe();
        Observable.just(1,2,2,3,5,6,5).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer>=5;
            }
        }).lastElement().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(""+integer);
            }
        });
        //打印结果：
//        08-23 13:45:06.450 8794-8794/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::69::onNext-->>5


        //lastOrDefault(T）操作符是在Observable没有发射任何数据时提交一个指定的默认值
        LogUtil.i("---lastOrDefault(T）---");
        initSubscribe();
        Observable.just(1,2,2,3,5,6,5).last(10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(""+integer);
            }
        });
        //打印结果：
//        08-23 13:49:40.190 13161-13161/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::69::onNext-->>5


        //lastOrDefault(T,Func1) 操作符是在Observable没有发射满足条件的数据时提交一个指定的默认值
        LogUtil.i("---lastOrDefault(T）---");
        initSubscribe();
        Observable.just(1,2,2,3,5,6,5).last(10).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer>6;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 13:51:51.820 15439-15439/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::69::onNext-->>10
    }

    private void ignoreElements(){
        //ignoreElements()操作符不提交任何数据给订阅者，
        // 只提交终止通知（onError或者onCompeleted）给订阅者，默认不在任何特定的调度器上执行
        initSubscribe();
        Observable.range(2,5).ignoreElements().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                LogUtil.i("onComplete");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
        //打印结果；
//        08-23 12:02:32.470 16317-16317/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::57::onCompleted-->>onCompleted
    }

    private void first(){
        //first()操作符提交源Observable发射的第一项数据，如果只是想要一个过滤符，
        // 最好使用take(2)或者elementAt(0)
//        first(Func1)操作符是提交第一项符合自定义条件的数据
//        firstOrDefault(T)操作符是在Observable没有发射任何数据时提交一个指定的默认值
//        takeFirst(Func1)操作符提交符合自定义条件的的第一项数据，
//        与first（Func1）不同的是，takeFirst(Func1)在没有符合条件的时候，
//        会调用onCompleted，而first(Func1)会抛一个NoSuchElementException的异常
        LogUtil.i("---first()---");
        initSubscribe();
        Observable.just(1,2,3,5,7).first(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 11:41:56.030 30569-30569/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::65::onNext-->>1

        //first(Func1)操作符是提交第一项符合自定义条件的数据
        LogUtil.i("---first(Func1)---");
        initSubscribe();
        Observable.just(1,2,3,5,7).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer>3;
            }
        }).firstElement().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 11:44:47.330 1431-1431/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::65::onNext-->>5


        //firstOrDefault(T)操作符是在Observable没有发射任何数据时提交一个指定的默认值
        LogUtil.i("---firstOrDefault(T)---");
        initSubscribe();
        Observable.just(1,2,3,5,7).first(9).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 11:48:14.890 4412-4412/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::65::onNext-->>1


        //firstOrDefault(T,Func1)操作符是在Observable没有发射满足条件的数据时提交一个指定的默认值
        LogUtil.i("---firstOrDefault(T,Func1)---");
        initSubscribe();
        Observable.just(1,2,3,5,7).first(9).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer > 10;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });
        //打印结果：
//        08-23 11:52:28.460 7896-7896/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::65::onNext-->>9


        //takeFirst(Func1)操作符提交符合自定义条件的的第一项数据
        //与first（Func1）不同的是，takeFirst(Func1)在没有符合条件的时候，
        // 会调用onCompleted，而first(Func1)会抛一个NoSuchElementException的异常
        LogUtil.i("---takeFirst(Func1)---");
        initSubscribe();
        Observable.just(1,2,3,5,7).take(1).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer>9;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(""+integer);
            }
        });
        //打印结果：
//        08-23 11:56:55.320 11659-11659/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::55::onCompleted-->>onCompleted
    }

    private void filter(){
        //filter操作符对源Observable发射的数据项按照指定的条件进行过滤，
        // 满足的条件的才会调给订阅者。默认不在任何特定的调度器上执行
        initSubscribe();
        Observable.just(1,3,5,7).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer>3;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 11:36:27.410 26704-26704/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::63::onNext-->>5
//        08-23 11:36:27.420 26704-26704/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::63::onNext-->>7
    }

    private void elementAt(){
        //elementAt(index)
        //将指定索引的数据项提交给订阅者，索引是从0开始，当没有这个索引或者索引为负数会抛异常。
        LogUtil.i("---elementAt(index)---");
        initSubscribe();
        Observable.just(1,2,3,5).elementAt(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(integer+"");
            }
        });
        //打印结果：
//        08-23 11:27:42.300 19148-19148/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::60::onNext-->>3



        //elementAtOrDefault(index,default)：这个会设置一个默认值，当没有指定的索引就提交默认值给订阅者，为负数就抛异常
        LogUtil.i("---elementAtOrDefault(index,default)---");
        initSubscribe();
        Observable.just(1,2,3,5).elementAt(6,0).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(""+integer);
            }
        });
        //打印结果：
//        08-23 11:31:41.500 22447-22447/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::60::onNext-->>0
    }

    private void distinct(){
        //distinct  过滤掉重复的数据项。过滤规则是只允许没有发射过的数据项通过。
//        变体distinct(Func1)根据返回的key值去过滤，不用数据本身.
//        distinctUntilChanged()只判断这个数据项跟前一个数据项是否相同，
//        distinctUnitilChanged(Func1)也是根据返回的key值去比较过滤。
//        默认不在任何特定的调度器上执行。
        LogUtil.i("---distinct()---");
        initSubscribe();//由于onCompleted方法执行后，其他如果再次发射数据，Subscripber也不会接收了。所以每次都
        //需要重新创建一个Subscriber对象。
        Observable.just(1,2,3,3,5,5,7,7,9,7).distinct().subscribe(mSubscriber);
        //打印结果：
//        08-23 11:00:20.750 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>1
//        08-23 11:00:20.750 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>2
//        08-23 11:00:20.750 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>3
//        08-23 11:00:20.750 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>5
//        08-23 11:00:20.750 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>7
//        08-23 11:00:20.750 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>9
        LogUtil.i("---distinct(Func1)---");
        //变体distinct(Func1)根据返回的key值去过滤，不用数据本身,意思是可以自己定义一套过滤规则
        //如下示例的过滤规则是，根据返回的boolean的值去过滤，
        // 具体就是只要是false的只存一次，true的也只存一次，
        //打印的结果是1,5，解释下打印的的结果：第一次比较 1和5不相等，则返回false，则将1打印，
        //后续的2,3,3，都和5不相等，也是返回false,由于过滤规则是：相同的false的只存一个，其他的都过滤掉
        //所以2,3，3都不打印，当是5时，返回了true,由于true是第一次出现，则将5打印出来，后续的5,5
        //由于第一个5已经返回了true,后续的两个5返回的true都被被虑掉了，所以不打印，后续的7,7,9
        //由于返回的是false，之前有fasle了，所以7,7,9返回的false都被过滤掉了，所以7,7,9也不打印。
        initSubscribe();
        Observable.just(1,2,3,3,5,5,7,7,9,7).distinct(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(@NonNull Integer integer) throws Exception {
                boolean result = (integer == 5);
                return result;
            }
        }).subscribe(mSubscriber);
        //打印结果：
//        08-23 11:00:20.760 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>1
//        08-23 11:00:20.760 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>5

         LogUtil.i("----distinctUntilChanged---");
        //使用distinctUntilChanged()方法，抑制序列中连续出现的重复值，并返回一个新的序列
        initSubscribe();
        Observable.just(1,2,3,3,5,5,7,7,9,7).distinctUntilChanged().subscribe(mSubscriber);
        //打印结果：
//        08-23 11:00:20.760 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>1
//        08-23 11:00:20.760 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>2
//        08-23 11:00:20.760 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>3
//        08-23 11:00:20.760 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>5
//        08-23 11:00:20.760 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>7
//        08-23 11:00:20.770 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>9
//        08-23 11:00:20.770 32623-32623/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>7

        LogUtil.i("-----distinctUnitilChanged(Func1)----");
        //使用distinctUnitilChanged(Func1)也是根据返回的key值去比较过滤
        initSubscribe();
        Observable.just(1,2,3,3,5,5,7,7,9,7).distinctUntilChanged(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(@NonNull Integer integer) throws Exception {
                return (integer == 5);
            }
        }).subscribe(mSubscriber);
        //打印结果：
//        08-23 11:13:30.030 7601-7601/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>1
//        08-23 11:13:30.030 7601-7601/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>5
//        08-23 11:13:30.030 7601-7601/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorFilter.java::58::onNext-->>7
    }

    private void deBounce() {
        //debounce操作符对源Observable每产生一个结果后，
        // 如果在规定的间隔时间内没有别的结果产生，则把这个结果提交给订阅者处理，否则忽略该结果
        //如果在规定的间隔时间内产生了其他结果，就忽略掉发射的这个数据,使用新产生的结果,
        // 通过制定的时间间隔来限流，
        // 可以过滤掉发射速率过快的数据项，默认在computatiion调度器上执行，可以指定执行线程
        //值得注意的是，如果源Observable产生的最后一个结果后在规定的时间间隔内调用了onCompleted，
//        那么通过debounce操作符也会把这个结果提交给订阅者
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Exception {
                if(emitter.isDisposed()){
                    return;
                }
                try {
                    for (int i=0;i<9;i++){
//                        LogUtil.i(""+i);
                        emitter.onNext(i);
                        Thread.sleep(400);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(mSubscriber);
    }
}
