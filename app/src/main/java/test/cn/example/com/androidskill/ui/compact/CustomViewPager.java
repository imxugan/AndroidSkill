package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/17.
 */

public class CustomViewPager extends ViewGroup {

    private final int mTouchSlop;
    private int leftBound;
    private int rightBound;
    private float downX;
    private float moveX;
    private float lastMoveX;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(i*child.getMeasuredWidth(),t,(i+1)*child.getMeasuredWidth(),child.getMeasuredHeight());
        }
        if(childCount>0){
            leftBound = getChildAt(0).getLeft();
            rightBound = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        LogUtil.i(""+event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                lastMoveX = downX;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                float moveDiff = Math.abs(moveX - downX);
                LogUtil.i("moveDiff      "+moveDiff+"       mTouchSlop="+mTouchSlop+"       "+leftBound+"       "+rightBound);
                lastMoveX = moveX;
                if(moveDiff>mTouchSlop){
                    return true;
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                int scrollDx = (int) (lastMoveX - moveX);
                LogUtil.i("scrollDx      "+scrollDx+"       moveX="+moveX);

                //判断左边界，如果不断的拖拽达到了左边界，则拖拽失效
                if(getScrollX()+scrollDx<leftBound){
                    scrollTo(leftBound,0);
                    return true;
                }else if(getScrollX()+scrollDx+getWidth()>rightBound){
                    scrollTo(rightBound-getWidth(),0);
                    return true;
                }
                scrollBy(scrollDx,0);
                lastMoveX = moveX;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(event);
    }
}
