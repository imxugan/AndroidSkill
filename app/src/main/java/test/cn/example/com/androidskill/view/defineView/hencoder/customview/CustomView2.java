package test.cn.example.com.androidskill.view.defineView.hencoder.customview;

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

public class CustomView2 extends View {
    Paint mPaint ;
    public CustomView2(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
//        在android中Rect和RextF都是用来创建一个矩形的，
//        Rect的参数是  int型   ，  RectF的参数是float型，由此可以看出RectF比Rect的精确度更高
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(100f,100f,400f,400f,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(700,100,1000,400,mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(100,700,400,1000,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawRect(700,700,1000,1000,mPaint);

        mPaint.reset();
        //paint重置后，如果需要再次设置抗锯齿效果，则setAntiAlias(true)
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.DKGRAY);
        canvas.drawRoundRect(100,1100,400,1400,60,40,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawRoundRect(700,1100,1000,1400,50,50,mPaint);
    }
}
