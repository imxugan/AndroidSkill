package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    private int mDefaultWidth = DensityUtil.dp2Px(80);
    private int mDefaultHeight = DensityUtil.dp2Px(80);
    private final Paint mPaint;
    private int mSize;
    public static final int STATUS_NO_FINGER = 0;
    public static final int STATUS_FINGER_ON = 1;
    public static final int STATUS_FINGER_UP = 2;
    private int mCurrentStatus;
    private final Path mArrowPath;
    private int mArrowDegree = -1;
    private float mCenterX;
    private float mCenterY;

    public GestureLockView(Context context,int count,int fingerOnColor,int fingerOffColor,
                           int noFingerInnerCircleColor,int noFingerOuterCircleColor,
                           int tryTimes){
        super(context);
        this.mDefaultCount = count;
        this.mDefaultFingerOnColor = fingerOnColor;
        this.mDefaultFingerUpColor = fingerOffColor;
        this.mDefaultNoFingerInnerCircleColor = noFingerInnerCircleColor;
        this.mDefaultNoFingerOuterCircleColor = noFingerOuterCircleColor;
        this.mTryTimes = tryTimes;
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mCurrentStatus = STATUS_NO_FINGER;

        //初始化三角形路径
        mArrowPath = new Path();
    }

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
        a.recycle();
        LogUtil.i("mTryTimes="+mTryTimes);
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mCurrentStatus = STATUS_NO_FINGER;

        //初始化三角形路径
        mArrowPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        mSize = Math.min(widthSpecSize,heightSpecSize);

//        setMeasuredDimension((MeasureSpec.AT_MOST == widthSpecMode)?mDefaultWidth: mSize,
//                (MeasureSpec.AT_MOST == heightSpecMode)?mDefaultHeight: mSize);
        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode){
            mSize = mDefaultWidth;
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            mSize = Math.min(mSize,mDefaultWidth);
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            mSize = Math.min(mSize,mDefaultHeight);
        }
        setMeasuredDimension(mSize,mSize);
        //初始化三角形,三角形默认是箭头朝上的一个等边三角形，用户绘制结束后，
        //根据两个GestureLockView决定需要旋转多少度
        float mArrowLength = mSize /2;
        mArrowPath.moveTo(mSize/4,mSize/2 + 2);
        mArrowPath.lineTo(mSize/2,mSize/2 + mSize/4);
        mArrowPath.lineTo(mSize/2 + mSize/4,mSize/2 + 2);
        mArrowPath.close();
        mArrowPath.setFillType(Path.FillType.WINDING);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCenterX = getWidth()/2;
        mCenterY = mCenterX;
//        LogUtil.i("mCenterX="+mCenterX+"---mCurrentStatus="+mCurrentStatus);
        float outerRadius = mCenterX;
        float innerRadius = mCenterX /3;
        switch (mCurrentStatus){
            case STATUS_FINGER_ON:
                mPaint.setColor(mDefaultFingerOnColor);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(mCenterX, mCenterY,outerRadius,mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCenterX, mCenterY,innerRadius,mPaint);
                break;
            case STATUS_FINGER_UP:
                mPaint.setColor(mDefaultFingerUpColor);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(mCenterX, mCenterY,outerRadius,mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCenterX, mCenterY,innerRadius,mPaint);
                drawArrow(canvas);
                break;
            case STATUS_NO_FINGER:
            default:
//                LogUtil.i("STATUS_NO_FINGER---mDefaultNoFingerOuterCircleColor="+mDefaultNoFingerOuterCircleColor);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mDefaultNoFingerOuterCircleColor);
                canvas.drawCircle(mCenterX, mCenterY,outerRadius,mPaint);
                mPaint.setColor(mDefaultNoFingerInnerCircleColor);
                canvas.drawCircle(mCenterX, mCenterY,innerRadius,mPaint);
                break;
        }

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                mCurrentStatus = STATUS_FINGER_ON;
//                return true;
//            case MotionEvent.ACTION_UP:
//                mCurrentStatus = STATUS_FINGER_UP;
//                break;
//        }
//        postInvalidate();
//        return super.onTouchEvent(event);
//    }


//   @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                 break;
//            case MotionEvent.ACTION_MOVE:
//                int dx = (int)(event.getX() - mMoveLastX);
//                int dy = (int)(event.getY() - mMoveLastY);
//                //计算角度
////                LogUtil.e("Math.atan2(dy,dx)="+Math.atan2(dy,dx));
////                LogUtil.e("Math.toDegrees(Math.atan2(dy,dx)="+Math.toDegrees(Math.atan2(dy,dx)));
//                int degree = (int) (Math.toDegrees(Math.atan2(dy,dx)) + 270);
//                setmArrowDegree(degree);
//                mMoveLastX = x;
//                mMoveLastY = y;
//                break;
//            case MotionEvent.ACTION_UP:
//
//                break;
//        }
//        postInvalidate();
////       LogUtil.i("super.getClass()="+super.getClass()+"---this.getClass()="+this.getClass());
////       LogUtil.i("super.toString()="+super.toString());
////       LogUtil.i("super.getParent()="+super.getParent());
//        return super.onTouchEvent(event);
//    }



    private void drawArrow(Canvas canvas){
        if(mArrowDegree != -1){
//            LogUtil.i("mArrowDegree="+mArrowDegree+"---mArrowPath="+mArrowPath+"---getId()="+getId());
            LogUtil.i("mArrowPath="+mArrowPath+"---mArrowPath.isEmpty()="+mArrowPath.isEmpty()+"---getId()="+getId());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.save();
            canvas.rotate(mArrowDegree,mCenterX,mCenterY);
            canvas.drawPath(mArrowPath,mPaint);
            canvas.restore();
        }
    }

    public void setmArrowDegree(int arrowDegree){
        this.mArrowDegree = arrowDegree;
    }

    public int getArrowDegree(){
        return this.mArrowDegree;
    }

    public void setMode(int status){
        mCurrentStatus = status;
        mArrowDegree = -1;//这里记得将三角形的角度进行重置，否则，当某个小圈画了一个角度后，再次
                            //点击该小圈，小圈上的三角形还是上次的三角形的角度，并且会将三角形显示出来
                            //这样肯定是不合理的
        postInvalidate();
    }

}
