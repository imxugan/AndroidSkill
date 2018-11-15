package test.cn.example.com.androidskill.view.defineView.hencoder.practice_one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/15.
 */

public class DrawPieView extends View {
    Paint mPaint;
    public DrawPieView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public DrawPieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //采用drawArc的方式画
        RectF rectF = new RectF(100,100,700,700);
        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF,180,120,true,mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(rectF,0,-50,true,mPaint);
        mPaint.setColor(Color.CYAN);
        canvas.drawArc(rectF,5,15,true,mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(rectF,25,12,true,mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(rectF,47,30,true,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF,80,90,true,mPaint);

        //采用arcTo方式画
        float radius = 300;
        float centerX = 400;
        float centerY = 1200;
        float endX = 0;
        float endY =0;
        rectF = new RectF(100,900,700,1500);
        Path path = new Path();
        mPaint.setColor(Color.RED);
        path.arcTo(rectF,180,120,false);
        path.lineTo(centerX,centerY);
        endX = centerX-radius;
        endY = centerY;
        path.lineTo(endX,endY);
        path.close();
        canvas.drawPath(path,mPaint);
        mPaint.setTextSize(30);
        //在红色百分比的左边画文字
        float textEndX = centerX-(float) (radius*Math.cos(120/2*Math.PI/180));
        float textEndY = centerY- (float) (radius*Math.sin(120/2*Math.PI/180));

        float textMoveX = textEndX - 30;
        float textMoveY = textEndY - 30;

        float textStartX = textMoveX - 100;
        float textStartY = textMoveY;

        canvas.drawText("红色部分",textStartX,textStartY,mPaint);
        canvas.drawLine(textStartX,textStartY,textMoveX,textMoveY,mPaint);
        canvas.drawLine(textMoveX,textMoveY,textEndX,textEndY,mPaint);

        path.reset();
        mPaint.setColor(Color.YELLOW);
        path.arcTo(rectF,0,-50,false);
        path.lineTo(centerX,centerY);
        endX = centerX+radius;
        endY = centerY;
        path.lineTo(endX,endY);
        canvas.drawPath(path,mPaint);

        path.reset();
        mPaint.setColor(Color.CYAN);
        path.arcTo(rectF,5,15,false);
        path.lineTo(centerX,centerY);
        endX = centerX+(float) (radius*Math.cos(5*Math.PI/180));
        endY = centerY+ (float) (radius*Math.sin(5*Math.PI/180));
        path.lineTo(endX,endY);
        canvas.drawPath(path,mPaint);

        path.reset();
        mPaint.setColor(Color.GREEN);
        path.arcTo(rectF,25,12,false);
        path.lineTo(centerX,centerY);
        endX = centerX+(float) (radius*Math.cos(25*Math.PI/180));
        endY = centerY+(float) (radius*Math.sin(25*Math.PI/180));
        path.lineTo(endX,endY);
        canvas.drawPath(path,mPaint);

        path.reset();
        mPaint.setColor(Color.GRAY);
        path.arcTo(rectF,47,30,false);
        path.lineTo(centerX,centerY);
        endX = centerX+(float) (radius*Math.cos(47*Math.PI/180));
        endY = centerY+(float) (radius*Math.sin(47*Math.PI/180));
        path.lineTo(endX,endY);
        canvas.drawPath(path,mPaint);

        path.reset();
        mPaint.setColor(Color.BLUE);
        path.arcTo(rectF,80,90,false);
        path.lineTo(centerX,centerY);
        endX = centerX+(float) (radius*Math.cos(80*Math.PI/180));
        endY = centerY+(float) (radius*Math.sin(80*Math.PI/180));
        path.lineTo(endX,endY);
        canvas.drawPath(path,mPaint);


    }
}
