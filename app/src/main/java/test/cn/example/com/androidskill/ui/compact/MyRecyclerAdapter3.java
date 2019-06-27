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
 * Created by xugan on 2019/6/27.
 */

public class MyRecyclerAdapter3 extends RecyclerView.Adapter<MyRecyclerAdapter3.MyViewHolder> {
    private List<String> mData;

    public MyRecyclerAdapter3(Context context, List<String> data) {
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.tv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return (null == mData) ? 0 : mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
