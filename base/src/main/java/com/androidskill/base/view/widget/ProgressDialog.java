package com.androidskill.base.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.androidskill.base.R;

public class ProgressDialog extends Dialog {

    private TextView txt;

    public ProgressDialog(Context context, String content) {
        super(context, R.style.progress_dialog);
        // 加载布局文件
        View view = View.inflate(context, R.layout.progress_dialog, null);
        txt = (TextView) view.findViewById(R.id.progress_dialog_txt);
        txt.setText(content);
        getWindow().setBackgroundDrawableResource(R.drawable.loading_bg);
        // dialog添加视图
        setContentView(view);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示对话框
     */
    public void showProgersssDialog() {
        if (!this.isShowing()) {
            this.show();
        }
    }

    /**
     * 关闭对话框
     */
    public void closeProgersssDialog() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }
}