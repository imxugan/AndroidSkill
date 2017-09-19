package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/19.
 */

public class GestureLockView extends View {
    private int mDefaultCount;
    private int mDefaultFingerOnColor = Color.RED;
    private int mDefaultFingerUpColor = Color.BLUE;
    private int mDefaultNoFingerInnerCircleColor = Color.WHITE;
    private int mDefaultNoFingerOuterCircleColor = Color.GREEN;
    private int mTryTimes ;
    private int mDefaultWidth = DensityUtil.dp2Px(150);
    private int mDefaultHeight = DensityUtil.dp2Px(150);
    private final Paint mPaint;
    private final int mStrokeWidth = DensityUtil.dp2Px(15);
    private int mSize;
    private final RectF mRect;

    public GestureLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockView);
        int attrCount = a.getIndexCount();
        for (int i = 0; i < attrCount; i++) {
            int arr = a.getIndex(i);
            switch (arr){
                case R.styleable.GestureLockView_count:
                    mDefaultCount = a.getInteger(arr, mDefaultCount);
                    break;
                case R.styleable.GestureLockView_finger_on_color:
                    mDefaultFingerOnColor = a.getColor(arr,mDefaultFingerOnColor);
                    break;
                case R.styleable.GestureLockView_finger_up_color:
                    mDefaultFingerUpColor = a.getColor(arr,mDefaultFingerUpColor);
                    break;
                case R.styleable.GestureLockView_no_finger_inner_circle_color:
                    mDefaultNoFingerInnerCircleColor = a.getColor(arr,mDefaultNoFingerInnerCircleColor);
                    break;
                case R.styleable.GestureLockView_no_finger_outer_circle_color:
                    mDefaultNoFingerOuterCircleColor = a.getColor(arr,mDefaultNoFingerOuterCircleColor);
                    break;
                case R.styleable.GestureLockView_tryTimes:
                    mTryTimes = a.getInt(arr,mTryTimes);
                    break;
            }
        }

        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
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
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeWidth(DensityUtil.dp2Px(1));
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(mRect,0,360,false,mPaint);
//        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DensityUtil.dp2Px(1));
        mPaint.setColor(Color.RED);
        canvas.drawRect(mRect,mPaint);
    }
}
