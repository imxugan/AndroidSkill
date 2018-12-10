package test.cn.example.com.androidskill.view.defineView.hencoder.practice_five;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by xugan on 2018/12/10.
 */

public class SpottedLinearLayout extends LinearLayout {
    private Paint mPaint;
    public SpottedLinearLayout(Context context) {
        super(context);
        initPaint();
    }

    public SpottedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SpottedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawCircle(50,50,30,mPaint);
        canvas.drawCircle(100,30,20,mPaint);
        canvas.drawCircle(200,130,30,mPaint);
        canvas.drawCircle(300,400,50,mPaint);
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.RED);
    }
}
