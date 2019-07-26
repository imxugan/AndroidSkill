package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/25.
 */

public class PaintView extends View {
    private final Paint mPaint;
    private String str = "路人霍元甲abcd8";

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(context.getResources().getColor(R.color.c_00c8aa));
        mPaint.setStrokeWidth(50);
        mPaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Path path = new Path();
        path.moveTo(100,100);
        path.lineTo(300,100);
        path.lineTo(100,300);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        mPaint.setColor(getResources().getColor(R.color.c_00c8aa));
        canvas.drawPath(path, mPaint);

        path.moveTo(100,400);
        path.lineTo(300,500);
        path.lineTo(100,700);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, mPaint);

        path.moveTo(100,800);
        path.lineTo(300,800);
        path.lineTo(100,1100);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path, mPaint);
        float measureText = mPaint.measureText(str);
        LogUtil.i(""+measureText);

        float[] measuredWidth = new float[str.length()];
        int breakText = mPaint.breakText(str, true, 50, measuredWidth);
        LogUtil.i("通过paint.breakText()方法获取宽度为50的文字的个数="+breakText+"   这个50宽度范围内的字符个数的总宽度是"+measuredWidth[0]);

        mPaint.reset();
        mPaint.setColor(getResources().getColor(R.color.red));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        int topY = 1500;
        float x = 0;
        canvas.drawLine(0,topY,2000,topY, mPaint);

        //以topY坐标为文字的baseline绘制文字
        mPaint.reset();
        mPaint.setColor(getResources().getColor(R.color.black));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(30);
        canvas.drawText(str,0,topY,mPaint);

        //以topY坐标为文字的top进行绘制文字
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        x = 0+mPaint.measureText(str)+30;
        canvas.drawText(str,x,topY-fontMetrics.top,mPaint);

        //以topY的坐标为文字的bottom绘制文字
        x +=x;
        canvas.drawText(str,x,topY-fontMetrics.bottom,mPaint);

        //以topY的坐标为文字的纵向中心点绘制文字
        x +=mPaint.measureText(str)+30;
        canvas.drawText(str,x,(fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.bottom+topY,mPaint);
    }
}
