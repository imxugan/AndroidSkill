package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/19.
 */

public class GestureLockViewGroup extends RelativeLayout {
    private final Context mContext;
    private int mCount = 0;
    private int mFingerOnColor = Color.RED;
    private int mFingerUpColor = Color.BLUE;
    private int mNoFingerInnerColor = Color.WHITE;
    private int mNoFingerOuterColor = Color.YELLOW;
    private int mTryTimes = 5;
    private GestureLockView[] mChilds ;
    private int mGestureLockViewWidth;
    private int mMarginBetweenLockView;
    private final Paint mPaint;
    private List<Integer> mChoose = new ArrayList<>();
    private final Path mPath;
    private float mLastPathX;//指引线的开始位置x
    private float mLastPathY;//指引线的开始位置y
    private PointF mTempleTarget = new PointF();//指引线的结束位置
    private int[] mAnswer = {1,2,6,10,14,11,8};
    private OnGestureLockViewSelectedListener mOnGestureLockViewSelectedListener;

    public GestureLockViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
//        LogUtil.i(""+attrs.toString());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockViewGroup);

        int arrCount = a.getIndexCount();
        int arr;
        for (int i = 0; i < arrCount; i++) {
            arr = a.getIndex(i);
            switch (arr){
                case R.styleable.GestureLockViewGroup_count:
                    mCount = a.getInt(arr,mCount);
                    break;
                case R.styleable.GestureLockViewGroup_finger_on_color:
                    mFingerOnColor = a.getColor(arr,mFingerOnColor);
                    break;
                case R.styleable.GestureLockViewGroup_finger_up_color:
                    mFingerUpColor = a.getColor(arr,mFingerUpColor);
                    break;
                case R.styleable.GestureLockViewGroup_no_finger_inner_circle_color:
                    mNoFingerInnerColor = a.getColor(arr, mNoFingerInnerColor);
                    break;
                case R.styleable.GestureLockViewGroup_no_finger_outer_circle_color:
                    mNoFingerOuterColor = a.getColor(arr, mNoFingerOuterColor);
                    break;
                case R.styleable.GestureLockViewGroup_tryTimes:
                    mTryTimes = a.getInt(arr, mTryTimes);
                    break;
            }
        }
        a.recycle();
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆线头
        mPath = new Path();

