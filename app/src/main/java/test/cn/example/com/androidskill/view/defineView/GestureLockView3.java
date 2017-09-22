package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/22.
 */

public class GestureLockView3 extends View {
    private int mSingerOnColor ;
    private int mSingerUpnColor ;
    private int mNoSingerInnerCircleColor ;
    private int mNoSingerOuterCircleColor ;
    private int DEFAULTWIDTH = DensityUtil.dp2Px(80);
    private int DEFAULTHEIGHT = DensityUtil.dp2Px(80);
    private Paint mPaint;
    private Path mArrowPath;
    private int mCenterX;
    public static final int STATUS_NO_SINGER = 0;
    public static final int STATUS_SINGER_ON = 1;
    public static final int STATUS_SINGER_UP = 2;
    private int mCurrentStatus;
    private int mOuterRadius;
    private int mInnerRadius;
    private int mDegree;

    public GestureLockView3(Context context) {
        super(context);
    }

    public GestureLockView3(Context context,int singerOnColor,int singerUpColor,
                            int noSingerInnerCircleColor,int noSingerOuterCircleColor){
        this(context);
        this.mSingerOnColor = singerOnColor;
        this.mSingerUpnColor = singerUpColor;
        this.mNoSingerInnerCircleColor = noSingerInnerCircleColor;
        this.mNoSingerOuterCircleColor = noSingerOuterCircleColor;

        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //初始化箭头
        mArrowPath = new Path();
        mCurrentStatus = STATUS_NO_SINGER;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int size = (widthSpecSize>heightSpecSize)?heightSpecSize:widthSpecSize;
        if(MeasureSpec.AT_MOST == widthMeasureSpec && MeasureSpec.AT_MOST == heightSpecMode){
            size = DEFAULTWIDTH;
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            size = (DEFAULTWIDTH>heightSpecSize)?heightSpecSize:DEFAULTWIDTH;
        }else if(MeasureSpec.AT_MOST == heightMeasureSpec){
            size = (DEFAULTWIDTH>widthSpecSize)?widthSpecSize:DEFAULTWIDTH;
        }
        setMeasuredDimension(size,size);
        //需要提前初始化arrowPath，否则，第一次滑动，不会出现小箭头，不知道是什么原因
        LogUtil.i("size========="+size);
        mCenterX = size/2;
        mOuterRadius = mCenterX/2;
        mInnerRadius = mOuterRadius/2;
        LogUtil.i("size========="+size);
        LogUtil.i("mCenterX========="+mCenterX);
        LogUtil.i("mOuterRadius========="+mOuterRadius);
        LogUtil.i("mInnerRadius========="+mInnerRadius);
        initArrowPath(mCenterX,mInnerRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCenterX = getWidth()/2;
        mOuterRadius = mCenterX/2;
        mInnerRadius = mOuterRadius/2;
        switch (mCurrentStatus){
            case STATUS_NO_SINGER:
                default:
                    mPaint.setColor(mNoSingerOuterCircleColor);
                    canvas.drawCircle(mCenterX,mCenterX,mOuterRadius,mPaint);
                    mPaint.setColor(mNoSingerInnerCircleColor);
                    canvas.drawCircle(mCenterX,mCenterX,mInnerRadius,mPaint);
                break;
            case STATUS_SINGER_ON:
                mPaint.setColor(mSingerOnColor);
                mPaint.setStyle(Paint.Style.STROKE);
                //绘制大圆
                canvas.drawCircle(mCenterX,mCenterX,mOuterRadius,mPaint);
                //绘制小圆
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCenterX,mCenterX,mInnerRadius,mPaint);
//                //绘制三角形
                drawArrow(canvas,mCenterX,mInnerRadius);
                break;
            case STATUS_SINGER_UP:
                mPaint.setColor(mSingerUpnColor);
                mPaint.setStyle(Paint.Style.STROKE);
                //绘制大圆
                canvas.drawCircle(mCenterX,mCenterX,mOuterRadius,mPaint);
                //绘制小圆
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCenterX,mCenterX,mInnerRadius,mPaint);
                //绘制三角形
                LogUtil.i("mDegree="+mDegree+"---getId()"+getId());
                if(mDegree != -1){
//                    LogUtil.i("mArrowPath===="+mArrowPath+""+mArrowPath.isEmpty()+"---getId()="+getId());
                    drawArrow(canvas,mCenterX,mInnerRadius);
                }
                break;
        }
    }

    private void initArrowPath(int centerX,int innerRadius) {
        if (mArrowPath.isEmpty()) {
            mArrowPath.moveTo(centerX,centerX - innerRadius -DensityUtil.dp2Px(2));
        }else {
            mArrowPath.lineTo(centerX + innerRadius + DensityUtil.dp2Px(5),centerX);
            mArrowPath.lineTo(centerX,centerX + innerRadius + DensityUtil.dp2Px(2));
        }
    }

    private void drawArrow(Canvas canvas, int centerX, int mInnerRadius) {
//        LogUtil.i("mArrowPath===="+mArrowPath+""+mArrowPath.isEmpty()+"---getId()="+getId());
        canvas.save();
        canvas.rotate(mDegree,centerX,centerX);
        canvas.drawPath(mArrowPath, mPaint);
        canvas.restore();
    }

    public void setStatus(int status){
        mCurrentStatus = status;
        //由于父控件重写了dispatchDraw，并且父控件中未对当前子控件进行绘制，所以，这里需要自己从新跟新界面
        invalidate();
   }

    public void setDegree(int degree){
        mDegree = degree;
    }
}
