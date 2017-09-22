package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/21.
 */

public class GestureLockViewGroup2 extends RelativeLayout {
    private int mDefaultFingerOnColor = Color.RED;
    private int mDefaultFingerUpColor = Color.BLUE;
    private int mDefaultNoFingerInnerCircleColor = Color.GREEN;
    private int mDefaultNoFingerOuterCircleColor = Color.BLACK;
    private int mDefaultCount = 4;
    private int mTryTimes;
    private final Paint mPaint;
    private final Path mPath;
    private int mLineWidth;
    private GestureLockView3[] mChildres = null;
    private int mOuterCirclerWidth;
    private int mInnerCircleRadius;
    private ArrayList<GestureLockView3> mSelected ;
    private float mLastMoveX;
    private float mLastMoveY;
    private GestureLockView3 mLastSelectedChild;
    private int mChildWidth;
    private int mCurrentStatus;

    public GestureLockViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockViewGroup);
        int attr = 0;
        for (int i = 0; i < a.getIndexCount(); i++) {
            attr = a.getIndex(i);
            switch (attr){
                case R.styleable.GestureLockViewGroup_count:
                    mDefaultCount = a.getInt(attr,mDefaultCount);
                    break;
                case R.styleable.GestureLockViewGroup_finger_on_color:
                    mDefaultFingerOnColor = a.getColor(attr, mDefaultFingerOnColor);
                    break;
                case R.styleable.GestureLockViewGroup_finger_up_color:
                    mDefaultFingerUpColor = a.getColor(attr, mDefaultFingerUpColor);
                    break;
                case R.styleable.GestureLockViewGroup_no_finger_inner_circle_color:
                    mDefaultNoFingerInnerCircleColor = a.getColor(attr,mDefaultNoFingerInnerCircleColor);
                    break;
                case R.styleable.GestureLockViewGroup_no_finger_outer_circle_color:
                    mDefaultNoFingerOuterCircleColor = a.getColor(attr,mDefaultNoFingerOuterCircleColor);
                    break;
                case R.styleable.GestureLockViewGroup_tryTimes:
                    mTryTimes = a.getInt(attr, mTryTimes);
                    break;
            }
        }
        a.recycle();
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.YELLOW);
        mPaint.setAlpha(50);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        //创建一个存放GestureLockView的数组
//        mChildres = new GestureLockView[mDefaultCount * mDefaultCount];
        //连接线
        mPath = new Path();
        //初始化选中的圆圈的集合
        mSelected = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //获取宽和高中的较小值
        int size = (widthSpecSize>heightSpecSize)?heightSpecSize:widthSpecSize;
        initChildrens(size);

        //第二种初始化方式，会出现，一些莫名的bug,这种方式会执行两次LogUtil.i("childCount = "+getChildCount());
        //并且通过GestureLockView获取的getLeft()和getTop()始终是0，很奇葩
        //别忘记在构造函数中的对于 GestureLockView的数组 进行初始化
