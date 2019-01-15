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

public class RuleView extends View {
    private Paint mPaint;
    private Paint cursorPaint;
    private int shortMark,longMark;
    private float startX,startY;
    private TextPaint textPaint;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        LogUtil.i(""+this.offset);
        invalidate();
    }

    private int offset = 0;



    public RuleView(Context context) {
        super(context);
        initPaint();
    }

    public RuleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RuleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
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
    }
}
