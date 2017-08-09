package test.cn.example.com.androidskill.rxjavaTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.City;
import test.cn.example.com.androidskill.model.House;
import test.cn.example.com.util.LogUtil;

import static test.cn.example.com.androidskill.R.id.flatMap;

/**
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
    }

    private void buffer(){
        Observable.just(1,2,3,4,5).buffer(2).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                LogUtil.i(integers.toString());
            }
        });
    }

    private void window() {
        //窗口，它可以批量或者按周期性从Observable收集数据到一个集合，
        // 然后把这些数据集合打包发射，而不是一次发射一个数据,类似于Buffer，
        // 但Buffer发射的是数据，Window发射的是Observable
        Observable.just(1,2,3,4,5,6,7).window(2).subscribe(new Action1<Observable<Integer>>() {
            @Override
            public void call(Observable<Integer> integerObservable) {
                LogUtil.i(integerObservable.toString());
                integerObservable.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
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
        Observable.just(1,2,3,4,5,6,7,8,9).groupBy(new Func1<Integer, String>() {

            @Override
            public String call(Integer integer) {
                return (integer % 2) == 0?"偶数":"奇数";
            }
        }).subscribe(new Action1<GroupedObservable<String, Integer>>() {
            @Override
            public void call(GroupedObservable<String, Integer> stringIntegerGroupedObservable) {
                String key = stringIntegerGroupedObservable.getKey();
                LogUtil.i("key="+key);
                if("偶数".equals(key)){
                    stringIntegerGroupedObservable.subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
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
        Observable.just(1,2,3,4,5).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                //interger 是上次执行的结果，integer2是此Observable的输入值
                LogUtil.i("integer="+integer);
                LogUtil.i("integer2="+integer2);
                return integer*integer2;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

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
        Observable.just("A","B","C","D").switchMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtil.i("这是在同一线程产生数据，所以当第二个数据项来临时，第一个已经完成了，同理b，c,d都将完成");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.i("使用switchMap转换    "+s);
            }
        });

        String[] arr = new String[]{"A","B","C","D"};
        Observable.from(arr).switchMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s).subscribeOn(Schedulers.newThread());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtil.i("这是在多线程产生数据,当源Observable发射一个新的数据项时，如果旧数据项订阅还未完成，就取消旧订阅数据和停止监视那个数据项产生的Observable,开始监视新的数据项");
            }

            @Override
            public void onError(Throwable e) {

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
        Observable.from(citys).flatMapIterable(new Func1<City, Iterable<House>>() {
            @Override
            public Iterable<House> call(City city) {
                return city.housesList;
            }
        }).subscribe(new Action1<House>() {
            @Override
            public void call(House house) {
                LogUtil.i("flatMapIterable转换后      "+house.price);
            }
        });
    }

    private void flatMap() {
        //flatMap的使用
//        flatMap()的原理是这样的：
//        将传入的事件对象装换成一个Observable对象；
//        这是不会直接发送这个Observable, 而是将这个Observable激活让它自己开始发送事件；
//        每一个创建出来的Observable发送的事件，都被汇入同一个Observable，
//        这个Observable负责将这些事件统一交给Subscriber的回调方法。
//        这三个步骤，把事件拆成了两级，通过一组新创建的Observable将初始的对象『铺平』之后
//        通过统一路径分发了下去。而这个『铺平』就是flatMap()所谓的flat
        City c1 = new City(23.00);
        City c2 = new City(33.00);
        City c3 = new City(53.00);
        City[] citys = {c1,c2,c3};
        Observable.from(citys).flatMap(new Func1<City, Observable<House>>() {
            @Override
            public Observable<House> call(City city) {
                return Observable.from(city.houses);
            }
        }).subscribe(new Action1<House>() {
            @Override
            public void call(House house) {
                LogUtil.i(""+house.price);
            }
        });
    }

    private void map() {
        Observable<Integer> observable = Observable.just(1,2,3,5,9,7);
        observable.map(new Func1<Integer,String>(){

            @Override
            public String call(Integer integer) {
                return "this is "+integer;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
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
            default:
                break;
        }
    }
}
