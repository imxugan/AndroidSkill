package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/18.
 */

public class VerticalLinearLayout2 extends ViewGroup {

    private final int mScreenHeight;
    private final Scroller mScroller;
    private final VelocityTracker mVelocityTracker;
    private float mLastY;
    private int mScrollYDown;
    private float mScrollUp;
    private boolean mIsScrolling;

    public VerticalLinearLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取屏幕的高度
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight = displayMetrics.heightPixels;
        //初始化Scroller
        mScroller = new Scroller(context);
        //初始化速度跟踪器
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        View child = null;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.height = mScreenHeight * childCount;
        setLayoutParams(params);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //确定子view的位置
        int childCount = getChildCount();
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.height = mScreenHeight * childCount;
        setLayoutParams(params);
        View child = null;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            child.layout(l,i* mScreenHeight,r,(i + 1)*mScreenHeight);
        }


    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(mIsScrolling){
//            return super.onTouchEvent(event);
//        }
//        float y = event.getY();
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                mScrollYDown = getScrollY();
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float dy = mLastY - y ;
//                int scrollMove = getScrollY();
//                if(dy < 0){//向下滑动
//                    if((scrollMove + dy)< 0){//表示已经滑动到了顶部了
//                        //已经滑到顶部了，这时继续向下滑动，就不在滑动了
//                        scrollBy(0,0);
//                    }else {
////                        //如果滑动的距离大于屏幕高度的一半，则将上一个子控件完全显示在屏幕上
////                        if(Math.abs(scrollMove - mScrollYDown)>mScreenHeight/2){
//////                            scrollBy(0,(int)(scrollMove-(mScreenHeight - Math.abs(dy))));
////                            scrollBy(0,(int)((mScreenHeight - Math.abs(dy))));
////                        }else {
////                            scrollBy(0,(int)Math.abs(dy));
////                        }
//
//                    }
//                }else {
//                    //向上滑动
//                    if((scrollMove + mScreenHeight + dy)>getHeight()){//到达底部
//                        //滑动到达了底部，这时继续向上滑动，就不在滑动了
//                        scrollBy(0,0);
//                    }else {
////                        //未到达底部
////                        if(Math.abs(scrollMove - mScrollYDown)>mScreenHeight/2){//如果滑动的距离超过了屏幕高度的一半，则将下一个子控件完全显示在屏幕上
////                            scrollBy(0,(int)((mScreenHeight - Math.abs(dy))));
////                        }else {
////                            //将当前子控件移动到初始位置
////                            scrollBy(0,(int)Math.abs(dy));
////                        }
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                mScrollUp = getScrollY();
//                int moveYOffset = (int)(mLastY - y);
//                if(moveYOffset < 0){//向下滑动
//                    if((mScrollUp + moveYOffset)< 0){//表示已经滑动到了顶部了
//                        //已经滑到顶部了，这时继续向下滑动，就不在滑动了
//                        scrollBy(0,0);
//                    }else {
//                        //如果滑动的距离大于屏幕高度的一半，则将上一个子控件完全显示在屏幕上
//                        if(Math.abs(moveYOffset - mScrollYDown)>mScreenHeight/2){
//                            mScroller.startScroll(0,getScrollY(),0,(mScreenHeight - Math.abs(moveYOffset)));
//                        }else {
//                            mScroller.startScroll(0,getScrollY(),0,Math.abs(moveYOffset));
//                        }
//
//                    }
//                }else {
//                    //向上滑动
//                    if((mScrollUp + mScreenHeight + moveYOffset)>getHeight()){//到达底部
//                        //滑动到达了底部，这时继续向上滑动，就不在滑动了
//                        scrollBy(0,0);
//                    }else {
//                        //未到达底部
//                        if(Math.abs(mScrollUp - mScrollYDown)>mScreenHeight/2){//如果滑动的距离超过了屏幕高度的一半，则将下一个子控件完全显示在屏幕上
//                            mScroller.startScroll(0,getScrollY(),0,(mScreenHeight - Math.abs(moveYOffset)));
//                        }else {
//                            //将当前子控件移动到初始位置
//                            mScroller.startScroll(0,getScrollY(),0,Math.abs(moveYOffset));
//                        }
//                    }
//                }
//                mIsScrolling =true;
//                break;
//        }
//        return true;//自己处理
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mIsScrolling){
            return super.onTouchEvent(event);
        }
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //mScroller.computeScrollOffset(),返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成
        if(mScroller.computeScrollOffset()){
            scrollTo(0,mScroller.getCurrY());
            postInvalidate();
        }else {
            int position = getScrollY()/mScreenHeight;
            LogUtil.i("position========"+position);
            mIsScrolling = false;
        }
    }
}
