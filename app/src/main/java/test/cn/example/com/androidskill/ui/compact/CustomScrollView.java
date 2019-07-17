package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by xugan on 2019/7/17.
 */

public class CustomScrollView extends ScrollView {

    private ListView listView;
    private float mLastY;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                ViewGroup viewGroup = (ViewGroup) getChildAt(0);
                View child = viewGroup.getChildAt(1);
                if(child instanceof ListView){
                    listView = (ListView) child;
                    //listivew滑动到顶部，且继续向下滑动
                    if(listView.getFirstVisiblePosition() == 0 && (ev.getY()-mLastY)>0){
                        //scrollview做事件拦截
                        intercept = true;
                    }else if(listView.getLastVisiblePosition() == listView.getCount() -1
                            && (ev.getY() - mLastY)<0){
                        //listivew滑动到了底部，且继续向上滑动
                        intercept = true;
                    }else {
                        intercept  = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastY = ev.getY();
        return intercept;
    }

}
