package test.cn.example.com.androidskill.view.defineView.hencoder.practice_one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by xugan on 2018/11/14.
 */

public class DrawCircleView extends View {
    Paint mPaint;
    public DrawCircleView(Context context) {
        super(context);
        //设置抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(android.R.color.background_dark));
        canvas.drawCircle(200,200,100,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(600,200,100,mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        canvas.drawCircle(200,600,100,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(600,600,100,mPaint);
        //画圆 边线一个颜色，里面一个颜色
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200,1000,100,mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(200,1000,80,mPaint);


    }
}
