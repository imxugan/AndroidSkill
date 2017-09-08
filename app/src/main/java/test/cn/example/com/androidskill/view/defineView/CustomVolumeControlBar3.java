package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;

/**
 * Created by xgxg on 2017/9/8.
 */

public class CustomVolumeControlBar3 extends View {
    private final int DEFAULTWIDTH = DensityUtil.dp2Px(150);
    private final int DEFALULTHEIGHT = DensityUtil.dp2Px(150);
    private float mCircleWidth = DensityUtil.dp2Px(10);
    private int mCount = 1;
    private int mFirstColor = Color.RED;
    private int mSecondColor = Color.BLUE;
    private Bitmap mBitmap ;
    private int mSplitSize = DensityUtil.dp2Px(10);
    private Paint mPaint;
    public CustomVolumeControlBar3(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomVolumeControlBar);
        int attrsCount = a.getIndexCount();
        int arr;
        for (int i = 0; i < attrsCount; i++) {
            arr = a.getIndex(i);
            switch (arr){
                case R.styleable.CustomVolumeControlBar_circleWidth:
                    mCircleWidth = a.getDimension(arr,mCircleWidth);
                    break;
                case R.styleable.CustomVolumeControlBar_count:
                    mCount = a.getInt(arr,mCount);
                    break;
                case R.styleable.CustomVolumeControlBar_firstColor:
                    mFirstColor = a.getColor(arr,mFirstColor);
                    break;
                case R.styleable.CustomVolumeControlBar_secondColor:
                    mSecondColor = a.getColor(arr,mSecondColor);
                    break;
                case R.styleable.CustomVolumeControlBar_image:
                    int resourceId = a.getResourceId(arr, 0);
                    mBitmap = BitmapFactory.decodeResource(getResources(),resourceId);
                    break;
                case R.styleable.CustomVolumeControlBar_splitSize:
                    mSplitSize = a.getInt(arr,mSplitSize);
                    break;
            }
        }

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,DEFALULTHEIGHT);
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(widthSpecSize,DEFALULTHEIGHT);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth()/2;
        float radius = center - mCircleWidth/2;
        int itemSize = (360 - mCount * mSplitSize)/mCount;
        RectF oval = new RectF(center - radius,center - radius,center+radius,center+radius);
        drawOval(oval,canvas,itemSize);
    }

    private void drawOval(RectF oval, Canvas canvas, int itemSize) {
        mPaint.setColor(mFirstColor);
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval,i * (mSplitSize + itemSize),itemSize,false,mPaint);
        }
        mPaint.setColor(mSecondColor);
        for (int i=0;i<mCount;i++){
            canvas.drawArc(oval,i*(mSplitSize + itemSize),itemSize,false,mPaint);
        }
    }
}
