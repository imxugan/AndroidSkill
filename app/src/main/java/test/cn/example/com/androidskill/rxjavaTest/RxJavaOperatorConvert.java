package test.cn.example.com.androidskill.rxjavaTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.City;
import test.cn.example.com.androidskill.model.House;
import test.cn.example.com.util.LogUtil;

import static test.cn.example.com.androidskill.R.id.flatMap;

/**
 * rxJava转换符的使用
 * Created by xgxg on 2017/8/4.
 */

public class RxJavaOperatorConvert extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_operator_convert);
        initView();
//        链接：https://www.zhihu.com/question/32209660/answer/63984697

//      变换操作这些操作符可用于对Observable发射的数据进行变换，详细解释可以看每个操作符的文档
//      Buffer — 缓存，可以简单的理解为缓存，它定期从Observable收集数据到一个集合，
//          然后把这些数据集合打包发射，而不是一次发射一个
//      FlatMap — 扁平映射，将Observable发射的数据变换为Observables集合，
//          然后将这些Observable发射的数据平坦化的放进一个单独的Observable，
//          可以认为是一个将嵌套的数据结构展开的过程。
//      GroupBy — 分组，将原来的Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，
//          每一个Observable发射一组不同的数据Map — 映射，通过对序列的每一项都应用一个函数
//          变换Observable发射的数据，实质是对序列中的每一项执行一个函数，函数的参数就是这个数据项
//      Scan — 扫描，对Observable发射的每一项数据应用一个函数，然后按顺序依次发射这些值
//      Window — 窗口，定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，
//          而不是每次发射一项。类似于Buffer，但Buffer发射的是数据，Window发射的是Observable，
//          每一个Observable发射原始Observable的数据的一个子集




    }

    private void initView() {
        Button map = (Button) findViewById(R.id.map);
        map.setOnClickListener(this);
        Button flatMap = (Button) findViewById(R.id.flatMap);
        flatMap.setOnClickListener(this);
        Button flatMapIterable = (Button) findViewById(R.id.flatMapIterable);
        flatMapIterable.setOnClickListener(this);
        Button switchMap = (Button) findViewById(R.id.switchMap);
        switchMap.setOnClickListener(this);
        Button scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(this);
        Button groupBy = (Button) findViewById(R.id.groupBy);
        groupBy.setOnClickListener(this);
        Button window = (Button) findViewById(R.id.window);
        window.setOnClickListener(this);
        Button buffer = (Button) findViewById(R.id.buffer);
        buffer.setOnClickListener(this);
        Button cast = (Button) findViewById(R.id.cast);
        cast.setOnClickListener(this);
        Button concatMap = (Button) findViewById(R.id.concatMap);
        concatMap.setOnClickListener(this);
    }

    private void concatMap(){
        //concatMap和flatMap最大的区别是concatMap发射的数据集是有序的，flatMap发射的数据集是无序的
//        subscribeOn()和observeOn()的区别
//
//        subscribeOn()主要改变的是订阅的线程，即call()执行的线程;
//        observeOn()主要改变的是发送的线程，即onNext()执行的线程。
        Observable.just(1,2,3,4,5,6,7).concatMap(new Function<Integer, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(@NonNull Integer integer) throws Exception {
                return Observable.just(integer).subscribeOn(Schedulers.io());
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onComplete() {
                LogUtil.i("onCompleted---threadId="+Thread.currentThread().getId());
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i("onNext---threadId="+Thread.currentThread().getId()+"---integer="+integer);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    private void cast(){
        //cast操作符就是将不同数据类型转换成指定类型. 可以用于校验是否是同一种类型
        Observable.just(1,2,3,4,5).cast(Integer.class).subscribe(new Observer<Integer>() {
            @Override
            public void onError(Throwable e) {
                LogUtil.i(e.toString());//如果类型转换失败，就会包异常
            }

            @Override
            public void onComplete() {
                LogUtil.i("onCompleted");//onCompleted
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.i(integer+"");//1,2,3,4,5
            }
        });

    }

    private void buffer(){
        //它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个
        //注意：如果原来的Observable发射了一个onError通知，Buffer会立即传递这个通知，
        // 而不是首先发射缓存的数据，即使在这之前缓存中包含了原始Observable发射的数据
        Observable.just(1,2,3,4,5).buffer(2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                LogUtil.i(integers.toString());
                //输出结果
                //[1, 2]
                // [3, 4]
                // [5]
            }
        });

        List<Integer> arr = new ArrayList<>();
        for (int i=0;i<=10;i++){
            arr.add(i);
        }
        //buffer(2,3)就要这么算：首先，将集合中的元素按照skip指定的间隔分成几个小的集合，
        //在在这些小的集合中，取前两个元素，如果这些小集合中的元素不够两个，就只取第一个。
        //buffer还有其他的一些重载
        Observable.fromArray(arr).buffer(2,3).subscribe(new Consumer<List<List<Integer>>>() {
            @Override
            public void accept(List<List<Integer>> lists) throws Exception {
                LogUtil.i(lists.toString());
                //[0, 1]
                //[3, 4]
                //[6, 7]
                //[9, 10]
            }
        });
    }

    private void window() {
        //窗口，它可以批量或者按周期性从Observable收集数据到一个集合，
        // 然后把这些数据集合打包发射，而不是一次发射一个数据,类似于Buffer，
        // 但Buffer发射的是数据，Window发射的是Observable
        Observable.just(1,2,3,4,5,6,7).window(2).subscribe(new Consumer<Observable<Integer>>() {
            @Override
            public void accept(Observable<Integer> integerObservable) throws Exception {
                LogUtil.i(integerObservable.toString());
                integerObservable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(""+integer);
                    }
                });
            }
        });
    }

    private void groupBy(){
        //  http://blog.csdn.net/wbwjx/article/details/51216960
        //  http://blog.csdn.net/new_abc/article/details/52981241
        //groupBy操作符
        // GroupBy操作符将原始Observable发射的数据按照key来拆分成一些小的Observables集合，
        // 然后这些小的Observable分别发射其所包含的的数据，类似于sql里面的groupBy
        //需要注意的是：
        //groupBy将原始Observable分解为一个发射多个GroupedObservable的Observable，一旦有订阅，
        // 每个GroupedObservable就开始缓存数据。
        //如果你忽略这些GroupedObservable中的任何一个，GroupedObservable 中的任何一个，
        // 这个缓存可能形成一个潜在的内存泄露。
        Observable.just(1,2,3,4,5,6,7,8,9).groupBy(new Function<Integer, String>() {

            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return (integer % 2) == 0?"偶数":"奇数";
            }
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {
                String key = stringIntegerGroupedObservable.getKey();
                LogUtil.i("key="+key);
                if("偶数".equals(key)){
                    stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            LogUtil.i(""+integer);
                        }
                    });
                }
            }
        });
    }

    private void scan(){
        //scan转换符
        //sacn操作符是遍历源Observable产生的结果，再按照自定义规则进行运算，依次输出每次计算后的结果给订阅者:
        //call 回掉第一个参数是上次的结算结果，第二个参数是当此的源observable的输入值
        //下面是依次做乘法的例子,第一次并不会执行call方法，
        //注意:当执行原Observable输入的第一个时,并不会回掉call函数，也就是说第一次并不参与运算，直接输给订阅者
        Observable.just(1,2,3,4,5).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                //interger 是上次执行的结果，integer2是此Observable的输入值
                LogUtil.i("integer="+integer);
                LogUtil.i("integer2="+integer2);
                return integer*integer2;
            }
        }).subscribe(new Observer<Integer>() {
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
            public void onNext(Integer integer) {
                LogUtil.i("================scan转换符  "+integer);
            }
        });
    }

    private void switchMap(){
        //switchMap使用场景：
        //多用于频繁的网络请求，如EditText输入联想。
        //由于输入的不断变化，返回的结果和输入框字符的不同步，且多线程同时运行，易发生错误，
        // 用switchMap取消上次请求

        //switchMap使用
        //switchMap()和flatMap()很像，除了一点:当源Observable发射一个新的数据项时，
        // 如果旧数据项订阅还未完成，就取消旧订阅数据和停止监视那个数据项产生的Observable,
        // 开始监视新的数据项
        Observable.just("A","B","C","D").switchMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                return Observable.just(s);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                LogUtil.i("这是在同一线程产生数据，所以当第二个数据项来临时，第一个已经完成了，同理b，c,d都将完成");
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i("使用switchMap转换    "+s);
            }
        });

        String[] arr = new String[]{"A","B","C","D"};
        Observable.fromArray(arr).switchMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                return Observable.just(s).subscribeOn(Schedulers.newThread());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                LogUtil.i("这是在多线程产生数据,当源Observable发射一个新的数据项时，如果旧数据项订阅还未完成，就取消旧订阅数据和停止监视那个数据项产生的Observable,开始监视新的数据项");
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i("使用switchMap转换,使用多线程    "+s);
            }
        });
    }

    private void flatMapIterable() {
        City c1 = new City(23.00);
        City c2 = new City(33.00);
        City c3 = new City(53.00);
        City[] citys = {c1,c2,c3};
        //flatMapIterable()和flatMap()几乎是一样的，
        // 不同的是flatMapIterable()它转化的多个Observable是使用Iterable作为源数据的
        Observable.fromArray(citys).flatMapIterable(new Function<City, Iterable<House>>() {
            @Override
            public Iterable<House> apply(@NonNull City city) throws Exception {
                return city.housesList;
            }
        }).subscribe(new Consumer<House>() {
            @Override
            public void accept(House house) throws Exception {
                LogUtil.i("flatMapIterable转换后      "+house.price);
            }
        });
    }

    private void flatMap() {
        //flatMap的使用
        //map和flatMap
        // 相同点：都是讲传入的参数转化之后返回另一个对象,
        // 不同点：flatmap()返回的是Observable对象

//        flatMap()的原理是这样的：
//        将传入的事件对象装换成一个Observable对象；
//        这是不会直接发送这个Observable, 而是将这个Observable激活让它自己开始发送事件；
//        每一个创建出来的Observable发送的事件，都被汇入同一个Observable，
//        这个Observable负责将这些事件统一交给Subscriber的回调方法。
//        这三个步骤，把事件拆成了两级，通过一组新创建的Observable将初始的对象『铺平』之后
//        通过统一路径分发了下去。而这个『铺平』就是flatMap()所谓的flat
        City c1 = new City(23.00);
        City c2 = new City(34.00);
        City c3 = new City(45.00);
        City c4 = new City(56.00);
        City c5 = new City(78.00);
        City c6 = new City(89.00);
        City[] citys = {c1,c2,c3,c4,c5,c6};
        Observable.fromArray(citys).flatMap(new Function<City, Observable<House>>() {
            @Override
            public Observable<House> apply(@NonNull City city) throws Exception {
                return Observable.fromArray(city.houses).subscribeOn(Schedulers.io());
            }
        }).subscribe(new Consumer<House>() {
            @Override
            public void accept(House house) throws Exception {
                LogUtil.i(""+house.price+"---threadId="+Thread.currentThread().getId());//打印的结果是无序的
                //需要多试几次，才可能看到打印的结果是无序的
//                08-22 19:55:39.680 26138-26138/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorConvert.java::357::call-->>23.0---threadId=1
//                08-22 19:55:39.690 26138-26138/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorConvert.java::357::call-->>34.0---threadId=1
//                08-22 19:55:39.690 26138-26725/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorConvert.java::357::call-->>45.0---threadId=1970
//                08-22 19:55:39.700 26138-26726/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorConvert.java::357::call-->>78.0---threadId=1971
//                08-22 19:55:39.700 26138-26726/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorConvert.java::357::call-->>56.0---threadId=1971
//                08-22 19:55:39.710 26138-26726/test.cn.example.com.androidskill I/MY_LOG: RxJavaOperatorConvert.java::357::call-->>89.0---threadId=1971
            }
        });
    }

    private void map() {
        //map():对Observable发射的每一项数据使用函数执行变换操作，然后在发射出去。
        // 返回的对象可以随便指定，可以实现一对一的转换
        Observable<Integer> observable = Observable.just(1,2,3,5,9,7);
        observable.map(new Function<Integer, String>(){
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                //将Integer转换成String
                return "this is "+integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtil.i(s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map:
                map();
                break;
            case flatMap:
                flatMap();
                break;
            case R.id.flatMapIterable:
                flatMapIterable();
                break;
            case R.id.switchMap:
                switchMap();
                break;
            case R.id.scan:
                scan();
                break;
            case R.id.groupBy:
                groupBy();
                break;
            case R.id.window:
                window();
                break;
            case R.id.buffer:
                buffer();
                break;
            case R.id.cast:
                cast();
                break;
            case R.id.concatMap:
                concatMap();
                break;
            default:
                break;
        }
    }
}
