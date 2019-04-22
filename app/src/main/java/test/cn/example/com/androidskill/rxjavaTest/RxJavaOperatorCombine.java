package test.cn.example.com.androidskill.rxjavaTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * RxJava组合操作符
 * Created by xgxg on 2017/8/22.
 */

public class RxJavaOperatorCombine extends AppCompatActivity implements View.OnClickListener {
    private Observer mSbuscriber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_operator_combine);
        initView();
        initSubscriber();
    }

    private void initSubscriber() {
        mSbuscriber = new Observer() {
            @Override
            public void onComplete() {
                LogUtil.i("thread ---"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i(e.toString());
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                LogUtil.i("thread---"+Thread.currentThread().getName()+"---"+o.toString());
            }
        };
    }

    private void initView() {
        Button combineLatest = (Button) findViewById(R.id.combineLatest);
        combineLatest.setOnClickListener(this);
        Button join = (Button) findViewById(R.id.join);
        join.setOnClickListener(this);
        Button groupJoin = (Button) findViewById(R.id.groupJoin);
        groupJoin.setOnClickListener(this);
        Button merge = (Button) findViewById(R.id.merge);
        merge.setOnClickListener(this);
        Button mergeDelayError = (Button) findViewById(R.id.mergeDelayError);
        mergeDelayError.setOnClickListener(this);
        Button startWith = (Button) findViewById(R.id.startWith);
        startWith.setOnClickListener(this);
        Button switchOnNext = (Button) findViewById(R.id.switchOnNext);
        switchOnNext.setOnClickListener(this);
        Button zip = (Button) findViewById(R.id.zip);
        zip.setOnClickListener(this);
        Button zipWith = (Button) findViewById(R.id.zipWith);
        zipWith.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.combineLatest:
                combineLatest();
                break;
            case R.id.join:
                join();
                break;
            case R.id.groupJoin:
                groupJoin();
                break;
            case R.id.merge:
                merge();
                break;
            case R.id.mergeDelayError:
                mergeDelayError();
                break;
            case R.id.startWith:
                startWith();
                break;
            case R.id.switchOnNext:
                switchOnNext();
                break;
            case R.id.zip:
                zip();
                break;
            case R.id.zipWith:
                zipWith();
                break;
        }
    }

    private void zipWith(){
        //跟zip类似，但是他是非静态的，需要在另外一个Observable操作之上,
        // 他接受两个参数，一种是一个Observable和Func2，
        // 另外一个是多个组合， Iterable和Func2
        //默认不在特定的调度器上执行
        initSubscriber();
        Observable<Integer> ob = Observable.just(1, 3, 5,7);
        ob.zipWith(Observable.just(2, 4), new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(mSbuscriber);
        //打印结果：
//        08-24 14:40:52.620 27929-27929/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---3
//        08-24 14:40:52.620 27929-27929/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---7
//        08-24 14:40:52.620 27929-27929/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::42::onCompleted-->>thread ---main

    }

    private void zip(){
        //Zip通过一个函数将多个Observable发送的事件结合到一起，
        // 然后发送这些组合到一起的事件. 它按照严格的顺序应用这个函数。
        // 它只发射与发射数据项最少的那个Observable一样多的数据
        //默认不在特定的调度器上执行
        //https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247484322&idx=1&sn=70e6c88cfcd518f2134e80d3b4fdf309&chksm=96cda2efa1ba2bf90007379eef18d2ea3976ced1ce0aa47e9fd929652760927d2d9b23502ad9&scene=0&key=442dcab2e39cb7dab5a711b7298466e8b842a5bcf5c768e9622a80e73badd2dd5cf3d7768dd8755305008876141dab946d4ebb791a439a4b622e20cc20261726a52562c3e479a64a612fc50ac2807cea&ascene=0&uin=MjI2ODA0ODAzNg%3D%3D&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.12.4+build(16E195)&version=12020110&nettype=WIFI&fontScale=

        //学习了Zip的基本用法, 那么它在Android有什么用呢, 其实很多场景都可以用到Zip. 举个例子.
        //比如一个界面需要展示用户的一些信息, 而这些信息分别要从两个服务器接口中获取,
        // 而只有当两个都获取到了之后才能进行展示, 这个时候就可以用Zip了
        Observable<Integer> ob = Observable.just(1, 2, 3,4, 5);
        Observable<Integer> ob2 = Observable.just(7, 8);
        Observable.zip(ob, ob2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                //                LogUtil.i("integer="+integer+"---integer2="+integer2);
                return integer+integer2;
            }
        }).subscribe(mSbuscriber);
        //打印结果：
