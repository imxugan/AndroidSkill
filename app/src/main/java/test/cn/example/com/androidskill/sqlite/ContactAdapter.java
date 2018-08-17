package test.cn.example.com.androidskill.sqlite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/8/15.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyHolder> implements View.OnClickListener, View.OnLongClickListener {
    private final Context context;
    private final List<String> data;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public ContactAdapter(Context context, List<String> list){
        this.context = context;
        this.data = list;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.recyclerview_contact_item,parent,false);
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        return new MyHolder(root);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        LogUtil.i("  ---position="+position);
        String contactName = data.get(position);
        holder.tv_userName.setText(contactName);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {
        if(null != mOnItemClickListener){
            int position = (int) view.getTag();
            mOnItemClickListener.onItemClick(view,position,data.get(position));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(null != mOnItemLongClickListener){
            int position = (int) view.getTag();
            mOnItemLongClickListener.onItemLongClick(view,position,data.get(position));
        }
        return true;//这里要接的返回true,如果返回false，则点击事件也会触发
    }

    class MyHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView tv_userName;
        public MyHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_userName = (TextView) itemView.findViewById(R.id.tv_userName);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position,String data);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position,String data);
    }
}
