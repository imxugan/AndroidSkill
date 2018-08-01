package com.xywy.im.activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xywy.im.R;
import com.xywy.im.db.IMessage;
import com.xywy.im.db.Message;
import com.xywy.im.db.MessageDB;


public class MessageTextViewNew extends MessageRowViewNew {
    public MessageTextViewNew(Context context, boolean incomming, boolean isShowUserName) {
        super(context, incomming, isShowUserName);
        final int contentLayout;
        contentLayout = R.layout.chat_content_text;

        ViewGroup group = (ViewGroup)this.findViewById(R.id.content);
        group.addView(inflater.inflate(contentLayout, group, false));
    }

    @Override
    public void setMessage(Message msg) {
        super.setMessage(msg);

        int mediaType = msg.getMsgType();

        if (mediaType == 0) {
            TextView content = (TextView) findViewById(R.id.text);
            String text = msg.getContent();
            content.setText(text);
        } else {
            TextView content = (TextView) findViewById(R.id.text);
            content.setText("unknown");
        }
        requestLayout();
    }



}