//        integer=1---integer2=7
//        08-24 14:23:10.560 12086-12086/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---8
        //integer=2---integer2=8
//        08-24 14:23:10.560 12086-12086/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---10
//        08-24 14:23:10.560 12086-12086/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::42::onCompleted-->>thread ---main

    }

    private void switchOnNext(){
        //switchOnNext操作符是把一组Observable转换成一个Observable，
        // 转换规则为：对于这组Observable中的每一个Observable所产生的结果，
        // 如果在同一个时间内，存在两个或多个Observable提交的结果，
        // 只取最后一个Observable提交的结果给订阅者
        initSubscriber();
        Observable<Observable<?>> ob = Observable.interval(100, TimeUnit.MILLISECONDS).map(new Function<Long, Observable<?>>() {
            @Override
            public Observable<?> apply(@NonNull Long aLong) throws Exception {
                Observable<Long> longObservable = Observable.interval(30, TimeUnit.MILLISECONDS).map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        //                        return aLong;
//                        LogUtil.i("aLong="+aLong+"---aLong2="+aLong2);
                        return aLong ;
                    }
                });
//                LogUtil.i("longObservable="+longObservable);
                return longObservable;
            }
        });
        Observable.switchOnNext(ob).take(9).subscribe(mSbuscriber);
        //打印结果：