        LogUtil.i("mCount="+mCount);
//        LogUtil.i("mFingerOnColor="+mFingerOnColor);
//        LogUtil.i("mFingerUpColor="+mFingerUpColor);
//        LogUtil.i("mNoFingerInnerColor="+mNoFingerInnerColor);
//        LogUtil.i("mNoFingerOuterColor="+mNoFingerOuterColor);
//        LogUtil.i("mTryTimes="+mTryTimes);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        LogUtil.i("widthSpecSize="+widthSpecSize);
//        LogUtil.i("heightSpecSize="+heightSpecSize);
        int mSize = Math.min(widthSpecSize,heightSpecSize);
        if(null == mChilds){
            mChilds = new GestureLockView[mCount*mCount];
            mGestureLockViewWidth = 4 * mSize / (5 * mCount +1);
//            LogUtil.i("mGestureLockViewWidth===="+mGestureLockViewWidth);
            mMarginBetweenLockView = (int)(mGestureLockViewWidth * 0.25);
            mPaint.setStrokeWidth(mGestureLockViewWidth * 0.25f);
            for (int i = 0; i < mChilds.length; i++) {
                //初始化每一个GestureLockView
                mChilds[i] = new GestureLockView(mContext,mCount,mFingerOnColor,mFingerUpColor,mNoFingerInnerColor,mNoFingerOuterColor,mTryTimes);
                mChilds[i].setId(i+1);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mGestureLockViewWidth,
                        mGestureLockViewWidth);
                if(i % mCount != 0){
                    layoutParams.addRule(RelativeLayout.RIGHT_OF,mChilds[i -1].getId());
                }
                if(i > (mCount - 1)){
                    layoutParams.addRule(RelativeLayout.BELOW,mChilds[i - mCount].getId());
                }

                //设置左上右下的margin
                int leftMargin = 0;
                int topMargin = 0;
                int rightMargin = mMarginBetweenLockView;
                int bottomMargin = mMarginBetweenLockView;
                if(i>=0 && i<mCount){//第一行
                    topMargin = mMarginBetweenLockView;
                }

                if(i % mCount == 0){//第一列
                    leftMargin = mMarginBetweenLockView;
                }

                layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
                mChilds[i].setMode(GestureLockView.STATUS_NO_FINGER);
                addView(mChilds[i],layoutParams);
            }
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //绘制GestureLockView之间的连接线
        if(null != mPath){
            canvas.drawPath(mPath,mPaint);
        }

//        //绘制指引线
//        if(mChoose.size()>0){
//            if(mLastPathX != 0 && mLastPathY != 0){
//                canvas.drawLine(mLastPathX,mLastPathY,mTempleTarget.x,mTempleTarget.y,mPaint);
//            }
//        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                LogUtil.i("super.getClass()="+super.getClass()+"---this.getClass()="+this.getClass());
                //int x = (int) getX();
                //int y = (int) getY();
                //getX()方法获取的是当前View左上角相对于父容器的坐标
//                LogUtil.i("x="+x+"---y="+y+"---this.getY()="+this.getY()+"---this.getX()="+this.getX()+"---getX()="+getX());
                reset();//重置
                break;
            case MotionEvent.ACTION_MOVE:
                mPaint.setColor(mFingerOnColor);
                mPaint.setAlpha(50);
                GestureLockView child = getchildIdByPosition(x,y);
                if(null != child){
                    int id = child.getId();
                    if(!mChoose.contains(id)){
                        mChoose.add(id);
                        child.setMode(GestureLockView.STATUS_FINGER_ON);
                        //设置监听，暂时省略
                        mLastPathX = child.getLeft() /2 + child.getRight()/2;
                        mLastPathY = child.getTop()/2 + child.getBottom() /2;
                        if(mChoose.size() == 1){//当前添加为第一个
                            mPath.moveTo(mLastPathX, mLastPathY);
                        }else {//不是第一个，则将两个点用线连上
                            mPath.lineTo(mLastPathX, mLastPathY);
                        }
                    }
                }
                //指引线的终点
                mTempleTarget.x = x;
                mTempleTarget.y = y;
                break;
            case MotionEvent.ACTION_UP:
                mPaint.setColor(mFingerUpColor);
                mPaint.setAlpha(50);
                this.mTryTimes--;
                //判断是否回调成功
                mOnGestureLockViewSelectedListener.restMatchTimes(mTryTimes);
                mOnGestureLockViewSelectedListener.isMatch(checkAnswer());
                //将终点位置设置为起点，即取消指引线
                mTempleTarget.x = mLastPathX;
                mTempleTarget.y = mLastPathY;
                //改变每一个被选中的子元素的状态为up
                changeSelectedChildMode();
                LogUtil.i("mChoose.size()="+mChoose.size());
                for (int i = 0; i < mChoose.size()-1; i++) {
                    int childId = mChoose.get(i);
                    int nextChildId = mChoose.get(i + 1);
                    GestureLockView startChild = (GestureLockView) findViewById(childId);
                    GestureLockView nextChild = (GestureLockView) findViewById(nextChildId);
                    mOnGestureLockViewSelectedListener.onSingleSelected(childId);
                    int dx = nextChild.getLeft() - startChild.getLeft();
                    int dy = nextChild.getTop() - startChild.getTop();
                    //计算角度
//                    LogUtil.e("Math.atan2(dy,dx)="+Math.atan2(dy,dx));
//                    LogUtil.e("Math.toDegrees(Math.atan2(dy,dx)="+Math.toDegrees(Math.atan2(dy,dx)));
                    int degree = (int) (Math.toDegrees(Math.atan2(dy,dx)) + 270);

                    LogUtil.i("i="+i+"---dx="+dx+"---dy="+dy+"---degree="+degree);
                    startChild.setmArrowDegree(degree);
                    if(i+1 == mChoose.size() -1){
                        mOnGestureLockViewSelectedListener.onSingleSelected(nextChildId);
                        degree = -1;
                        nextChild.setmArrowDegree(degree);
                        LogUtil.e("i="+i+"---dx="+dx+"---dy="+dy+"---degree="+degree);
                    }
                }
                break;
        }
