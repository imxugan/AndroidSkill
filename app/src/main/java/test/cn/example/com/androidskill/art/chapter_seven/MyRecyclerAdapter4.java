package test.cn.example.com.androidskill.art.chapter_seven;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.cn.example.com.androidskill.R;

public class MyRecyclerAdapter4 extends RecyclerView.Adapter<MyRecyclerAdapter4.MyViewHolder> {
    private Context mContext;
    private List<String> mData;
    public MyRecyclerAdapter4(Context context, List<String> data){
        mContext = context;
        mData = data;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview4, viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.itemView.setTag(position);
        myViewHolder.tv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return (null == mData)?0:mData.size();
    }

    public void addData(int position,String itemData) {
        mData.add(position,itemData);
        //注意这里，使用的是notifyItemInserted，这样ItemAnimator才起作用
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mData.remove(position);
        //注意这里，使用的是notifyItemRemoved，这样ItemAnimator才起作用
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
