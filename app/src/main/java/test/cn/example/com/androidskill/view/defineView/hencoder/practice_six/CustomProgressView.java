package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/12.
 */

public class CustomProgressView extends View {
    private Paint mPaint;
    private TextPaint textPaint;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        LogUtil.i(""+progress);
        invalidate();
    }

    private int progress;

    public CustomProgressView(Context context) {
        super(context);
        initPaint();
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int radius = 100;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(centerX-radius,centerY-radius,centerX+radius,centerY+radius,0,progress*3.6f,false,mPaint);
        }
        String text = progress+"%";
        Rect rect = new Rect();
        textPaint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,centerX-rect.width()/2,centerY+rect.height()/2,textPaint);
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
        mPaint.setColor(Color.RED);
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(50);
    }
}
