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
        LogUtil.i("onTouchEvent         "+event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;//这里不能返回false，否则，后续的move,up事件CustomViewPager都收不到，因为，
            //down事件传递下来后，CoustomViewPager分发给自己的子控件，比如TextView,由于TextView默认是
            //不消费事件的，所以回传给CoustomViewPager的onTouchEvent方法，但是CustomViewPager也是默认不
            //消费事件的，所以，再次将事件回传给CustomViewPager的父控件，这样当move事件到来时，由于CoustomViewPager
            //的父控件知道down事件时，自己的子控件CustomViewPager不需要事件，所以move事件就不会交给自己的子控件
            //CustomViewPager了，这就解释了，为什么这里不返回true时，触摸TextView这些CustomViewPgger的子控件
            //CustomViewPager无法滑动的原因。
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                int scrollDx = (int) (lastMoveX - moveX);
//                getScrollX()
// Return the scrolled left position of this view. This is the left edge of the displayed part of your view
// 返回这个视图滚动到的左边的位置
                LogUtil.i("getWidth()="+getWidth()+" , "+"getLeft()="+getLeft()+"  ,getScrollX()="+getScrollX()+"    "+"scrollDx="+scrollDx+"    moveX="+moveX);

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
        boolean touchEvent = super.onTouchEvent(event);
        if(MotionEvent.ACTION_DOWN == event.getAction()){
            LogUtil.e("重点看down事件时，这个返回值     "+touchEvent);
        }

        return touchEvent;
    }
}
