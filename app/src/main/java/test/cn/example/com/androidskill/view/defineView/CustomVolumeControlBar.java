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
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/7.
 */

public class CustomVolumeControlBar extends View {
    private final int DEFAULTWIDTH = DensityUtil.dp2Px(150);
    private final int DEFAULTHEIGHT = DensityUtil.dp2Px(150);
    private Bitmap mBitmap;
    private Paint mPaint;
    private float mCircleWidth;
    private int mCount = 1;
    private int mSplitSize = DensityUtil.dp2Px(10);
    private int mFirstColor;
    private int mSecondColor;
    private final RectF mRect;
    private float mRawX;
    private float mRawY;
    private float mEventDownX;
    private float mEventDownY;
    private int mCurrentCount;
    private boolean isUp = true;

    public CustomVolumeControlBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomVolumeControlBar);
        int attrCount = a.getIndexCount();
        int arr;
        mCircleWidth = DensityUtil.dp2Px(10);
        mFirstColor = Color.RED;
        mSecondColor = Color.BLUE;
        for (int i = 0; i < attrCount; i++) {
            arr = a.getIndex(i);
            switch (arr){
                case R.styleable.CustomVolumeControlBar_circleWidth:
                    mCircleWidth = a.getDimension(arr, mCircleWidth);
                    break;
                case R.styleable.CustomVolumeControlBar_count:
                    mCount = a.getInt(arr, mCount);
                    mCurrentCount = mCount;
                    break;
                case R.styleable.CustomVolumeControlBar_firstColor:
                    mFirstColor = a.getColor(arr, mFirstColor);
                    break;
                case R.styleable.CustomVolumeControlBar_secondColor:
                    mSecondColor = a.getColor(arr, mSecondColor);
                    break;
                case R.styleable.CustomVolumeControlBar_image:
                    int resourceId = a.getResourceId(arr, R.mipmap.ic_launcher);
                    mBitmap = BitmapFactory.decodeResource(getResources(), resourceId);
                    break;
                case R.styleable.CustomVolumeControlBar_splitSize:
                    mSplitSize = a.getInt(arr,mSplitSize);
                    break;
            }
        }
        a.recycle();

        //设置点击事件监听
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUp){
                    volumeUp();
                    LogUtil.i("up mCurrentCount="+mCurrentCount);
                    if(mCurrentCount<=0){
                        isUp = false;
                    }
                }else {
                    volumeDown();
                    LogUtil.i("else mCurrentCount="+mCurrentCount);
                    if(mCurrentCount==mCount){
                        isUp = true;
                    }

                }
            }
        });

        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        //设置画笔的样式，不填充,仅描边
        mPaint.setStyle(Paint.Style.STROKE);
        //设置线冒样式，取值有Cap.ROUND(圆形线冒)、Cap.SQUARE(方形线冒)、Paint.Cap.BUTT(无线冒)
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //相关知识点
//        设置线段连接处样式，取值有：Join.MITER（结合处为锐角）、Join.Round(结合处为圆弧)、Join.BEVEL(结合处为直线)
//        setStrokeJoin(Paint.Join join)
//        设置笔画的倾斜度，90度拿画笔与30拿画笔，画出来的线条样式肯定是不一样的吧。
//        setStrokeMiter(float miter)

        //设置画笔的宽度
        mPaint.setStrokeWidth(mCircleWidth);
        mRect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,DEFAULTHEIGHT);
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightSpecSize){
            setMeasuredDimension(widthSpecSize,DEFAULTHEIGHT);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth()/2;
        float radius = center - mCircleWidth/2;
        drawOval(canvas,center,radius);

        float realRadius = radius - mCircleWidth/2;//获得内圆半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.left = (float)(realRadius - Math.sqrt(2)/2 * realRadius)+mCircleWidth;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.top = (float) (realRadius - Math.sqrt(2)/2 * realRadius) + mCircleWidth;
        mRect.right = mRect.left +(float) Math.sqrt(2) * realRadius;
        mRect.bottom = mRect.top + (float) Math.sqrt(2) * realRadius;
        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if(mBitmap.getWidth()<Math.sqrt(2)*realRadius){
            mRect.left = mRect.left + (float) (Math.sqrt(2)* realRadius/2 - mBitmap.getWidth()/2);
            mRect.top = mRect.top + (float) (Math.sqrt(2) * realRadius/2 - mBitmap.getHeight()/2);
            mRect.right = mRect.left + mBitmap.getWidth();
            mRect.bottom = mRect.top + mBitmap.getHeight();
        }
        LogUtil.i(""+mRect);
        //绘制图片
        canvas.drawBitmap(mBitmap,null,mRect,mPaint);
    }

    private void drawOval(Canvas canvas, int center, float radius) {
        float itemSize = (360*1f - mCount * mSplitSize)/mCount;
        RectF oval = new RectF(center - radius,center - radius,center+radius,center+radius);
        mPaint.setColor(mFirstColor);
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval,i * (mSplitSize + itemSize),itemSize,false,mPaint);
        }
        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval,i * (itemSize + mSplitSize),itemSize,false,mPaint);
        }
    }


    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                mRawX = event.getRawX();
//                mRawY = event.getRawY();
////                LogUtil.i();
//                mEventDownX = event.getX();
//                mEventDownY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float eventMoveY = event.getY();
//                if(eventMoveY>mEventDownY){
//                    //向下滑动
//                    volumeDown();
//                }else {
//                    volumeUp();
//                }
//                break;
//        }
////        return super.onTouchEvent(event);
//        return true;//由于触摸事件需要自己去处理，所以这里直接返回true,这样CustomVolumeControlBar这个控件
//        //的dispatchTouchEvent方法才会返回true,这样触摸事件才会被CustomVolumeControlBar消费，也就是自己处理
//        //这个up,move,down这些事件。
//    }

    private void volumeUp() {
        mCurrentCount--;
        postInvalidate();
    }

    private void volumeDown() {
        if(mCurrentCount<mCount){
            mCurrentCount++;
        }
        postInvalidate();
    }
}
