package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/21.
 */

public class RuleView2 extends View {
    private Paint mPaint;
    private Paint cursorPaint;
    private int shortMark,longMark;
    private float startX,startY;
    private TextPaint textPaint;
    private ValueAnimator valueAnimator;
    private int animatedValue;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        LogUtil.i(""+this.offset);
        valueAnimator.setIntValues(offset);
        valueAnimator.start();
    }

    private int offset = 0;



    public RuleView2(Context context) {
        super(context);
        initPaint();
    }

    public RuleView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RuleView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        cursorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cursorPaint.setStyle(Paint.Style.STROKE);
        cursorPaint.setStrokeWidth(5);
        cursorPaint.setColor(Color.RED);
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        shortMark = 10;
        longMark = 30;
        startX = 10;
        startY = 10;
        //如果采用 valueAnimator = new ValueAnimator();方式初始化valueAnimator时，
        // 需要调用setIntValues方法，设置valueAnimator的数据变换的起始数据和结束数据，
        //否则会报  java.lang.NullPointerException: Attempt to get length of null array 异常

        valueAnimator = ValueAnimator.ofInt(0,offset);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedValue = (int) valueAnimator.getAnimatedValue();
                LogUtil.i("valueAnimator.getAnimatedValue()=   "+animatedValue);
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
//        canvas.translate(animatedValue,0);

        LogUtil.i("cursorPaint.getStrokeWidth()/2=   "+cursorPaint.getStrokeWidth()/2);
        canvas.drawLine(300+startX-cursorPaint.getStrokeWidth()/2,startY,300+startX-cursorPaint.getStrokeWidth()/2,startY+longMark,cursorPaint);
        for (int i = 0; i <=60; i++) {
            if((i+offset)%10==0){
                canvas.drawLine(i*10+startX,startY,i*10+startX,longMark+startY,mPaint);
                canvas.drawText(""+(i+offset),(i*10+startX)-textPaint.measureText(""+i)/2,longMark+50,textPaint);
            }else {
                canvas.drawLine(i*10+startX,startY,i*10+startX,shortMark+startY,mPaint);
            }
        }
        canvas.restore();
    }
}
