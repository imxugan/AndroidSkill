package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/1.
 */

public class CustomTitleView extends View {
    private final int defaultWidth = DensityUtil.dp2Px(150);
    private final int defaultHeight = DensityUtil.dp2Px(150);
    private Paint mPaint;
    private float mTitleTextSize;
    private Rect mBound;
    private String mTitleText;
    private int mTitleTextColor;
    private Random mRandom;
    private Set<Integer> set = new HashSet<>();

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i =0;i<count;i++){
            int arr = array.getIndex(i);
            switch (arr){
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = array.getString(arr);
                    LogUtil.i("mTitleText="+mTitleText);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    mTitleTextColor = array.getColor(arr, Color.BLACK);
                    LogUtil.i("mTitleTextColor="+mTitleTextColor);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize = array.getDimension(arr, DensityUtil.dp2Px(28));
                    LogUtil.i("mTitleTextSize="+mTitleTextSize);
                    break;
                default:
                    String s = array.getString(arr);
                    LogUtil.i(""+s);
                    break;
            }
            array.recycle();
            mRandom = new Random();
            mPaint = new Paint();
            mPaint.setColor(Color.BLUE);
            mPaint.setTextSize(mTitleTextSize);
            mBound = new Rect();
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    set.clear();
                    mTitleText = getText();
                    postInvalidate();
                }
            });
        }
    }

    private String getText() {
        while(set.size()<4){
            set.add(mRandom.nextInt(10));
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> iterator = set.iterator();
        while(iterator.hasNext()){
            sb.append(iterator.next());
        }
        return sb.toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if(MeasureSpec.AT_MOST == widthSpecMode &&
                MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            setMeasuredDimension(defaultWidth,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(widthSpecSize,defaultHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i("onDraw======"+mTitleText);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText
                ,getWidth()/2-mBound.width()/2
                ,getHeight()/2 + mBound.height()/2
                ,mPaint);
    }
}
