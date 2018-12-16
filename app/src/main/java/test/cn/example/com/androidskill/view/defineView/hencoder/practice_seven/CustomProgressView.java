package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/12/16.
 */

public class CustomProgressView extends View {
    private Paint mPaint;
    private float progress;
    private TextPaint textPaint;
    private String text;
    private Rect textBounds;

    public CustomProgressView(Context context) {
        super(context);
        initPait();
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPait();
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPait();
    }

    private void initPait() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(40);
        textBounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(20,20,320,320);
        canvas.drawArc(rectF,0,progress*3.6f,false,mPaint);
        text = (int)progress+"%";
        textPaint.getTextBounds(text,0,text.length(),textBounds);
        canvas.drawText(text,170-textBounds.width()/2,170+textBounds.height()/2,textPaint);
    }


    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
