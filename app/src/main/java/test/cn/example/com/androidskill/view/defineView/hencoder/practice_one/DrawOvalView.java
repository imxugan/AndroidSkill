package test.cn.example.com.androidskill.view.defineView.hencoder.practice_one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/14.
 */

public class DrawOvalView extends View {
    Paint mPaint;
    public DrawOvalView(Context context) {
        super(context);
        //设置抗锯齿效果
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public DrawOvalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawOval(100,100,500,300,mPaint);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(700,100,950,400,mPaint);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawOval(100,700,600,1200,mPaint);
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawOval(700,700,1000,1500,mPaint);

        mPaint.setColor(Color.YELLOW);
        RectF rectF = new RectF(700,1100,1000,1200);
        canvas.drawOval(rectF,mPaint);
    }
}
