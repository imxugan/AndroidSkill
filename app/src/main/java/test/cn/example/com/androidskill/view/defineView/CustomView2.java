package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/19.
 */

public class CustomView2 extends View {
    private int mDefaultWidth = DensityUtil.dp2Px(150);
    private int mDefaultHeight = DensityUtil.dp2Px(150);
    private final Paint mPaint;
    private final int mStrokeWidth = DensityUtil.dp2Px(15);
    private int mSize;
    private final RectF mRect;

    public CustomView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mRect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        mSize = Math.min(widthSpecSize,heightSpecSize);
        setMeasuredDimension((MeasureSpec.AT_MOST == widthSpecMode)?mDefaultWidth: mSize,
                (MeasureSpec.AT_MOST == heightSpecMode)?mDefaultHeight: mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth()/2;
        float radius = centerX - mStrokeWidth/2;
        float left = centerX - radius;
        float top = centerX - radius;
        float right = centerX  + radius;
        float bottom = centerX + radius;
        LogUtil.i("left="+left+"---top="+top+"---right="+right+"---bottom="+bottom);
        LogUtil.i("centerX="+centerX+"---radius="+radius+"---mStrokeWidth="+mStrokeWidth);
        mRect.set(left,top,right,bottom);
        canvas.drawArc(mRect,0,360,false,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DensityUtil.dp2Px(1));
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(mRect,mPaint);
    }
}
