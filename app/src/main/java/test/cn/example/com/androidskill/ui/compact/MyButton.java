package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/17.
 */

public class MyButton extends Button {
    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.i("MyButton         "+event.getAction());
        boolean dispatchTouchEvent = super.dispatchTouchEvent(event);
        LogUtil.i("MyButton         "+dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("MyButton         "+event.getAction());
        boolean onTouchEvent = super.onTouchEvent(event);
        LogUtil.i("MyButton         "+onTouchEvent);
        return onTouchEvent;
    }
}
