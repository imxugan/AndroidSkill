package test.cn.example.com.androidskill.art.chapter_seven;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.cn.example.com.androidskill.R;

public class ItemAnimatorActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private MyRecyclerAdapter4 adapter4;
    private int position;
    private UIHandler uiHandler = new UIHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_animator);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        List<String> data = new ArrayList<String>();
        adapter4 = new MyRecyclerAdapter4(this,data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter4);

        MyItemAnimator itemAnimator = new MyItemAnimator();
        itemAnimator.setAddDuration(500);
        itemAnimator.setRemoveDuration(500);
        recyclerView.setItemAnimator(itemAnimator);

        for(int i=0;i<16;i++){
            uiHandler.sendEmptyMessageDelayed(i,500*(i+1));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                if(1==position){
                    position = 2;
                }else {
                    position = 1;
                }
                adapter4.addData(position,"add item"+new Random().nextInt(100));
                break;
            case R.id.btn_delete:
                adapter4.removeData(1);
                break;
        }
    }

    private class UIHandler  extends Handler{
        WeakReference<Activity> softReference;
        public UIHandler(Activity activity){
            softReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = softReference.get();
            if(null != activity){
                int what = msg.what;
                adapter4.addData(what,"add item "+what);
                if(what == 15){
                    //这里给recyclerView设置ItemAnimator为null,
                    //是为了让后续添加的条目，不在使用这个动画,要是后续添加的 条目想继续使用这个动画，
                    //这行代码可以去掉
                    recyclerView.setItemAnimator(null);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity的onDestory时，清空所有handler中没有执行完的任务,这样在GC就能快速的回收activity
        uiHandler.removeCallbacksAndMessages(null);
    }
}