//        08-24 15:23:11.800 1878-2607/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-2---0
//        08-24 15:23:11.830 1878-2607/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-2---0
//        08-24 15:23:11.860 1878-2607/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-2---0
//        08-24 15:23:11.900 1878-2608/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---1
//        08-24 15:23:11.930 1878-2608/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---1
//        08-24 15:23:11.960 1878-2608/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---1
//        08-24 15:23:12.000 1878-2609/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-4---2
//        08-24 15:23:12.030 1878-2609/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-4---2
//        08-24 15:23:12.060 1878-2609/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-4---2
//        08-24 15:23:12.060 1878-2609/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::42::onCompleted-->>thread ---RxComputationThreadPool-4
//        注意上面示例中 switchOnNext 的参数 每隔 100毫秒返回一个 Observable 。
//        这个返回的 Observable 会每隔 30 毫秒发射一个数字，
//        这个数字被映射为 100毫秒发射一个数据的 Observable返回的数据。
//        所以在第一个100毫秒的时候，switchOnNext 的参数
//        返回的第一个 Observable 可以发射3个数据 0，然后到第100毫秒的时候，
//        switchOnNext 的参数返回的第二个 Observable 开发发射数据1，
//        所以前一个发射数据 0 的 Observable 就被丢弃了，
//        switchOnNext 切换到新的发射数据的 Observable
    }

    private void startWith(){
        //startWith操作符是在源Observable提交结果之前插入指定的数据，
        // 可以是数值，也可以是Observable对象
        initSubscriber();
        Observable.just(1,2).startWith(1).subscribe(mSbuscriber);
        //打印结果：
//        08-24 14:01:12.920 23789-23789/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---0
//        08-24 14:01:12.930 23789-23789/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---1
//        08-24 14:01:12.930 23789-23789/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---2
//        08-24 14:01:12.930 23789-23789/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::42::onCompleted-->>thread ---main

        //startWith(Observable)
        LogUtil.i("---startWith(Observable)---");
        initSubscriber();
        Observable<Integer> ob = Observable.just(0,9);
        Observable.just(1,2).startWith(ob).subscribe(mSbuscriber);
        //打印结果：
//        08-24 14:06:29.260 28022-28022/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---0
//        08-24 14:06:29.260 28022-28022/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---9
//        08-24 14:06:29.260 28022-28022/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---1
//        08-24 14:06:29.260 28022-28022/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---2
//        08-24 14:06:29.270 28022-28022/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::42::onCompleted-->>thread ---main

    }

    private void mergeDelayError(){
        //mergeDelayError操作符类似于merge操作符，可能会让合并的Observable发射的数据交错
        //唯一不同就是如果在合并途中出现错误，
        // 不会立即发射错误通知，而是保留错误直到合并后的Observable将所有的数据发射完成，
//        此时才会将onError提交给订阅者。
        //合并多个Observable也可以通过传递一个Observalbe列表List、数组
        initSubscriber();
        Observable ob = Observable.just(1,2,3).delay(200,TimeUnit.MILLISECONDS);
        Observable ob2 = Observable.just(4,5,6).delay(200, TimeUnit.MILLISECONDS);
        Observable.mergeDelayError(ob,ob2).subscribe(mSbuscriber);
        //打印结果：
//        08-24 13:57:01.460 19532-19680/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::53::onNext-->>thread---RxComputationThreadPool-3---1
//        08-24 13:57:01.460 19532-19680/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::53::onNext-->>thread---RxComputationThreadPool-3---2
//        08-24 13:57:01.470 19532-19680/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::53::onNext-->>thread---RxComputationThreadPool-3---4
//        08-24 13:57:01.470 19532-19680/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::53::onNext-->>thread---RxComputationThreadPool-3---5
//        08-24 13:57:01.470 19532-19680/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::53::onNext-->>thread---RxComputationThreadPool-3---6
//        08-24 13:57:01.470 19532-19680/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::53::onNext-->>thread---RxComputationThreadPool-3---3
//        08-24 13:57:01.470 19532-19680/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::43::onCompleted-->>thread ---RxComputationThreadPool-3
        //其他重载方法演示省略
    }

    private void merge(){
        //merge操作符将多个Observalbe发射的数据项，合并到一个Observable中再发射出去，
        // 可能会让合并的Observable发射的数据交错（concat是连接不会出现交错），
        // 如果在合并的途中出现错误，就会立即将错误提交给订阅者，将终止合并后的Observable
        //        合并多个Observable也可以通过传递一个Observalbe列表List、数组
        initSubscriber();
        Observable<Integer> ob = Observable.just(1, 2, 3).delay(100,TimeUnit.MILLISECONDS);
        Observable<Integer> ob2 = Observable.just(4, 5, 6).delay(100,TimeUnit.MILLISECONDS);
        Observable.merge(ob,ob2).subscribe(mSbuscriber);
        //打印结果：
//        08-24 13:48:55.690 12715-12838/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---1
//        08-24 13:48:55.690 12715-12838/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---2
//        08-24 13:48:55.690 12715-12838/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---4
//        08-24 13:48:55.690 12715-12838/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---5
//        08-24 13:48:55.700 12715-12838/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---6
//        08-24 13:48:55.700 12715-12838/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---RxComputationThreadPool-3---3
//        08-24 13:48:55.700 12715-12838/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::42::onCompleted-->>thread ---RxComputationThreadPool-3

        //merge其他重载，省略
    }

    private void groupJoin(){
        //groupJoin，基本和join相同，只是最后组合函数的参数不同,Func2中的参数不同
        Observable<Integer> ob = Observable.just(1, 2,3,4,5);
        Observable.just("groupJoin-").groupJoin(ob,
                new Function<String, Observable<Long>>() {
                    @Override
                    public Observable<Long> apply(@NonNull String s) throws Exception {
                        //源observable
                        LogUtil.i("源observable---s="+s);
                        return Observable.timer(50, TimeUnit.MILLISECONDS);
                    }
                },
                new Function<Integer, Observable<Long>>() {
                    @Override
                    public Observable<Long> apply(@NonNull Integer integer) throws Exception {
                        //目标observable
                        LogUtil.i("目标observable---integer="+integer);
                        return Observable.timer(100, TimeUnit.MILLISECONDS);
                    }
                },
                new BiFunction<String, Observable<Integer>, Observable<String>>() {

                    @Override
                    public Observable<String> apply(@NonNull final String s, @NonNull Observable<Integer> integerObservable) throws Exception {
                        return integerObservable.map(new Function<Integer, String>() {
                            @Override
                            public String apply(@NonNull Integer integer) throws Exception {
                                return s+integer;
                            }
                        });
                    }
                }
        ).subscribe(new Observer<Observable<String>>() {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Observable<String> stringObservable) {
                stringObservable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.i(""+s);
                    }
                });
            }
        });

    }

    private void join(){
        //join操作符将两个Observable产生的结果合并成一个新Observable对象，
        // join操作符可以控制每个Observable产生结果的生命周期。
//        使用join操作符需要4个参数，分别是：
//        源Observable所要组合的目标Observable
//        一个函数，接受从源Observable发射来的数据，并返回一个Observable，
//        这个Observable的生命周期决定了源Observable发射出来数据的有效期
//        一个函数，接受从目标Observable发射来的数据，并返回一个Observable，
//        这个Observable的生命周期决定了目标Observable发射出来数据的有效期
//        一个函数，接受从源Observable和目标Observable发射来的数据，并返回最终组合完的数据。
//        groupJoin()操作符第四个参数与join操作符不同

//        目标Observable和源Observable发射的数据都有一个有效时间限制，
//        比如目标发射了一条数据（a）有效期为3s，过了2s后，源发射了一条数据（b），
//        因为2s<3s，目标的那条数据还在有效期，所以可以组合为ab；
//        再过2s，源又发射了一条数据（c）,这时候一共过去了4s，
//        目标的数据a已经过期，所以不能组合了…
        initSubscriber();
        Observable<Integer> obs1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 1; i < 5; i++) {
                    emitter.onNext(i);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });

        //join
        Observable.just("srcObs-")
                .join(obs1,
                        //接受从源Observable发射来的数据，并返回一个Observable，
                        //这个Observable的生命周期决定了源Observable发射出来数据的有效期
                        new Function<String, Observable<Long>>() {
                            @Override
                            public Observable<Long> apply(@NonNull String s) throws Exception {
                                LogUtil.e(""+s);
                                return Observable.timer(3000, TimeUnit.MILLISECONDS);
                            }
                        },
                        //接受从目标Observable发射来的数据，并返回一个Observable，
                        //这个Observable的生命周期决定了目标Observable发射出来数据的有效期
                        new Function<Integer, Observable<Long>>() {
                            @Override
                            public Observable<Long> apply(@NonNull Integer integer) throws Exception {
                                return Observable.timer(2000, TimeUnit.MILLISECONDS);
                            }
                        },
                        //接收从源Observable和目标Observable发射来的数据，并返回最终组合完的数据
                        new BiFunction<String,Integer,String>() {
                            @Override
                            public String apply(@NonNull String str1, @NonNull Integer integer) throws Exception {
                                //LogUtil.i(str1+"---"+integer);
                                return str1 + integer;
                            }
                        })
                .subscribe(mSbuscriber);
        //打印结果：
