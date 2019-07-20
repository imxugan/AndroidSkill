package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/20.
 */

public class QQSlidingMenu extends HorizontalScrollView {
    private final int mScreenWidth;
    private ViewGroup mMenu;
    private ViewGroup mMain;
    private int mMenuPaddingRight = 100;
    private int mMenuWidth;
    private float downX;
    private boolean isOnce;

    public QQSlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!isOnce){
            LinearLayout wrap = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) wrap.getChildAt(0);
            mMain = (ViewGroup) wrap.getChildAt(1);
            mMenuWidth = mScreenWidth - mMenuPaddingRight;
            mMenu.getLayoutParams().width = mMenuWidth;
            mMain.getLayoutParams().width = mScreenWidth;
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
//            mMenu.scrollTo(mMenuWidth,0); //当时这里手误，写成了mMenu.scrollTo，倒是mMenu中的内容移动到了左边
            //从而导致mMenu中内容看不到，坑了1个多小时
            scrollTo(mMenuWidth,0);//向左滑动，也就是将侧滑菜单显示出来,记得
            isOnce = true;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                float dx = ev.getX() - downX;
                if(dx<mScreenWidth/3){
                    //如果向右侧滑动的距离超过屏幕的三分之一的宽度，则将菜单全部滑动到屏幕中
                    smoothScrollTo(mMenuWidth,0);
                }else {
                    smoothScrollTo(0,0);
                }
               return true;
        }
        boolean onTouchEvent = super.onTouchEvent(ev);
        if(MotionEvent.ACTION_DOWN == ev.getAction()){
            LogUtil.i(""+onTouchEvent);
        }
        return onTouchEvent;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //这里要用将l转换成float,否则fraction就直接是0.8，这样缩放的动画效果就看着很僵硬
        float fraction = (float)l/mMenu.getWidth();//l 一直变大，所以fraction一直变大
        //mMenum滑动动画
        mMenu.setTranslationX(l*0.6f);
        //mMenu的缩放动画,向右侧移动，menu控件要缩小,乘以0.4，是为了缩放的最小比例是0.6，否则就会缩放的看不到了
        float menuScale = 1 - 0.4f *fraction;
        mMenu.setScaleX(menuScale);
        mMenu.setScaleY(menuScale);
        //menu控件的alpha动画
        mMenu.setAlpha(1-fraction);
        //main控件的缩放动画,重1变成0.8
        float mainScale = 0.8f + 0.2f*fraction;
        mMain.setScaleX(mainScale);
        mMain.setScaleY(mainScale);


    }
}
