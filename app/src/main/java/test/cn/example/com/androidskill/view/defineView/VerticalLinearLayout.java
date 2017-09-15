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
 * Created by xgxg on 2017/9/14.
 */

public class VerticalLinearLayout extends ViewGroup {

    private final int mScreenHeight;
    private boolean mIsScrolling;
    private VelocityTracker mVelocityTracker;
    private int mLastY;
    private final Scroller mScroller;
    private int mScrollStart;
    private int mScrollEnd;
    private int currentPage;
    private OnPageChangeListener mOnPageChangeListener;

    public VerticalLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取屏幕的高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight = displayMetrics.heightPixels;

        //初始化scroller
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
//            measureChild(child,widthMeasureSpec,mScreenHeight);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int childCount = getChildCount();
            MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
            params.height = mScreenHeight * childCount;
            setLayoutParams(params);//设置VerticalLinearLayout自身在父容器中的宽高
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if(child.getVisibility() != View.GONE){
                    //确定每个子view在父控件中的位置
                    child.layout(l,i * mScreenHeight,r,(i + 1) * mScreenHeight);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mIsScrolling){
           return super.onTouchEvent(event);
        }
        int action = event.getAction();
        int y = (int) event.getY();
        obtainVelocity(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mScrollStart = getScrollY();
                mLastY = y;
                LogUtil.i("mScrollStart===="+mScrollStart);
                break;
            case MotionEvent.ACTION_MOVE:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                //边界检查
                int scrollY = getScrollY();
//                LogUtil.i("scrollY===="+scrollY+"---dy="+dy+"--mLastY="+mLastY+"---y="+y);
                //已经到达顶端，下拉多少，加往上滚动多少
                if(dy < 0 && (scrollY + dy)<0){
                    dy = -scrollY;
                }
                //已经到达底部，上拉多少，就往下滚动多少
                if(dy > 0 && (scrollY + dy) > (getHeight() - mScreenHeight)){
                    dy = getHeight() - mScreenHeight - scrollY;
                }
                scrollBy(0,dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mScrollEnd = getScrollY();
                LogUtil.i("mScrollEnd===="+mScrollEnd);
                int dScrollY = mScrollEnd - mScrollStart;
                if(wantScrollToNext()){
                   if(shouldScrollToNext()){
                       mScroller.startScroll(0,getScrollY(),0,(mScreenHeight - dScrollY));
                   } else {
                       mScroller.startScroll(0,getScrollY(),0,-dScrollY);
                   }
                }

                if(wantScrollToPre()){//向下滑动
                    if(shouScrollToPre()){
                        mScroller.startScroll(0,getScrollY(),0,-mScreenHeight - dScrollY);
                    }else {
                        mScroller.startScroll(0,getScrollY(),0,-dScrollY);
                    }
                }
                mIsScrolling = true;
                postInvalidate();
                recycleVelocity();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(0,mScroller.getCurrY());
            postInvalidate();
        }else {
            int position = getScrollY()/mScreenHeight;
            if(position != currentPage){
                if(null !=mOnPageChangeListener){
                    mOnPageChangeListener.onPageChange(position);
                }
            }
            mIsScrolling = false;
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener){
        mOnPageChangeListener = listener;
    }

    public interface  OnPageChangeListener{
        void onPageChange(int page);
    }

    /**
     * 根据用户的意图，判断用户的意图是否是滚动到下一页
     * @return
     */
    private boolean wantScrollToNext() {
        return mScrollEnd > mScrollStart;
    }

    /**
     * 根据滚动的距离判断用户的意图是否是滚动到上一页
     * @return
     */
    private boolean wantScrollToPre() {
        return mScrollEnd < mScrollStart;
    }

    /**
     * 释放资源
     */
    private void recycleVelocity() {
        if(null != mVelocityTracker){
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 根据滚动的距离判断是否能够滚动到上一页
     * @return
     */
    private boolean shouScrollToPre() {
        return (-mScrollEnd + mScrollStart)>mScreenHeight/2 || Math.abs(getVolicity())>600;
    }

    /**
     * 根据滑动的距离，判读能否滚动到下一页
     * @return
     */
    private boolean shouldScrollToNext() {
        return (mScrollEnd - mScrollStart)>mScreenHeight/2 || Math.abs(getVolicity())>600;
    }

    /**
     * 初始化加速度
     * @param ev
     */
    private void obtainVelocity(MotionEvent ev) {
        if(null == mVelocityTracker){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
    }

    /**
     * 获取Y方向的加速度
     * @return
     */
    public int getVolicity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        return (int)mVelocityTracker.getYVelocity();
    }
}
