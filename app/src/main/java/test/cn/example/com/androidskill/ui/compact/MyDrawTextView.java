package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import test.cn.example.com.androidskill.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

public class MyDrawTextView extends View {
    private int defaultWidth = 0;
    private int defaultHeight = 0;
    private String text = "甲afgb8";
    private String text_top = "top";
    private String text_ascent = "ascent";
    private String text_descent = "descent";
    private String text_bottom = "bottom";
    private Paint mPaint;
    private int bigTextSize;
    private int smallTextSize;

    public MyDrawTextView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        LogUtil.i("init");
    }

    private void init(Context context) {
        defaultWidth = DensityUtil.dp2px(context,60);
        defaultHeight = DensityUtil.dp2px(context,60);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        bigTextSize = DensityUtil.sp2px(context, 56);
        smallTextSize = DensityUtil.sp2px(context, 16);
        mPaint.setTextSize(bigTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resolveWidthSize = resolveSize(defaultWidth, widthMeasureSpec);
        int resolveHeightSize = resolveSize(defaultHeight, heightMeasureSpec);
        setMeasuredDimension(resolveWidthSize,resolveHeightSize);
        LogUtil.i("onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(bigTextSize);
        Rect rect = new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;//top的值是baseline上方的一个纵坐标减去baseline后的结果，所以top的值是负的
        float ascent = fontMetrics.ascent;//ascent的值是baseline上方的一个纵坐标减去baseline后的结果，所以，ascent的值是负的
        float descent = fontMetrics.descent;//descent的值是baseline下方的一个纵坐标减去baseline后的结果，所以descent的值是正的
        float bottom = fontMetrics.bottom;//bottyom的值是baseline下方的一个纵坐标减去baseline后的结果，所以，bottom的值是正的s
        float leading = fontMetrics.leading;//这个值是第一个行的bottom和第二行的top的距离
        LogUtil.e("   top=  "+top+"   ascent="+ascent+"   descent="+descent+"   bottom="+bottom);
        LogUtil.e("leading="+leading+"  rect.height()="+rect.height()+"    (descent-ascent)="+(descent-ascent));
        //两种获取文字宽度的对比
        LogUtil.e("  rect.width()="+rect.width()+"    (mPaint.measureText(text))="+(mPaint.measureText(text)));
        float x = (getWidth()-rect.width())/2;
        float baseLine = (getHeight()-rect.height())/2;
        canvas.drawText(text,x,baseLine,mPaint);

        LogUtil.e("baseLine=  "+baseLine);

        //绘制文字的 top 线
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(smallTextSize);
        mPaint.setStrokeWidth(5);
        rect = new Rect();
        mPaint.getTextBounds(text_top,0,text_top.length(),rect);
        canvas.drawText(text_top,0,baseLine+top,mPaint);
        canvas.drawLine(0+rect.width(),baseLine+top,getWidth(),baseLine+top,mPaint);

        //绘制文字的 ascent 线
        mPaint.setColor(Color.GREEN);
        rect = new Rect();
        mPaint.getTextBounds(text_ascent,0,text_ascent.length(),rect);
        canvas.drawText(text_ascent,getWidth()-rect.width(),baseLine+ascent,mPaint);
        canvas.drawLine(0,baseLine+ascent,getWidth()-rect.width(),baseLine+ascent,mPaint);

        //绘制文字的 baseLine
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(rect.width(),baseLine,getWidth(),baseLine,mPaint);

        //绘制文字的 descent 线
        mPaint.setColor(Color.GRAY);
        rect = new Rect();
        mPaint.getTextBounds(text_descent,0,text_descent.length(),rect);
        canvas.drawText(text_descent,0,baseLine+descent,mPaint);
        canvas.drawLine(rect.width(),baseLine+descent,getWidth(),baseLine+descent,mPaint);

        mPaint.setColor(Color.CYAN);
        rect = new Rect();
        mPaint.getTextBounds(text_bottom,0,text_bottom.length(),rect);
        canvas.drawText(text_bottom,getWidth()-rect.width(),baseLine+bottom+rect.height(),mPaint);
        canvas.drawLine(0,baseLine+bottom,getWidth()-rect.width(),baseLine+bottom,mPaint);


    }
}
