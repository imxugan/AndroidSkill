package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;


/**
 * Created by xugan on 2019/7/28.
 */

public class CustomProgerssBar3 extends View {

    private final int roundColor;
    private final int roundProgressColor;
    private final float roundWidth;
    private final float textSize;
    private final int textColr;
    private final boolean textShow;
    private final int STROKE = 0;
    private final int FILL = 1;
    private final int style;
    private final int max;
    private final int paddingRight;
    private final int paddingBottom;
    private int progress;
    private final Paint mPaint;
    private int defaultWidth = 0;
    private int defaultHeight = 0;
    private final int paddingLeft;
    private final int paddingTop;

    public CustomProgerssBar3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgerssBar3);
        roundColor = typedArray.getColor(R.styleable.CustomProgerssBar3_roundColor, Color.CYAN);
        roundProgressColor = typedArray.getColor(R.styleable.CustomProgerssBar3_roundProgressColor, Color.RED);
        roundWidth = typedArray.getDimension(R.styleable.CustomProgerssBar3_roundWidth, 55);
        textSize = typedArray.getDimension(R.styleable.CustomProgerssBar3_textSize, 10);
        textColr = typedArray.getColor(R.styleable.CustomProgerssBar3_textColor, Color.BLUE);
        textShow = typedArray.getBoolean(R.styleable.CustomProgerssBar3_textShow, true);
        style = typedArray.getInt(R.styleable.CustomProgerssBar3_style, STROKE);
        max = typedArray.getInt(R.styleable.CustomProgerssBar3_max, 100);
        mPaint = new Paint();
        LogUtil.i("roundWidth=  "+roundWidth+"     "+ DensityUtil.px2dip(context,roundWidth));
        typedArray.recycle();
        defaultWidth = DensityUtil.dip2px(context,60);
        defaultHeight = DensityUtil.dip2px(context,60);
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        LogUtil.i("paddingLeft= "+ paddingLeft+"  paddingRight="+paddingRight+"  paddingTop="+paddingTop+"  paddingBottom="+paddingBottom);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resolveWidthSize = resolveSize(defaultWidth, widthMeasureSpec);
        int resolveHeightSize = resolveSize(defaultHeight, heightMeasureSpec);
        setMeasuredDimension(resolveWidthSize,resolveHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStyle((style==STROKE)? Paint.Style.STROKE: Paint.Style.FILL);
        mPaint.setStrokeWidth(roundWidth);
        mPaint.setColor(roundProgressColor);
        int width = getWidth()-paddingLeft-paddingRight;
        int height = getHeight()-paddingTop-paddingBottom;
        int centerX = paddingLeft+width/2;
        int centerY = paddingTop+height/2;
        int radius = Math.min(width,height)/2;
        radius = (int) (radius-roundWidth/2);
        canvas.drawCircle(centerX,centerY,radius,mPaint);
        float percent = 0.0f;
        if(textShow){
            //绘制进度百分比
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setStyle((style==STROKE)? Paint.Style.STROKE: Paint.Style.FILL);
            mPaint.setTextSize(textSize);
            mPaint.setColor(textColr);
            percent = progress/(float)max * 100;
            Rect bounds = new Rect();// 矩形
            String text = (int)percent + "%";
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            int textHeight = bounds.height();
            int textWidth = bounds.width();
//        measureText() 会在左右两侧加上一些额外的宽度值，而 getTextBounds() 则是返回需要的最小宽度而已
            LogUtil.i("textWidth="+textWidth+"        textHeight="+textHeight+"       mPaint.measureText(text)="+mPaint.measureText(text));
            float x =  centerX-textWidth/2;
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            LogUtil.e("fontMetrics.descent="+fontMetrics.descent+"          fontMetrics.ascent="+fontMetrics.ascent);
            LogUtil.i("descent="+mPaint.descent()+"         ascent="+mPaint.ascent()+"     textHeight="+(mPaint.descent()-mPaint.ascent()));
            canvas.drawText(text, x,(centerY+textHeight/2),mPaint);
        }


        //绘制圆弧
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(style==STROKE? Paint.Style.STROKE: Paint.Style.FILL);
        mPaint.setStrokeWidth(roundWidth);
        mPaint.setColor(roundColor);
        float left = centerX-radius;
        float top = centerY-radius;
        float right = centerX+radius;
        float bottom = centerY+radius;
        RectF rectF = new RectF(left,top,right,bottom);
        boolean useCenter = (style==FILL);
        LogUtil.i("(progress/(float)max*360)=   "+(progress/(float)max*360));
        canvas.drawArc(rectF,0,progress/(float)max*360,useCenter,mPaint);
    }

    /**
     * 自定义的属性progress,方便使用属性动画
     * @param progress
     */
    public synchronized void setProgress(int progress){
        this.progress = progress;
        invalidate();
    }

    public synchronized int getProgress(){
        return this.progress;
    }

}
