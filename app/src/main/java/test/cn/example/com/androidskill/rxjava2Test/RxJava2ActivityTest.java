package test.cn.example.com.androidskill.rxjava2Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/22.
 */

public class RxJava2ActivityTest extends AppCompatActivity implements View.OnClickListener {

    private EditText et;
    private Button btn_throttleFirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
        findViewById(R.id.btn_testMap).setOnClickListener(this);
        findViewById(R.id.btn_testFlatMap).setOnClickListener(this);
        btn_throttleFirst = findViewById(R.id.btn_throttleFirst);
        btn_throttleFirst.setOnClickListener(this);
        findViewById(R.id.btn_merge).setOnClickListener(this);
        et = findViewById(R.id.et);
        RxTextView.textChanges(et).debounce(200, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        LogUtil.i(charSequence.toString());
                        return charSequence.toString().trim().length()>0;
                    }
                })
                //这里使用switchMap更好，switchMap，当上一个任务尚未完成时，就开始下一个任务的话，上一个任务就会被取消掉
                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull CharSequence charSequence) throws Exception {
                        //模拟网络返回结果
                        LogUtil.w(charSequence.toString());
                        List<String> resultData = new ArrayList<String>();
                        resultData.add("abc");
                        resultData.add("bbc");
                        return Observable.just(resultData);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        LogUtil.e(strings.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private static void testFlatMap() {
        List<List<ResultBean>> data = new ArrayList<>();
        for(int i=0;i<10;i++){
            ArrayList<ResultBean> resultBeanArrayList = new ArrayList<>();
            for(int k=0;k<10;k++){
                resultBeanArrayList.add(new ResultBean(k,"jack ma"+i+k));
            }
            data.add(resultBeanArrayList);
        }
        Observable.just(data).flatMap(new Function<List<List<ResultBean>>, ObservableSource<List<ResultBean>>>() {
            @Override
            public ObservableSource<List<ResultBean>> apply(@NonNull final List<List<ResultBean>> lists) throws Exception {
//                Observable<List<ResultBean>> listObservable = Observable.just(lists.get(0));
//                return listObservable;
                return Observable.create(new ObservableOnSubscribe<List<ResultBean>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<ResultBean>> emitter) throws Exception {
                        for(int i=0;i<lists.size();i++){
                            emitter.onNext(lists.get(i));
                        }
                    }
                });
            }
        }).flatMap(new Function<List<ResultBean>, ObservableSource<ResultBean>>() {
            @Override
            public ObservableSource<ResultBean> apply(@NonNull final List<ResultBean> resultBeen) throws Exception {
                System.out.println(resultBeen);
                return Observable.create(new ObservableOnSubscribe<ResultBean>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<ResultBean> emitter) throws Exception {
                        for(ResultBean bean:resultBeen){
                            emitter.onNext(bean);
                        }
                    }
                });
            }
        }).subscribe(new Consumer<ResultBean>() {
            @Override
            public void accept(ResultBean resultBean) throws Exception {
                System.out.println(resultBean.id+"        "+resultBean.name);
            }
        });
    }

    private static void testMap() {
        Observable.just(1,2,3,4,5).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer+"";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    private static void test2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("abc");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe    d.isDisposed()="+d.isDisposed()+"     "+Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println(s+"     "+Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage()+"     "+Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete      "+ Thread.currentThread().getName());
            }
        });
    }

    private static void test() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                //必须要调用onComplete()方法，否则，看不到打印结果
                emitter.onComplete();
            }
        });
        Observer<Integer> observer = new Observer<Integer>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                this.disposable = d;
                System.out.println("onSubscribe     "+Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull Integer s) {
                //isDisposed()方法，是否取消了订阅关系
                System.out.println(s+"      "+disposable.isDisposed()+"     "+Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError"+"      "+Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete"+"       "+Thread.currentThread().getName());
            }
        };
        observable.subscribe(observer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:
                test();
                break;
            case R.id.btn_test2:
                test2();
                break;
            case R.id.btn_testMap:
                testMap();
                break;
            case R.id.btn_testFlatMap:
                testFlatMap();
                break;
            case R.id.btn_throttleFirst:
                testThrottleFirst();
                break;
            case R.id.btn_merge:
                tetMerge();
                break;
        }
    }

    private void tetMerge() {
        Observable<String> observableLocal = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                //模拟重数据库读取数据
                Thread.sleep(new Random().nextInt(500));
                emitter.onNext("data from local");
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observableNetWork = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                //模拟网络延时
                Thread.sleep(new Random().nextInt(500));
                emitter.onNext("data form network");
            }
        }).subscribeOn(Schedulers.io());

        Observable.merge(observableLocal,observableNetWork).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            private Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                this.mDisposable = d;
                LogUtil.i("onSubscribe   "+d.isDisposed());
            }

            @Override
            public void onNext(@NonNull String s) {
                if(!mDisposable.isDisposed()){
                    LogUtil.i(s);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i("onError    "+e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.i("onComplete");
            }
        });
    }

    private void testThrottleFirst() {
        RxView.clicks(btn_throttleFirst).throttleFirst(1,TimeUnit.SECONDS).subscribe(new Observer<Unit>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Unit unit) {
                LogUtil.i(System.currentTimeMillis()+"");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
