package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import test.cn.example.com.util.LogUtil;

public class CustomViewPager2 extends ViewGroup {

    private final int touchSlop;
    private float downX;
    private float moveX;
    private final Scroller mScroller;
    private int leftBoard;
    private int rightBoard;

    public CustomViewPager2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        int scaledPagingTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        LogUtil.i("touchSlop"+touchSlop+"        scaledPagingTouchSlop="+scaledPagingTouchSlop);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.i("changed=  "+changed+"        width="+(r-l));
        if(changed){
            int childCount = getChildCount();
            View child ;
            int childLeft =0 ,childTop=0,childRight=0,childBottom=0;
            for (int i = 0; i < childCount; i++) {
                child = getChildAt(i);
                //注意，这里获取宽和高要用getMeasuredWidth方法，不能用getWidth方法， 因为子view还未layout，
                // 所以，通过getWidth获取的宽高就是0
                childLeft = i*child.getMeasuredWidth();
                childRight = childLeft+child.getMeasuredWidth();
                childBottom = childTop+child.getMeasuredHeight();
                LogUtil.i("childLeft=  "+childLeft+"    childRight="+childRight+"   childBottom="+childBottom);
                child.layout(childLeft,childTop,childRight,childBottom);
                if(i == childCount-1){
                    rightBoard = childRight;
                }
            }
            leftBoard = 0;
            LogUtil.i("左边界：  "+leftBoard+"           右边界：  "+rightBoard);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.i(ev.getAction()+"     downX="+downX);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getX();
                if(Math.abs(moveX-downX)>50){
                    downX = moveX;
                    LogUtil.i("拦截事件后的downX="+downX);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getX();
                int dx = (int) (moveX - downX);
                LogUtil.i("ACTION_MOVE      dx="+dx+"      getScrollX()="+getScrollX());
                if(getScrollX()-dx<leftBoard){
                    scrollTo(leftBoard,0);
                }else if(getScrollX()-dx+getWidth()>=rightBoard){
                    //这里要注意一点：getWidth()方法返回的是CustomViewPager2这个自定义View的宽度，
                    //这个宽度不会因为其包含的子view的增加而发生变化
                    LogUtil.i("(rightBoard-getWidth())="+(rightBoard-getWidth())+"      getWidth()="+getWidth());
                    scrollTo(rightBoard-getWidth(),0);
                }else {
                    scrollBy(-dx,0);
                }
                downX = moveX;
                return true;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx2 = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx2, 0);
                invalidate();
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        LogUtil.i("computeScroll");
        if(mScroller.computeScrollOffset()){
            LogUtil.i("mScroller.getCurrX()=    "+mScroller.getCurrX());
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
