package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/11.
 */

public class ViewDragHelperLayout2 extends LinearLayout {

    private final ViewDragHelper mViewDragHelper;
    private View mDragView;
    private View mAutoBackView;
    private View mTracingDragView;
    private final Point mTracingBackOrigin;


    public ViewDragHelperLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
//        Callback中的方法如下，其他包括一个抽象方法tryCaptureView()和十二个一般方法组成
//
//        tryCaptureView(View,int)	传递当前触摸的子View实例，如果当前的子View需要进行拖拽移动返回true
//        clampViewPositionHorizontal	决定拖拽的View在水平方向上面移动到的位置
//        clampViewPositionVertical	决定拖拽的View在垂直方向上面移动到的位置
//        getViewHorizontalDragRange	返回一个大于0的数，然后才会在水平方向移动
//        getViewVerticalDragRange	返回一个大于0的数，然后才会在垂直方向移动
//        onViewDragStateChanged	    拖拽状态发生变化回调
//        onViewPositionChanged       当拖拽的View的位置发生变化的时候回调(特指capturedview)
//
//
//        onViewCaptured	            捕获captureview的时候回调
//        onViewReleased	            当拖拽的View手指释放的时候回调
//        onEdgeTouched               当触摸屏幕边界的时候回调
//
//
//        onEdgeLock                  是否锁住边界
//        onEdgeDrageStarted          在边缘滑动的时候可以设置滑动另一个子View跟着滑动
//        getOrderedChildIndex


        LogUtil.i("ViewDragHelperLayout2");
        mTracingBackOrigin = new Point();
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            //控制子view的横向边界
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                return left;//如果仅仅是返回left，则child是不受边界限制的
                if(child == mTracingDragView){
                    //仅仅是对第三个子view进行边界限制
                    int leftBound = getPaddingLeft();
                    int rightBound = getMeasuredWidth() - child.getMeasuredWidth() - getPaddingRight();
                    int newLeft = Math.min(Math.max(leftBound,left),rightBound);
                    return newLeft;
                }else{
                    return left;
                }
            }

            //控制子view的纵向边界
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                return top;//不对子view进行边界限制
                if(child == mTracingDragView){
                    //仅仅对第三个子view进行边界限制
                    int topBound = getPaddingTop();
                    int bottomBound = getMeasuredHeight() - getPaddingBottom() - child.getMeasuredHeight();
                    int newTop = Math.min(Math.max(topBound,top),bottomBound);
                    return newTop;
                }else {
                    return top;
                }
            }

            //获取子view横向拖拽的最远距离
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            //获取子view在纵向拖拽的最远距离
            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if(releasedChild == mAutoBackView){
//                    第一个参数，就是当前的ViewGroup
//                    第二个sensitivity，主要用于设置touchSlop:
//                    helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity));
//
//                    可见传入越大，mTouchSlop的值就会越小。
//                    第三个参数就是Callback，在用户的触摸过程中会回调相关方法
                    mViewDragHelper.settleCapturedViewAt(mTracingBackOrigin.x,mTracingBackOrigin.y);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                LogUtil.i("edgeFlags="+edgeFlags+"---pointerId="+pointerId);
            }

            //当触摸屏幕边界的时候回调
            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                LogUtil.i("edgeFlags="+edgeFlags+"---pointerId="+pointerId);
            }
        });
        //如果需要使用边界检测需要添加,当社长了边界检查，onEdgeTouched才会被回调
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mTracingBackOrigin.x = mAutoBackView.getLeft();
        mTracingBackOrigin.y = mAutoBackView.getTop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mTracingDragView = getChildAt(2);
    }
}