//        08-24 16:00:24.720 4344-4344/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---srcObs-1
//        08-24 16:00:24.720 4344-4344/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---srcObs-2
//        08-24 16:00:24.720 4344-4344/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---srcObs-3
//        08-24 16:00:24.730 4344-4344/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::52::onNext-->>thread---main---srcObs-4
//        08-24 16:00:27.720 4344-4541/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorCombine.java::42::onCompleted-->>thread ---RxComputationThreadPool-1

    }

    private void combineLatest() {
        //combineLatest操作符把两个Observable产生的结果进行合并，
        // 合并的结果组成一个新的Observable
//        combineLatest操作符可以接受2-9个Observable作为参数，
//        最后一个Observable中的每一个数据项，都与前面Observable中的最后一项进行规则运算。
//        也就是call方法中的最后一个值参数是最后一个Observable的每一项数据，
//        前面的参数是前面每一个Observable的最后一项数据，固定不变的
        initSubscriber();
        Observable ob1 = Observable.just(1,3,5);
        Observable ob3 = Observable.just(2,4,6);
        Observable ob2 = Observable.just("a","b","c");
        Observable.combineLatest(ob1, ob3, ob2, new Function3<Integer,Integer,String,String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull Integer integer2, @NonNull String s) throws Exception {
                LogUtil.i("integer="+integer+"---integer2="+integer2+"---s="+s);
                return integer+integer2+s;
            }
        }).subscribe(mSbuscriber);


        //comineLatest操作集合的演示
        LogUtil.i("---combineLatest操作集合---");
        initSubscriber();
        List<Observable<Integer>> list = new ArrayList<Observable<Integer>>();
        Observable ob4 = Observable.just(1,3,5);
        Observable ob5 = Observable.just(2,4,6);
        Observable ob6 = Observable.just(7,8,9);
        list.add(ob4);
        list.add(ob5);
        list.add(ob6);
        Observable.combineLatest(list, new Function<Object[], Integer>() {
            @Override
            public Integer apply(@NonNull Object[] args) throws Exception {
                int sum = 0;
                for (Object value : args) {
                    LogUtil.i(""+value);
                    sum=(int)value+sum;
                }
                return sum;
            }
        }).subscribe(mSbuscriber);

    }
}
