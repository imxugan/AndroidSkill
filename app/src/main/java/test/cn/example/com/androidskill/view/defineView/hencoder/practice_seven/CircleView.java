package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/16.
 */

public class CircleView extends View {
    private Paint mPaint;
    private int color;
    private float[] point;
    public CircleView(Context context) {
        super(context);
        inPaint();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inPaint();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inPaint();
    }

    private void inPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        point = new float[]{100,100};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.i(""+color);
        LogUtil.i(""+point[0]+"     "+point[1]);
        canvas.drawCircle(point[0],point[1],100,mPaint);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(color);
        invalidate();
    }

    public float[] getPoint() {
        return point;
    }

    public void setPoint(float[] point) {
        this.point = point;
        invalidate();
    }
}
