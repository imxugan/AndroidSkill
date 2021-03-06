package com.xywy.im.activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xywy.im.R;
import com.xywy.im.db.IMessage;
import com.xywy.im.db.Message;
import com.xywy.im.db.MessageSendState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

public class MessageRowViewNew extends FrameLayout implements PropertyChangeListener {

    protected Context context;

    protected Message messageNew;

    protected View contentView;
    protected TextView nameView;

    protected LayoutInflater inflater;
    private ImageView flagView;
    private ProgressBar sendingProgressBar;

    public MessageRowViewNew(Context context) {
        super(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        inflater.inflate(
                R.layout.chat_container_center, this);
        ViewGroup group = (ViewGroup)findViewById(R.id.content);
        this.contentView = group;
    }

    public MessageRowViewNew(Context context, boolean incomming, boolean isShowUserName) {
        super(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        View convertView;
        if (!incomming) {
            convertView = inflater.inflate(
                    R.layout.chat_container_right, this);
        } else {
            convertView = inflater.inflate(
                    R.layout.chat_container_left, this);

            nameView = (TextView)convertView.findViewById(R.id.name);
            if (isShowUserName) {
                nameView.setVisibility(View.VISIBLE);
            } else {
                nameView.setVisibility(View.GONE);
            }
        }

        ViewGroup group = (ViewGroup)findViewById(R.id.content);
        this.contentView = group;
    }

    public void setMessage(Message msg) {
        if (this.messageNew != null) {
            this.messageNew.removePropertyChangeListener(this);
        }
        this.messageNew = msg;
        this.messageNew.addPropertyChangeListener(this);

        this.contentView.setTag(this.messageNew);
        LogUtil.i("content= "+msg.getContent().toString() +"   isOutgoing="+msg.getIsOutgoing());
        if (msg.getIsOutgoing()==Message.MSG_OUT) {
            int sendState = msg.getSendState();
            switch (sendState){
                case MessageSendState.MESSAGE_SEND_SUCCESS:
                    flagView = (ImageView) findViewById(R.id.flag);
                    if(null != flagView){
                        flagView.setVisibility(View.GONE);
                    }
                    sendingProgressBar = (ProgressBar) findViewById(R.id.sending_progress_bar);
                    if(null != sendingProgressBar){
                        sendingProgressBar.setVisibility(View.GONE);
                    }
                    break;
                case MessageSendState.MESSAGE_SEND_FAILED:
                    flagView = (ImageView) findViewById(R.id.flag);
                    if(null != flagView){
                        flagView.setVisibility(View.VISIBLE);
                    }
                    sendingProgressBar = (ProgressBar) findViewById(R.id.sending_progress_bar);
                    if(null != sendingProgressBar){
                        sendingProgressBar.setVisibility(View.GONE);
                    }
                    break;
                case MessageSendState.MESSAGE_SEND_LISTENED:
                    flagView = (ImageView) findViewById(R.id.flag);
                    if(null != flagView){
                        flagView.setVisibility(View.GONE);
                    }
                    sendingProgressBar = (ProgressBar) findViewById(R.id.sending_progress_bar);
                    if(null != sendingProgressBar){
                        sendingProgressBar.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        } else {
            if (nameView != null) {
//                nameView.setText(msg.getSender().toString());
                nameView.setText(msg.getSender()+"");
            }
        }

        ImageView headerView = (ImageView)findViewById(R.id.header);
        String avatar = "test";
        if (headerView != null && !TextUtils.isEmpty(avatar)) {
            Picasso.with(context)
                    .load(avatar)
                    .placeholder(R.drawable.image_download_fail)
                    .into(headerView);
        }
    }

    public View getContentView() {
        return contentView;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        ImageView flagView = (ImageView) findViewById(R.id.flag);
        ProgressBar sendingProgressBar = (ProgressBar) findViewById(R.id.sending_progress_bar);
//        LogUtil.i("msgId=   "+this.messageNew.getMsgId()+"   content=  "+this.messageNew.getContent()+"   propertyName"+event.getPropertyName());
        if (event.getPropertyName().equals("sendState")) {
            int sendState = this.messageNew.getSendState();
            switch (sendState){
                case MessageSendState.MESSAGE_SEND_SUCCESS:
                    Log.e("WebSocketApi","propertyChange            "+Thread.currentThread().getName()+"   sendState=  "+this.messageNew.getSendState());
                    flagView.setVisibility(View.GONE);
                    sendingProgressBar.setVisibility(View.GONE);
                    break;
                case MessageSendState.MESSAGE_SEND_FAILED:
                    flagView.setVisibility(View.VISIBLE);
                    sendingProgressBar.setVisibility(View.GONE);
                    break;
                case MessageSendState.MESSAGE_SEND_LISTENED:
                    flagView.setVisibility(View.GONE);
                    sendingProgressBar.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

        if("time".equals(event.getPropertyName())){
            if(MessageSendState.MESSAGE_SEND_SUCCESS == this.messageNew.getSendState()){
                ToastUtils.shortToast(context,"新的发送时间是  "+event.getNewValue());
            }
        }

        if (event.getPropertyName().equals("sender")) {
            if (nameView != null) {
                nameView.setText(this.messageNew.getSender()+"");
            }
            ImageView headerView = (ImageView)findViewById(R.id.header);
            String avatar = "abcd";
            if (headerView != null && !TextUtils.isEmpty(avatar)) {
                Picasso.with(context)
                        .load(avatar)
                        .placeholder(R.drawable.image_download_fail)
                        .into(headerView);
            }
        }
    }
}