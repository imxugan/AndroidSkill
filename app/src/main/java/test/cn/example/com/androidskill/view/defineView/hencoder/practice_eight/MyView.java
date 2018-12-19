package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/17.
 */

public class MyView extends View {
    private Context context = null;
    private Paint mPaint;
    private int defaultWidth = 0;
    private int defaultHeight = 0;
    public MyView(Context context) {
        super(context);
        this.context = context;
        initPaint();
        defaultWidth = DensityUtil.dp2px(context,100);
        defaultHeight = DensityUtil.dp2px(context,100);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        defaultWidth = DensityUtil.dp2px(context,100);
        defaultHeight = DensityUtil.dp2px(context,100);
        LogUtil.e("defaultWidth +"+defaultWidth);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMesauredSpecsize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasuredSpecsize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthSpecMode){
            case MeasureSpec.AT_MOST:
                LogUtil.e("widthSpecMode= MeasureSpec.AT_MOST"+"   widthMesauredSpecsize ="+widthMesauredSpecsize);
                break;
            case MeasureSpec.EXACTLY:
                LogUtil.e("widthSpecMode= MeasureSpec.EXACTLY"+"   widthMesauredSpecsize ="+widthMesauredSpecsize);
                break;
            case MeasureSpec.UNSPECIFIED:
                LogUtil.e("widthSpecMode= MeasureSpec.UNSPECIFIED"+"   widthMesauredSpecsize ="+widthMesauredSpecsize);
                break;
        }

        switch (heightSpecMode){
            case MeasureSpec.AT_MOST:
                LogUtil.e("heightSpecMode= MeasureSpec.AT_MOST"+"   heightMeasuredSpecsize ="+heightMeasuredSpecsize);
                break;
            case MeasureSpec.EXACTLY:
                LogUtil.e("heightSpecMode= MeasureSpec.EXACTLY"+"   heightMeasuredSpecsize ="+heightMeasuredSpecsize);
                break;
            case MeasureSpec.UNSPECIFIED:
                LogUtil.e("heightSpecMode= MeasureSpec.UNSPECIFIED"+"   heightMeasuredSpecsize ="+heightMeasuredSpecsize);
                break;
        }

        //修正后的宽度
        int measuredWidth = resolveSize(defaultWidth, widthMeasureSpec);
        //修正后的高度
        int measuredHeight = resolveSize(defaultHeight, heightMeasureSpec);
        LogUtil.i(""+defaultWidth+"   "+defaultHeight+"   "+measuredWidth+"   "+measuredHeight);
        setMeasuredDimension(measuredWidth,measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        mPaint.setColor(Color.BLACK);
        canvas.drawText("abcdeft",getWidth()/2,getHeight()/2,mPaint);
    }
}
