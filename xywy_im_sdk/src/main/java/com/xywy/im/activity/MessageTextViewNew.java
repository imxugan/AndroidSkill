package com.xywy.im.activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xywy.im.R;
import com.xywy.im.db.IMessage;
import com.xywy.im.db.Message;
import com.xywy.im.db.MessageDB;

import org.json.JSONException;
import org.json.JSONObject;

import test.cn.example.com.util.LogUtil;


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

        if (mediaType == Message.MSGTYPE_TEXT) {
            TextView content = (TextView) findViewById(R.id.text);
            String text = msg.getContent();
            try {
                JSONObject jsonObject = new JSONObject(text);
                content.setText(jsonObject.getString("content"));
            } catch (JSONException e) {
                LogUtil.i(e.getMessage());
                e.printStackTrace();
            }

        } else {
            TextView content = (TextView) findViewById(R.id.text);
            content.setText("unknown");
        }
        requestLayout();
    }



}