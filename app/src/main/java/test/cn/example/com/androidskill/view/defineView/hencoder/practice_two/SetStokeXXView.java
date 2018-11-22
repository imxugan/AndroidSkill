package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2018/11/22.
 */

public class SetStokeXXView extends View {
    private Paint mPaint;
    public SetStokeXXView(Context context) {
        super(context);
        initPaint();
    }

    public SetStokeXXView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SetStokeXXView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200,200,100,mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(500,200,100,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(20);
        canvas.drawCircle(800,200,100,mPaint);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setColor(Color.CYAN);
        canvas.drawLine(0,300,150,300,mPaint);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(0,330,150,330,mPaint);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(0,360,150,360,mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        Path path = new Path();
        path.moveTo(0,400);
        path.lineTo(200,400);
        path.lineTo(30,630);
        canvas.drawPath(path,mPaint);
        path = new Path();
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        path.moveTo(300,400);
        path.lineTo(500,400);
        path.lineTo(330,630);
        canvas.drawPath(path,mPaint);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        path = new Path();
        path.moveTo(600,400);
        path.lineTo(800,400);
        path.lineTo(630,630);
        canvas.drawPath(path,mPaint);
        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        path = new Path();
        path.moveTo(850,400);
        path.lineTo(1050,400);
        path.lineTo(880,630);
        canvas.drawPath(path,mPaint);

        mPaint.setColor(Color.DKGRAY);
        path = new Path();
        path.moveTo(0,730);
        path.lineTo(200,730);
        path.lineTo(20,780);
        canvas.drawPath(path,mPaint);

        mPaint.setStrokeMiter(10);
        mPaint.setColor(Color.GREEN);
        path = new Path();
        path.moveTo(0,830);
        path.lineTo(200,830);
        path.lineTo(20,880);
        canvas.drawPath(path,mPaint);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(20);
        canvas.drawText("setDither",0,950,mPaint);
        mPaint.setDither(true);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
        canvas.drawBitmap(bitmap,0,1000,mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawText("setFilterBitmap",0,1300,mPaint);
        mPaint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap,0,1350,mPaint);
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
}
