package test.cn.example.com.androidskill.rxjava2Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xugan on 2019/4/22.
 */

public class RxJava2Test {
    public static void main(String[] args){
        test();
        test2();
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
