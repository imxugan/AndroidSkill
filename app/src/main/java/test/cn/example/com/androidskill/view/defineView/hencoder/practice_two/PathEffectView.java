package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/26.
 */

public class PathEffectView extends View {
    private Paint mPaint;
    public PathEffectView(Context context) {
        super(context);
        initPaint();
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(20);
        mPaint.setColor(Color.BLACK);
        canvas.drawText("CornerPathEffect",50,25,mPaint);
        PathEffect pathEffect = new CornerPathEffect(20);
        mPaint.setColor(Color.RED);
        mPaint.setPathEffect(pathEffect);
        Path path = new Path();
        path.moveTo(50,50);
        path.lineTo(200,200);
        path.lineTo(300,20);
        path.lineTo(330,80);
        path.lineTo(400,260);
        canvas.drawPath(path,mPaint);
        canvas.drawText("DashPathEffect",0,270,mPaint);
        mPaint.setColor(Color.BLACK);
        PathEffect dashPathEffect = new DashPathEffect(new float[]{10,5},3);
        mPaint.setPathEffect(dashPathEffect);
        canvas.drawCircle(120,350,80,mPaint);
        dashPathEffect = new DashPathEffect(new float[]{30,10,10,5},30);
        mPaint.setPathEffect(dashPathEffect);
        canvas.drawCircle(300,350,80,mPaint);

        int height = getHeight();
        int width = getWidth();
        mPaint.setColor(Color.BLUE);
        //DashPathEffect中的第二个参数 phase 的理解如下
        //把画的虚线比作一个两端无限延长的绳子，绳子由实线虚线组成，
        // 偏移量就是把绳子往起始点里面拽拽。但线的总体长度是不变的。看到这个比喻已经很清楚了。
        // 绘制一条参考线
        canvas.drawLine(60, 480, 60, height-500, mPaint);

        // 绘制第一条虚线
        DashPathEffect dashPathEffect1 = new DashPathEffect(new float[]{60, 60}, 0);
        mPaint.setPathEffect(dashPathEffect1);
        path.reset();
        path.moveTo(0, height / 10+400);
        path.lineTo(width, height / 10+400);
        canvas.drawPath(path, mPaint);

        // 绘制第二条虚线
        DashPathEffect dashPathEffect2 = new DashPathEffect(new float[]{60, 60}, 20);
        mPaint.setPathEffect(dashPathEffect2);
        path.reset();
        path.moveTo(0, height * 2 / 10+400);
        path.lineTo(width, height * 2 / 10+400);
        canvas.drawPath(path, mPaint);

        // 绘制第三条虚线
        DashPathEffect dashPathEffect3 = new DashPathEffect(new float[]{60, 60}, 40);
        mPaint.setPathEffect(dashPathEffect3);
        path.reset();
        path.moveTo(0, height * 3 / 10+400);
        path.lineTo(width, height * 3 / 10+400);
        canvas.drawPath(path, mPaint);

        // 绘制第四条虚线
        DashPathEffect dashPathEffect4 = new DashPathEffect(new float[]{60, 60}, 60);
        mPaint.setPathEffect(dashPathEffect4);
        path.reset();
        path.moveTo(0, height * 4 / 10+400);
        path.lineTo(width, height * 4 / 10+400);
        canvas.drawPath(path, mPaint);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        path = new Path();
        path.moveTo(0,1150);
        path.lineTo(100,1110);
        path.lineTo(200,1290);
        canvas.drawPath(path,mPaint);
        DiscretePathEffect discretePathEffect = new DiscretePathEffect(10, 12);
        mPaint.setPathEffect(discretePathEffect);
        path = new Path();
        path.moveTo(0,1250);
        path.lineTo(100,1210);
        path.lineTo(200,1390);
        canvas.drawPath(path,mPaint);

    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
}
