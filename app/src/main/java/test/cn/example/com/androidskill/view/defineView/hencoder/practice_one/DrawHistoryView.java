package test.cn.example.com.androidskill.view.defineView.hencoder.practice_one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/15.
 */

public class DrawHistoryView extends View {
    Paint mPaint;
    public DrawHistoryView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public DrawHistoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawHistoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawRect(100,100,200,500,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(300,200,400,200,mPaint);
        canvas.drawLine(400,200,400,500,mPaint);
        canvas.drawLine(400,500,300,500,mPaint);
        canvas.drawLine(300,500,300,200,mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.BLUE);
        float[] floats = new float[]{500,300,600,300,600,300,600,500,600,500,500,500,500,500,500,300};
        canvas.drawLines(floats,mPaint);

        mPaint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(700,400);
        path.lineTo(800,400);
        path.lineTo(800,500);
        path.rLineTo(-100,0);
        path.close();
        canvas.drawPath(path,mPaint);
    }
}
