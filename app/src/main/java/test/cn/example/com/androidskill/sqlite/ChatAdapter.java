package test.cn.example.com.androidskill.sqlite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.sqlite.Message;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/8/15.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder> {
    private final Context context;
    private final List<Message> data;

    public ChatAdapter(Context context, List<Message> list){
        this.context = context;
        this.data = list;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.recyclerview_chat_item, parent, false);
        return new MyHolder(root);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        LogUtil.i("  ---position="+position);
        Message message = data.get(position);
        LogUtil.i("message.getIsOutgoing()="+message.getIsOutgoing()+"  ---position="+position);
        if(message.getIsOutgoing() == 1){ //0 表示接收到的消息，1 表示发送出去的消息
            holder.ll_receiver.setVisibility(View.GONE);
            holder.ll_sender.setVisibility(View.VISIBLE);
            holder.tv_sender.setText(message.getContent());
        }else {
            holder.ll_receiver.setVisibility(View.VISIBLE);
            holder.ll_sender.setVisibility(View.GONE);
            holder.tv_receiver.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        LogUtil.i("data.size()="+data.size());
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private final View ll_receiver;
        private final ImageView iv_receiver;
        private final TextView tv_receiver;
        private final View ll_sender;
        private final ImageView iv_sender;
        private final TextView tv_sender;

        public MyHolder(View itemView) {
            super(itemView);
            ll_receiver = itemView.findViewById(R.id.ll_receiver);
            iv_receiver = (ImageView) itemView.findViewById(R.id.iv_receiver);
            tv_receiver = (TextView) itemView.findViewById(R.id.tv_receiver);
            ll_sender = itemView.findViewById(R.id.ll_sender);
            iv_sender = (ImageView) itemView.findViewById(R.id.iv_sender);
            tv_sender = (TextView) itemView.findViewById(R.id.tv_sender);
        }
    }
}
