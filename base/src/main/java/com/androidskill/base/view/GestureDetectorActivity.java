package com.androidskill.base.view;

import android.os.Bundle;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.androidskill.base.util.ActivityToTranslucentUtil;
import com.androidskill.base.util.LogUtil;

public abstract class GestureDetectorActivity extends BaseActitivy {
    private int screenWidth;
    private int screenHeight;
    private float downX;
    private float downY;
    private int mTouchSlop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityToTranslucentUtil.convertActivityToTranslucent(this);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        ViewConfiguration configuration = ViewConfiguration.get(this);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.i(""+event.getAction());
        if(event.getAction() == MotionEvent.ACTION_DOWN){// 当按下时
            // 获得按下时的X坐标
            downX = event.getX();
            downY = event.getY();

        }else if(event.getAction() == MotionEvent.ACTION_MOVE){// 当手指滑动时
            // 获得滑过的距离
            float moveDistanceX = event.getX() - downX;
            float moveDistanceY = event.getY() - downY;
            if(moveDistanceX > 0 && moveDistanceY<mTouchSlop){// 如果是向右滑动
                getWindow().getDecorView().setX(moveDistanceX);// 设置界面的X到滑动到的位置
                return true;
            }

        }else if(event.getAction() == MotionEvent.ACTION_UP){// 当抬起手指时
            // 获得滑过的距离
            float moveDistanceX = event.getX() - downX;
            float moveDistanceY = event.getY() - downY;
            if(moveDistanceX > 0 && moveDistanceY<mTouchSlop){
                if(moveDistanceX > screenWidth / 2){
                    // 如果滑动的距离超过了手机屏幕的一半, 结束当前Activity
                    finish();
                }else{ // 如果滑动距离没有超过一半
                    // 恢复初始状态
                    getWindow().getDecorView().setX(0);
                }
                return true;
            }

        }
        return super.dispatchTouchEvent(event);
    }
}