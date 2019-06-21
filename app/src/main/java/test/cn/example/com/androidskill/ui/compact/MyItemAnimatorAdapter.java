package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/21.
 */

public class MyItemAnimatorAdapter extends RecyclerView.Adapter<MyItemAnimatorAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mData;
    public MyItemAnimatorAdapter(Context context, List<String> data){
        this.mContext = context;
        this.mData = data;
    }

    public void addData(int positon,String s){
        this.mData.add(positon,s);
        notifyItemInserted(positon);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_itemanimator, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        viewHolder.tv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
