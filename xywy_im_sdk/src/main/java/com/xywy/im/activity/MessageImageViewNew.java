package com.xywy.im.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.xywy.im.R;
import com.xywy.im.db.Message;
import com.xywy.im.db.MessageSendState;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/9/5.
 */

public class MessageImageViewNew extends MessageRowViewNew {

    protected ProgressBar uploadingProgressBar;
    protected View maskView;

    public MessageImageViewNew(Context context, boolean incomming, boolean isShowUserName) {
        super(context, incomming, isShowUserName);

        int contentLayout = R.layout.chat_content_image;

        ViewGroup group = (ViewGroup)this.findViewById(R.id.content);
        group.addView(inflater.inflate(contentLayout, group, false));
        uploadingProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
        if (!incomming) {
            maskView = findViewById(R.id.mask);
        }
    }

    public void setMessage(Message msg) {
        super.setMessage(msg);

        ImageView imageView = (ImageView)findViewById(R.id.image);
        String content = msg.getContent();
        LogUtil.e(content);
        try {
            JSONObject jsonObject = new JSONObject(content);

            String url = jsonObject.getString("content");
            LogUtil.i("url=     "+url);
            url = url.substring(5);
            LogUtil.i("url=     "+url);
            Picasso.with(context)
                    .load("file://"+url)
                    .placeholder(R.drawable.image_download_fail)
                    .into(imageView);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        boolean uploading = (msg.getSendState()== MessageSendState.MESSAGE_SEND_LISTENED);
        if (uploading) {
            if (maskView != null) {
                maskView.setVisibility(View.VISIBLE);
            }
//            uploadingProgressBar.setVisibility(View.VISIBLE);
            uploadingProgressBar.setVisibility(View.GONE);
        } else {
            if (maskView != null) {
                maskView.setVisibility(View.GONE);
            }
            uploadingProgressBar.setVisibility(View.GONE);
        }
        requestLayout();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
        if (event.getPropertyName().equals("sendState")) {
            boolean uploading = (this.messageNew.getSendState()== MessageSendState.MESSAGE_SEND_LISTENED);
            if (uploading) {
                if (maskView != null) {
                    maskView.setVisibility(View.VISIBLE);
                }
                uploadingProgressBar.setVisibility(View.VISIBLE);
            } else {
                if (maskView != null) {
                    maskView.setVisibility(View.GONE);
                }
                uploadingProgressBar.setVisibility(View.GONE);
            }
        }
    }
}
