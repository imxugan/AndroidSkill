package test.cn.example.com.androidskill.view.defineView.hencoder.practice_three;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/28.
 */

public class DrawTextVew extends View {
    private Paint mPaint;
    public DrawTextVew(Context context) {
        super(context);
        initPaint();
    }

    public DrawTextVew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DrawTextVew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        //canvas.drawText方法中的x,y两坐标的含义是
        //x 坐标是文字的起始位置的左边一点点的位置
        //y 坐标是表示的，文字baseline
        canvas.drawText("123abc",30,30,mPaint);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(30);
        Path path = new Path();
        path.moveTo(10,100);
        path.lineTo(90,160);
        path.lineTo(200,60);
        path.lineTo(300,130);
        path.lineTo(400,30);
        path.lineTo(500,100);
        //drawTextOnPath构造函数的中的参数
//        hOffset 为 5， vOffset 为 10，文字就会右移 5 像素和下移 10 像素。
        canvas.drawTextOnPath("123456abcdefg撒大大是多少的说法是电风扇是的发生的发生发达是打发斯蒂芬发送到发",path,10,20,mPaint);
        mPaint.setColor(Color.DKGRAY);
        canvas.drawPath(path,mPaint);

        path = new Path();
        path.moveTo(10,200);
        path.lineTo(90,260);
        path.lineTo(200,160);
        path.lineTo(300,230);
        path.lineTo(400,130);
        path.lineTo(500,200);
        mPaint.setColor(Color.RED);
        canvas.drawTextOnPath("123456abcdefg撒大大是多少的说法是电风扇是的发生的发生发达是打发斯蒂芬发送到发",path,0,0,mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(path,mPaint);


    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
}
