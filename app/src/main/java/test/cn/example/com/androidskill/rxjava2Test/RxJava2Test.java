package test.cn.example.com.androidskill.rxjava2Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xugan on 2019/4/22.
 */

public class RxJava2Test {
    public static void main(String[] args){
//        test();
//        test2();
//        testMap();

        testFlatMap();
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
}
