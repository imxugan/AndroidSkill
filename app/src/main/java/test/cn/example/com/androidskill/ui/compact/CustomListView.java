package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by xugan on 2019/7/17.
 */

public class CustomListView extends ListView {
    private float mLastY;
    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //通知父控件，不要拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if(getFirstVisiblePosition() ==0 && ev.getY()-mLastY>0){
                    //listview滑动到顶部，且继续向下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else if(getLastVisiblePosition() == getCount() -1
                        && (ev.getY() - mLastY)<0){
                    //listview滑动到了最底部，且继续向上滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        mLastY = ev.getY();
        return super.dispatchTouchEvent(ev);
    }
}
