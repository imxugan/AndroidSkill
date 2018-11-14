package test.cn.example.com.androidskill.view.defineView.hencoder.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/14.
 */

public class CustomView3 extends View {
    Paint mPaint;
    public CustomView3(Context context) {
        super(context);
        //设置抗拒次效果
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public CustomView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(100,100,mPaint);

        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(300,100,mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setColor(Color.BLUE);
        canvas.drawPoint(600,100,mPaint);

        mPaint.setColor(Color.GREEN);
        float[] points = new float[4];
        for (int i = 0; i < points.length; i++) {
            points[i] = (i+1)*100;
            LogUtil.e(""+points[i]);
        }
        canvas.drawPoints(points,mPaint);

    }
}
