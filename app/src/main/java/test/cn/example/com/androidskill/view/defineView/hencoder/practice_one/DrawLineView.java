package test.cn.example.com.androidskill.view.defineView.hencoder.practice_one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/14.
 */

public class DrawLineView extends View {
    Paint mPaint;
    public DrawLineView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public DrawLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(100,700,400,1100,mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawLine(700,700,1000,700,mPaint);
        float[] lines = new float[]{100,100,700,100,400,100,400,400,100,400,700,400};
        mPaint.setColor(Color.BLACK);
        canvas.drawLines(lines,mPaint);

    }
}
