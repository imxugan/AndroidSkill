package test.cn.example.com.androidskill.view.defineView.hencoder.practice_one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/15.
 */

public class DrawPathView extends View {
    Paint mPaint;
    public DrawPathView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public DrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
        Path path = new Path();
        path.addArc(100,100,300,800,170,190);
        path.addArc(300,100,500,800,10,-190);
        canvas.drawPath(path,mPaint);

        //画爱心的图
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        path.reset();

        float radius = 100;
        double leftx = 700-radius*Math.cos(30*Math.PI/180);
        double lefty = 200+radius*Math.sin(30*Math.PI/180);
        LogUtil.e(Math.cos(60*Math.PI/180)+"       "+Math.sin(60));
//        LogUtil.e(""+leftx+"       "+lefty);
//        LogUtil.e(""+(float)leftx+"      "+(float)lefty);
        path.moveTo((float) leftx,(float) lefty);
        double movex = 800;
        double movey = 200+radius*Math.tan((180-30)/2*Math.PI/180);
        path.lineTo((float) movex,(float) movey);
        double movexEnd = 900+radius*Math.cos(30*Math.PI/180);
        double moveyEnd = 200+radius*Math.sin(30*Math.PI/180);
        path.lineTo((float) movexEnd,(float) moveyEnd);
        radius = (float) ((movexEnd-leftx)/4);
        path.addArc((float) leftx,(float) (lefty-radius),(float) (leftx+2*radius),(float) (lefty+radius),180,180);
        path.addArc((float)(leftx+2*radius) ,(float) (lefty-radius),(float) movexEnd,(float) (moveyEnd+radius),180,180);
        canvas.drawPath(path,mPaint);


    }
}
