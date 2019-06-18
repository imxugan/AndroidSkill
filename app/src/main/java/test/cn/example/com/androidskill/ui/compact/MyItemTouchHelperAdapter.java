package test.cn.example.com.androidskill.ui.compact;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/18.
 */

public class MyItemTouchHelperAdapter extends RecyclerView.Adapter<MyItemTouchHelperAdapter.MyViewHolder> implements ItemMoveListener{
    private ItemDragListenter mItemDragListenter;
    private List<QQMessage> mData;
    public MyItemTouchHelperAdapter(ItemDragListenter listenter,List<QQMessage> data){
        this.mItemDragListenter = listenter;
        this.mData = data;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview_2, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int positon) {
        QQMessage qqMessage = mData.get(positon);
        myViewHolder.iv_logo.setImageResource(qqMessage.resId);
        myViewHolder.tv_title.setText(qqMessage.title);
        myViewHolder.tv_content.setText(qqMessage.content);
        myViewHolder.iv_logo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mItemDragListenter.onStartDrag(myViewHolder);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null==mData)?0:mData.size();
    }

    @Override
    public boolean onItemMove(int srcPositon, int targetPositon) {
        Collections.swap(mData,srcPositon,targetPositon);
        notifyItemMoved(srcPositon,targetPositon);
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_logo;
        private final TextView tv_title,tv_content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_logo = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