//        initChildrens2(size);
    }

    private void initChildrens2(int size) {
        //确定每一个子view的宽高
        int childWidth = size / mDefaultCount;
        mOuterCirclerWidth = childWidth/2;
        mInnerCircleRadius = mOuterCirclerWidth /3;
        mLineWidth = mInnerCircleRadius * 2;
        GestureLockView child = null;
        RelativeLayout.LayoutParams params = null;
        for (int i = 0; i < mDefaultCount * mDefaultCount; i++) {
//            child = new GestureLockView(getContext(),mDefaultCount,mDefaultFingerOnColor,mDefaultFingerUpColor,
//                    mDefaultNoFingerInnerCircleColor,mDefaultNoFingerOuterCircleColor,mTryTimes);
//            child.setId(i+1);
            mChildres[i] = new GestureLockView3(getContext(),mDefaultFingerOnColor,mDefaultFingerUpColor,
                    mDefaultNoFingerInnerCircleColor,mDefaultNoFingerOuterCircleColor);
            mChildres[i].setId(i+1);
            params = new RelativeLayout.LayoutParams(childWidth,childWidth);
            if(i%mDefaultCount != 0){//只要不是每一行的第一列，就将这个chiild，放到前一个子view的右边
//                LogUtil.e("不是每一行的第一列  i="+i);
//                params.addRule(RelativeLayout.RIGHT_OF,i);
                params.addRule(RelativeLayout.RIGHT_OF,mChildres[i-1].getId());
            }

            //i = 4是，当前的view应该放到id=1的view的下面,通过这个对应关系，就可以发现规律
            //i = 4    id = 1
            //i = 5    id = 2
            //i = 6    id = 3
            //i = 7    id = 4
            //i = 8    id = 5
            //i = 9    id = 6
            //i = 10   id = 7
            //i = 11   id = 8
            //i = 12   id = 9
            //i = 13   id = 10
            //i = 14   id = 11
            //i = 15   id = 12
            if(i >(mDefaultCount - 1)){//只要不是第一行，就将这个child放到他上面的一个view的下方
                //注意这里的addRule中的第二个参数一定要写view的id
//                params.addRule(RelativeLayout.BELOW,(i-mDefaultCount+1));
                params.addRule(RelativeLayout.BELOW,mChildres[i-mDefaultCount].getId());
//                LogUtil.i("不是第一行  i="+i+"---(i-mDefaultCount)="+(i-mDefaultCount+1));
            }
            //下面的代码要特别注意：
            // addView(child,params);//这句话不仅是将child添加进了父控件，而且，将params也设置到了子child上
            //如果在这里将child复制给mChildres种的每一个元素，会出现，此时mChildres
            //中的每一个view的getLeft和getTop都为0，因为这些child都是没有被添加进父控件的child
            //mChildres[i] = child;
            //如果想让mChildres中的每一个chlld被添加到父控件，需要这样处理
//             mChildres[i] = child;
            addView(mChildres[i],params);
        }
        LogUtil.i("childCount = "+getChildCount());
    }

    private void initChildrens(int size) {
        if(null == mChildres){
            //创建一个存放GestureLockView的数组
            mChildres = new GestureLockView3[mDefaultCount * mDefaultCount];
            //确定每一个子view的宽高
//            int childWidth = (int)(size / mDefaultCount * 1.5f);
            mChildWidth = size / mDefaultCount;
            mOuterCirclerWidth = mChildWidth /2;
            mInnerCircleRadius = mOuterCirclerWidth /2;
            LogUtil.i("mChildWidth="+mChildWidth);
            LogUtil.i("mOuterCirclerWidth="+mOuterCirclerWidth);
            LogUtil.i("mInnerCircleRadius="+mInnerCircleRadius);
//            GestureLockViewGroup2.java::161::initChildrens-->>mChildWidth=175
//            0GestureLockViewGroup2.java::162::initChildrens-->>mOuterCirclerWidth=87
//            0GestureLockViewGroup2.java::163::initChildrens-->>mInnerCircleRadius=43


//            GestureLockView3.java::75::onMeasure-->>size=========175
//            GestureLockView3.java::76::onMeasure-->>mCenterX=========87
//            GestureLockView3.java::77::onMeasure-->>mOuterRadius=========43
//            GestureLockView3.java::78::onMeasure-->>mInnerRadius=========21

            mLineWidth = mInnerCircleRadius ;
            mPaint.setStrokeWidth(mLineWidth);
            GestureLockView child = null;
            RelativeLayout.LayoutParams params = null;
            for (int i = 0; i < mDefaultCount * mDefaultCount; i++) {
//            child = new GestureLockView(getContext(),mDefaultCount,mDefaultFingerOnColor,mDefaultFingerUpColor,
//                    mDefaultNoFingerInnerCircleColor,mDefaultNoFingerOuterCircleColor,mTryTimes);
//            child.setId(i+1);
                mChildres[i] = new GestureLockView3(getContext(),mDefaultFingerOnColor,mDefaultFingerUpColor,
                        mDefaultNoFingerInnerCircleColor,mDefaultNoFingerOuterCircleColor);
                mChildres[i].setId(i+1);
                params = new RelativeLayout.LayoutParams(mChildWidth, mChildWidth);
                if(i%mDefaultCount != 0){//只要不是每一行的第一列，就将这个chiild，放到前一个子view的右边
//                LogUtil.e("不是每一行的第一列  i="+i);
//                params.addRule(RelativeLayout.RIGHT_OF,i);
                    params.addRule(RelativeLayout.RIGHT_OF,mChildres[i-1].getId());
                }

                //i = 4是，当前的view应该放到id=1的view的下面,通过这个对应关系，就可以发现规律
                //i = 4    id = 1
                //i = 5    id = 2
                //i = 6    id = 3
                //i = 7    id = 4
                //i = 8    id = 5
                //i = 9    id = 6
                //i = 10   id = 7
                //i = 11   id = 8
                //i = 12   id = 9
                //i = 13   id = 10
                //i = 14   id = 11
                //i = 15   id = 12
                if(i >(mDefaultCount - 1)){//只要不是第一行，就将这个child放到他上面的一个view的下方
                    //注意这里的addRule中的第二个参数一定要写view的id
//                params.addRule(RelativeLayout.BELOW,(i-mDefaultCount+1));
                    params.addRule(RelativeLayout.BELOW,mChildres[i-mDefaultCount].getId());
//                LogUtil.i("不是第一行  i="+i+"---(i-mDefaultCount)="+(i-mDefaultCount+1));
                }
                //下面的代码要特别注意：
                // addView(child,params);//这句话不仅是将child添加进了父控件，而且，将params也设置到了子child上
                //如果在这里将child复制给mChildres种的每一个元素，会出现，此时mChildres
                //中的每一个view的getLeft和getTop都为0，因为这些child都是没有被添加进父控件的child
                //mChildres[i] = child;
                //如果想让mChildres中的每一个chlld被添加到父控件，需要这样处理
//             mChildres[i] = child;
                addView(mChildres[i],params);
            }
            LogUtil.i("childCount = "+getChildCount());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                reset();
//                LogUtil.i("event.getX()="+event.getX());
//                LogUtil.i("event.getY()="+event.getY());
//                LogUtil.i("isTouchInnerCircle(event.getX(), event.getY())="+isTouchInnerCircle(event.getX(),event.getY()));
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentStatus = GestureLockView3.STATUS_SINGER_ON;
                GestureLockView3 selectedChild = getSelectedChildByPosition(event.getX(),event.getY());
                if(null != mLastSelectedChild){
                    mLastSelectedChild.setDegree(getDegree(event.getX(),event.getY(),
                            mLastSelectedChild.getLeft(),mLastSelectedChild.getTop()));
                    mLastSelectedChild.setStatus(GestureLockView3.STATUS_SINGER_ON);
                }
                if(null != selectedChild){

                    selectedChild.setStatus(GestureLockView3.STATUS_SINGER_ON);
                    if(!mSelected.contains(selectedChild)){
                        mLastSelectedChild = selectedChild;
                        mSelected.add(selectedChild);
                    }

                    if(mPath.isEmpty()){
                        mPath.moveTo(selectedChild.getLeft()+mOuterCirclerWidth,selectedChild.getTop()+mOuterCirclerWidth);
                    }else {
                        mPath.lineTo(selectedChild.getLeft()+mOuterCirclerWidth,selectedChild.getTop()+mOuterCirclerWidth);
                    }
                }
                mLastMoveX = event.getX();
                mLastMoveY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mCurrentStatus = GestureLockView3.STATUS_SINGER_UP;
                setSelectedItemStatsChanged(mCurrentStatus);
                break;
        }
        invalidate();
        return true;
    }

    private int getDegree(float x, float y, float mLastMoveX, float mLastMoveY) {
        double atan2 = Math.atan2((y - mLastMoveY),(x - mLastMoveX));
        return (int)Math.toDegrees(atan2);
    }

    private void setSelectedItemStatsChanged(int status) {
//        LogUtil.i("mSelected.size()="+mSelected.size());
        GestureLockView3 lastChild = null;
        GestureLockView3 currentChild = null;
        for (int i = 0; i < mSelected.size(); i++) {
            if(mSelected.size() == 1){//如果只选中了一个小圈，则不显示箭头
                mSelected.get(i).setDegree(-1);
                mSelected.get(i).setStatus(status);
                return;
            }

            currentChild = mSelected.get(i);
            if(null != lastChild){
                int degree = getDegree(currentChild.getLeft(),currentChild.getTop(),lastChild.getLeft(),lastChild.getTop());
//                LogUtil.i("lastChild.getId()="+lastChild.getId()+"---degree="+degree);
                lastChild.setDegree(degree);
                lastChild.setStatus(status);
            }
            lastChild = currentChild;

            if(i == mSelected.size()-1){//最后一个圆圈
                currentChild = mSelected.get(i);
//                LogUtil.i("currentChild.getId()="+currentChild.getId()+"---degree="+(-1));
                currentChild.setDegree(-1);
                currentChild.setStatus(status);
            }
        }
    }

    private GestureLockView3 getSelectedChildByPosition(float x, float y) {
        GestureLockView3 child = null;
        for (int i = 0; i < mChildres.length; i++) {
            child = mChildres[i];
//            if(i == 1){
//                LogUtil.i("child.getId()="+child.getId());
//                LogUtil.i("child.getLeft()="+child.getLeft());
//                LogUtil.i("child.getTop()="+child.getTop());
//                LogUtil.i("mOuterCirclerWidth="+mOuterCirclerWidth);
//                LogUtil.i("mInnerCircleRadius="+mInnerCircleRadius);
//            }
            if(x>=(child.getLeft()+mOuterCirclerWidth-mInnerCircleRadius)
                    && x<=(child.getLeft()+mOuterCirclerWidth+mInnerCircleRadius)
                    && y>=(child.getTop()+mOuterCirclerWidth-mInnerCircleRadius)
                    && y<=(child.getTop()+mOuterCirclerWidth+mInnerCircleRadius)){
                return child;
            }
        }
        return null;
    }

    private void reset() {
        mLastSelectedChild = null;
        mPath.reset();
        mSelected.clear();
        if(null != mChildres){
            for (int i = 0; i < mChildres.length; i++) {
                mChildres[i].setStatus(GestureLockView3.STATUS_NO_SINGER);
            }
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //绘制连接线
        canvas.save();
        canvas.drawPath(mPath,mPaint);
        if(mSelected.size()>0){
            if(mCurrentStatus == GestureLockView3.STATUS_SINGER_ON){
                mPaint.setAlpha(50);
                canvas.drawLine(mLastSelectedChild.getLeft()+mOuterCirclerWidth,
                        mLastSelectedChild.getTop()+mOuterCirclerWidth,
                        mLastMoveX,mLastMoveY,mPaint);
            }else if(mCurrentStatus == GestureLockView3.STATUS_SINGER_UP){
                mPaint.setColor(Color.TRANSPARENT);
                canvas.drawLine(mLastSelectedChild.getLeft()+mOuterCirclerWidth,
                        mLastSelectedChild.getTop()+mOuterCirclerWidth,
                        mLastMoveX,mLastMoveY,mPaint);
            }

        }
        canvas.restore();

    }
}
