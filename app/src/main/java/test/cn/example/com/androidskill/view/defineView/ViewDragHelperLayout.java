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

public class ViewDragHelperLayout extends LinearLayout {

    private final ViewDragHelper mViewDragHelper;
    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;
    private Point mAutoBackOriginPoint = new Point();

    public ViewDragHelperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //                return false;
//                return super.clampViewPositionHorizontal(child, left, dx);
//                return super.clampViewPositionVertical(child, top, dy);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
//                tryCaptureView如何返回ture则表示可以捕获该view
                return true;
//                return child == mDragView || child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                clampViewPositionHorizontal,可以在该方法中对child移动的边界进行控制,left为即将移动到的位置
//                return left;
                if(child == mEdgeTrackerView){
                    //只是对第三个子控件进行边界控制
                    final int leftBound = getPaddingLeft();
                    final int rightBound = getWidth() - mDragView.getWidth() - leftBound;
                    final int newLeft = Math.min(Math.max(left,leftBound),rightBound);
                    LogUtil.i("newLeft="+newLeft+"---leftBound="+leftBound+"---rightBound="+rightBound+"---dx="+dx);
                    return newLeft;
                }else {
                    LogUtil.i("left="+left);
                    return left;
                }
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                clampViewPositionVertical可以在该方法中对child移动的边界进行控制,top为即将移动到的位置
//                return top;
                if(child == mEdgeTrackerView){
                    int topBound = getPaddingTop();
                    int bottomBound = getHeight() - child.getHeight() - getPaddingBottom();
                    int newTop = Math.min(Math.max(topBound,top),bottomBound);
                    LogUtil.i("topBound="+topBound+"---bottomBound="+bottomBound+"---newTop="+newTop+"--dy="+dy);
                    return newTop;
                }else {
                    return top;
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //手指释放时回调
                if(releasedChild == mAutoBackView){
                    mViewDragHelper.settleCapturedViewAt(mAutoBackOriginPoint.x,mAutoBackOriginPoint.y);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mViewDragHelper.captureChildView(mEdgeTrackerView,pointerId);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
//        return false;//这里改为返回false，则所有的子view都不能拖动了
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPoint.x = mAutoBackView.getLeft();
        mAutoBackOriginPoint.y = mAutoBackView.getTop();
    }
}
