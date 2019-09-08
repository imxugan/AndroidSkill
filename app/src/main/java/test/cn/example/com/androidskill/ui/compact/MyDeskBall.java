package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import test.cn.example.com.util.LogUtil;

public class MyDeskBall extends LinearLayout {

    private final Scroller mScroller;
    private final GestureDetector gestureDetector;
    private final int touchSlop;
    private float downX;
    private float moveX;

    public MyDeskBall(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        LogUtil.i("touchSlop=   "+touchSlop);
        mScroller = new Scroller(context);
        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtil.i("onFling=   "+e1.getX()+"     "+e1.getY());
                LogUtil.i("onFling=   "+e2.getX()+"     "+e2.getY());
                mScroller.fling((int)e2.getX(),(int)e2.getY(),(int)velocityX,(int)velocityY,10,getWidth()-150,10,getHeight()-150);
                invalidate();
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.i("ev.getAction()=   "+ev.getAction());
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getX();
                if(Math.abs(moveX-downX)>touchSlop){
                    //拦截事件
                    downX = moveX;
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("event.getAction()=   "+event.getAction());
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            LogUtil.i("computeScroll=   "+mScroller.getCurrX()+"    "+mScroller.getCurrY());
            scrollTo(-mScroller.getCurrX(),-mScroller.getCurrY());
            invalidate();
        }
    }
}
