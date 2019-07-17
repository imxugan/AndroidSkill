package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/16.
 */

public class TouchEventConfilctActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_conflict2);
        final ListView lv = findViewById(R.id.lv);
        List<String> listData = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            listData.add("list item  "+i);
        }
        MyListViewAdapter myListViewAdapter = new MyListViewAdapter(this,listData);
        lv.setAdapter(myListViewAdapter);

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
