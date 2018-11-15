package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/15.
 */

public class SetColorView extends View {
    Paint mPaint;
    public SetColorView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public SetColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SetColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawRect(100,100,300,300,mPaint);

        mPaint.setColor(Color.parseColor("#ff980e"));
        canvas.drawCircle(500,200,100,mPaint);

        mPaint.setARGB(80,100,80,200);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawLine(700,100,800,200,mPaint);
    }
}
