package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/21.
 */

public class RecyclerViewItemAnimator extends AppCompatActivity implements View.OnClickListener {

    private MyItemAnimatorAdapter myItemAnimatorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_itematimator);
        findViewById(R.id.item_btn_add).setOnClickListener(this);
        findViewById(R.id.item_btn_delete).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
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
        MyItemAnimator defaultItemAnimator = new MyItemAnimator();

        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);

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
}