//        return super.onTouchEvent(event);
        invalidate();
        return true;
    }

    private boolean checkAnswer() {
        boolean result = false;
        if(mChoose.size() == mAnswer.length){
            for (int i = 0; i < mChoose.size(); i++) {
                if(mChoose.get(i) == mAnswer[i]){
                    result = true;
                }else {
                    result = false;
                    break;
                }
            }
        }else {
            result = false;
        }
        return result;
    }

    double getAngle(int px1, int py1, int px2, int py2) {
        //两点的x、y值
        int x = px2 - px1;
        int y = py2 - py1;
        double hypotenuse = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        //斜边长度
        double sin = x / hypotenuse;
        double radian = Math.asin(sin);
        //求出弧度
        double angle = 180 / (Math.PI / radian);
        //用弧度算出角度
        if (x < 0 && y < 0) {
            angle = angle + 270;
            LogUtil.i( "getAngle: 270 " );
        }
        if (x > 0 && y < 0) {
            LogUtil.i( "getAngle: 0 " );
        }
        if (x > 0 && y > 0) {
            angle = angle + 90;
            LogUtil.i( "getAngle: 90 " );
        }
        if (x < 0 && y > 0) {
            angle = angle + 180;
            LogUtil.i( "getAngle: 180 " );
        }

        LogUtil.i( "getAngle: px1 " + px1 + " py1 " + py1 + " px2 " + px2 + " py2 " + py2 + "   x "+x + "  y "+y);
//        LogUtil.i( "getAngle: sin " + sin + " radian " + radian + " angle " + angle);
        return angle;
    }

    private void changeSelectedChildMode() {
        if(null != mChilds){
            for (int i = 0; i < mChilds.length; i++) {
                if(mChoose.contains(mChilds[i].getId())){
                    mChilds[i].setMode(GestureLockView.STATUS_FINGER_UP);
                }
            }
        }
    }

    private GestureLockView getchildIdByPosition(int x, int y) {
        if(null != mChilds){
            for (int i = 0; i < mChilds.length; i++) {
                if(checkPosition(mChilds[i],x,y)){
                    return mChilds[i];
                }
            }
        }
        return null;

    }

    private boolean checkPosition(GestureLockView gestureLockView, int x, int y) {
//        LogUtil.i("x="+x+"---y="+y);
//        LogUtil.i("gestureLockView.getLeft()="+gestureLockView.getLeft());
//        LogUtil.i("gestureLockView.getRight()="+gestureLockView.getRight());
//        LogUtil.i("gestureLockView.getTop()="+gestureLockView.getTop());
//        LogUtil.i("gestureLockView.getBottom()="+gestureLockView.getBottom());
        if(x>=gestureLockView.getLeft() && x<=gestureLockView.getRight()
                && y>= gestureLockView.getTop() && y<= gestureLockView.getBottom()){
            return true;
        }
        return false;
    }

    private void reset() {
        mChoose.clear();
        mPath.reset();
        if(null !=mChilds){
            for (int i = 0; i < mChilds.length; i++) {
                mChilds[i].setMode(GestureLockView.STATUS_NO_FINGER);
            }
        }
    }

    public void setOnGestureLockViewSelectedListener(OnGestureLockViewSelectedListener listener){
        this.mOnGestureLockViewSelectedListener = listener;
    }

    public interface OnGestureLockViewSelectedListener{
        /**
         * 单独选中的GestureLockView的id
         * @param id
         */
        void onSingleSelected(int id);

        /**
         * 是否匹配
         * @param result
         */
        void isMatch(boolean result);

        /**
         * 剩余的次数
         */
        void restMatchTimes(int tryTimes);
    }

    //如果是直接继承了RelativeLayout,则不需要重写onLayout方法，如果重写了，就需要自己确定
    //子view的位置，如果这个方法空实现，则子view是无法调用自己的onMeasure和draw方法的，坑了3个小时
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int childCount = getChildCount();
//
//    }
}
