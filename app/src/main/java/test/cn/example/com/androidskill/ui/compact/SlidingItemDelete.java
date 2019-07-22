package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import test.cn.example.com.util.LogUtil;


/**
 * Created by xugan on 2019/7/22.
 */

public class SlidingItemDelete extends LinearLayout {

    private final Scroller mScroller;
    private View leftView;
    private View rightView;
    private float startX;
    private float startY;
    private float dx;
    private float dy;

    public SlidingItemDelete(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        //Scroller中第三个参数，代表是否使用"惯性轮"
        mScroller = new Scroller(context,null,false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftView = getChildAt(0);
        rightView = getChildAt(1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                super.dispatchTouchEvent(ev);//这里要调用这个方法，否则子控件无法响应点击事件
                return true;
            case MotionEvent.ACTION_MOVE:
                dx = ev.getX() - startX;
                dy = ev.getY() - startY;

                if(Math.abs(dx)-Math.abs(dy)> ViewConfiguration.get(getContext()).getScaledTouchSlop()){
//                    LogUtil.i(""+ViewConfiguration.get(getContext()).getScaledTouchSlop()+"      "+ViewConfiguration.getTouchSlop());
                    if(getScrollX()+(-dx) >rightView.getWidth() || getScrollX()+(-dx)< 0){
//                        LogUtil.i("getScrollX()="+getScrollX()+" ,(dx)="+dx+", rightView.getWidth()="+rightView.getWidth()+", (getScrollX()-dx)="+(getScrollX()-dx));
                        return true;
                    }
                    scrollBy((int) -dx,0);
                    startX = ev.getX();
                    startY = ev.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
//                LogUtil.i(ev.getX()+"               "+ev.getY()+"    "+rightView.getLeft()+"   "+rightView.getRight()+"    "+rightView.getTop()+"   "+rightView.getBottom());
                int offsetX = (getScrollX()/(float)rightView.getWidth())>0.5f?rightView.getWidth()-getScrollX():-getScrollX();
//                    Scroller的startScroll方法参数
//                    startX 水平方向滚动的偏移值，以像素为单位。正值表明滚动将向左滚动
//                    startY 垂直方向滚动的偏移值，以像素为单位。正值表明滚动将向上滚动
//                    dx 水平方向滑动的距离，正值会使滚动向左滚动
//                    dy 垂直方向滑动的距离，正值会使滚动向上滚动
                mScroller.startScroll(getScrollX(),getScrollY(),offsetX,0,1000);
//                LogUtil.i("offsetX="+offsetX+"          "+rightView.getWidth()+"          "+getScrollX()+"   "+(getScrollX()/(float)rightView.getWidth()));
                invalidate();
                startX = 0;
                startY = 0;
                dx = 0;
                dy = 0;
                LogUtil.i(""+isTouchPointInView(rightView,(int)ev.getRawX(),(int)ev.getRawY()));
                if(!isTouchPointInView(rightView,(int)ev.getRawX(),(int)ev.getRawY())){
                    return true;
                }
                break;
        }
        boolean dispatchTouchEvent = super.dispatchTouchEvent(ev);
//        LogUtil.e(""+ev.getAction()+"               "+dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    private boolean isTouchPointInView(View view,int x,int y){
        if(null == view){
            return false;
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if(y>=top && y<=bottom && x>=left && x<=right){
            return true;
        }
        return false;
    }


}
