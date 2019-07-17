package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/17.
 */

public class MyListView extends ListView {
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i("MyListView      "+ev.getAction());
        boolean dispatchTouchEvent = super.dispatchTouchEvent(ev);
        LogUtil.i("MyListView      "+dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtil.i("MyListView      "+ev.getAction());
        boolean onTouchEvent = super.onTouchEvent(ev);
        LogUtil.i("MyListView      "+onTouchEvent);
        return onTouchEvent;
    }
}
