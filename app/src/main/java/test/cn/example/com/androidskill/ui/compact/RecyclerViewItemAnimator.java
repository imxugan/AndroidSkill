package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/21.
 */

public class RecyclerViewItemAnimator extends AppCompatActivity implements View.OnClickListener {

    private MyItemAnimatorAdapter myItemAnimatorAdapter;
    private int index;
    private Observable<Long> observable;
    private Observer<Long> observer;
    private Disposable disposable;
    private MyItemRightInAnimator rightItemAnimator;
    private MyItemLeftInAnimator leftItemAnimator;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_itematimator);
        findViewById(R.id.item_btn_add).setOnClickListener(this);
        findViewById(R.id.item_btn_delete).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        List<String> data = new ArrayList<>();
//        for(int i= 0;i<10;i++){
//            data.add("item  "+i);
//        }
//        ViewCompat.postOnAnimationDelayed(removeDuration, adder1, this.getRemoveDuration());
        myItemAnimatorAdapter = new MyItemAnimatorAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myItemAnimatorAdapter);
        //recyclerview自带的默认动画
//        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        //模仿DefaultItemAnimator实现的动画效果，主要是实现我们自己的animateAdd( ) 、animateAddImpl( )方法
        leftItemAnimator = new MyItemLeftInAnimator();
        rightItemAnimator = new MyItemRightInAnimator();

        leftItemAnimator.setAddDuration(400);
        leftItemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(leftItemAnimator);
        palyAnimator();

    }

    private void palyAnimator(){
        //interval操作符,创建以1秒为事件间隔发送整数序列的Observable
        observable = Observable.interval(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread());
        //                myItemAnimatorAdapter.addData(0,"add");
        observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                LogUtil.i(Thread.currentThread().getName());
                if(index%2==0){
                    recyclerView.setItemAnimator(leftItemAnimator);
                }else {
                    recyclerView.setItemAnimator(rightItemAnimator);
                }
                myItemAnimatorAdapter.addData(index, "add" + index);
                index++;
                if(index == 10){
                    disposable.dispose();//实现断开Observer 与 Observable 的连接
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_btn_add:
                myItemAnimatorAdapter.addData(0,"add");
                break;
            case R.id.item_btn_delete:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != disposable && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
