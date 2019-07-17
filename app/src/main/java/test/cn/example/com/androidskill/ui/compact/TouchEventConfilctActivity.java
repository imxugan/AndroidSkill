package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/16.
 */

public class TouchEventConfilctActivity extends AppCompatActivity {
    private float downX;

    //操作时，滑动listview中的button和滑动listview中的textview,看看效果
    //这个案例中，scrollView嵌套了listview和recyclerview,scrollview在上下滑动时，
    //会拦截事件，让触摸事件自己处理，所以要是能够在上下滑动时操作的是listview，为了让listview中的条目
    // 能够滑动，就需要在listview的触摸事件中的down或者move事件时，请求listview父控件(scollview)不要对事件进行
    //拦截，让事件传递到listiview，虽然这时，事件传递到了listview，但是listview也是会默认将事件向自己内部地方子
    //view进行传递的，所以，需要子view不消耗事件，这样事件会从子view在次传递到listview，这样listview才能处理
    //事件，这样，listview就能滑动了，这是这个案例的解决方案的原理，但是，如果listview的子条目是button的，
    //由于button是默认消费事件的，所以，即时listview的touchlisten中在down和move做了请求父控件scrollview不要
    //拦截事件，但是由于事件传递到listview，listview在将事件传递给子控件button，由于button消耗了这个事件，所以
    //事件就不会再次传递到listview，这样listview的onTouchListener中的监听触摸的事件就不会执行，这样，listview就无法
    //滑动了。但是，如果listivew中的子控件是textview就不同了，这是因为textview的clickable默认是false,所以，事件从
    //listview传递到自己的子控件textview,由于textview的clickable默认是false，所以，textview不会消耗事件，会将事件
    //传递给listview，这样listview才能够相应touchListener监听，这样才能move或者down中，请求listivew的父控件不要拦截
    //事件， 让listview处理传递过来的事件，这样listview才能够滑动

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_conflict);
        final MyListView lv = findViewById(R.id.lv);
        List<String> listData = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            listData.add("list item  "+i);
        }
        MyListViewAdapter myListViewAdapter = new MyListViewAdapter(this,listData);
        lv.setAdapter(myListViewAdapter);
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtil.i("MyListView       "+event.getAction()+"            "+lv.isClickable());
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // 获得按下时的X坐标
                        downX = event.getX();
                        lv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveDistance = event.getX()-downX;
                        LogUtil.e("TouchEventConfilctActivity      "+moveDistance);

                        break;
                    case MotionEvent.ACTION_UP:

                        break;

                }
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> data = new ArrayList<>();
        for(int i=0;i<20;i++){
            data.add("recyvlerview item  "+i);
        }
        MyAdapter myAdapter = new MyAdapter(this,data);
        recyclerView.setAdapter(myAdapter);

    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private Context mContext;
        private List<String> mData;
        public MyAdapter(Context context,List<String> data){
            this.mContext = context;
            this.mData = data;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            LogUtil.e(""+viewGroup);
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_btn_recyclerview, viewGroup, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.btn.setText(mData.get(i));
        }

        @Override
        public int getItemCount() {
            return (null == mData)?0:mData.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final Button btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn);
        }
    }
}
