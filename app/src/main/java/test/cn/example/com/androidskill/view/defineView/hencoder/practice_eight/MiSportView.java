package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/1/17.
 */

public class MiSportView extends View {
    private Paint mPaint;
    private float centerX,centerY,radius;
    private Path path;
    private boolean connected;
    private Rect rectBound;
    private Bitmap bitmap;

    public MiSportView(Context context) {
        super(context);
        initPaint();
    }

    public MiSportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MiSportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public void setConnected(boolean connected){
        this.connected = connected;
        invalidate();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(30);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        path = new Path();
        rectBound = new Rect();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq);
//        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth()/2,bitmap.getHeight()/2);
        bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/5,bitmap.getHeight()/5,false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        radius = centerX/2;
        if(!connected){
            canvas.drawCircle(centerX,centerY,radius,mPaint);
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(1);
            mPaint.setStyle(Paint.Style.FILL);
            path.moveTo(centerX,centerY-radius+30/2);
            path.lineTo(centerX+20,centerY-radius);
            path.lineTo(centerX,centerY-radius-30/2);
            path.lineTo(centerX,centerY-radius+30/2);
            canvas.drawPath(path,mPaint);
        }else {
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.GREEN);
            mPaint.setStrokeWidth(45);
            mPaint.setStyle(Paint.Style.STROKE);
            radius = radius+50;
            canvas.drawCircle(centerX,centerY,radius,mPaint);
            mPaint.setStrokeWidth(5);
            radius = radius-40;
            RectF rectF = new RectF(centerX-radius,centerY-radius,centerX+radius,centerY+radius);
            canvas.drawArc(rectF,270,270,false,mPaint);
            mPaint.setColor(Color.RED);
            for (int i = 0; i < 15; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawArc(centerX-radius,centerY-radius,centerX+radius,centerY+radius,180+(i*6),5,false,mPaint);
                }
            }
            mPaint.setTextSize(50);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPaint.setLetterSpacing(0.3f);
            }
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(1);
            mPaint.getTextBounds("2274",0,"2274".length(),rectBound);
            canvas.drawText("2274",centerX-rectBound. width()/2,centerY+rectBound.height()/2,mPaint);
            mPaint.setTextSize(20);
            mPaint.getTextBounds("1.5公里",0,"1.5公里".length(),rectBound);
            canvas.drawText("1.5公里",centerX-20-rectBound.width(),centerY+60+rectBound.height()/2,mPaint);
            canvas.drawLine(centerX,centerY+60-rectBound.height()/2,centerX,centerY+60+rectBound.height(),mPaint);
            mPaint.getTextBounds("34千卡",0,"34千卡".length(),rectBound);
            canvas.drawText("34千卡",centerX+20,centerY+60+rectBound.height()/2,mPaint);
            canvas.drawBitmap(bitmap,centerX-bitmap.getWidth()/2,centerY+60+rectBound.height()+30,mPaint);
        }

    }
}
