package test.cn.example.com.androidskill.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/9.
 */

public class CircleView extends View {
    Paint p = new Paint();
    int mColor;
    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs){
//        super(context,attrs);
        this(context,attrs,0);//记得这里调用下面的重载方法
    }

    public CircleView(Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        //context.obtainStyledAttributes(attrs, R.styleable.CircleView,defStyleAttr,R.style.AppTheme);
        mColor = a.getColor(R.styleable.CircleView_circle_color, Color.BLUE);
        LogUtil.i(""+mColor);
        a.recycle();
        initColor();
    }

    private void initColor(){
        p.setColor(Color.BLACK);
    }

   @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        //width为match_parent时，widthSpecMode是MeasureSpec.EXACTLY,widthSpecSize = 720
        // width为wrap_content时，widthSpecMode是MeasureSpec.AT_MOST,widthSpecSize = 720
        LogUtil.i("widthSpecMode="+widthSpecMode);
        LogUtil.i("widthSpecSize="+widthSpecSize);
//        LogUtil.i("heightSpecMode="+heightSpecMode);
//        LogUtil.i("widthMeasureSpec="+widthMeasureSpec+"---heightMeasureSpec="+heightMeasureSpec);
        LogUtil.i("MeasureSpec.EXACTLY="+MeasureSpec.EXACTLY);
        LogUtil.i("MeasureSpec.AT_MOST="+MeasureSpec.AT_MOST);
        LogUtil.i("MeasureSpec.UNSPECIFIED="+MeasureSpec.UNSPECIFIED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i(""+mColor);
        canvas.drawColor(mColor);
    }
}
