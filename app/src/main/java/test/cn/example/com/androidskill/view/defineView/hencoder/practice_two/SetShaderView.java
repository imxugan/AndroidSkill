package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/15.
 */

public class SetShaderView extends View {
    Paint mPaint;
    public SetShaderView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public SetShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SetShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        这里我们的重复模式没有起作用，是因为渐变的区域正好等于画布绘制的区域，
//        填充模式使用的前题是，填充的区域小于绘制的区域。就和用图片做桌面一样，
//        如果图片大小大于等于桌面的大小，自然就不会出现平铺拉伸的效果了，如果是用小图做桌面，
//        那么就要看看是怎么一个拉伸法。
        mPaint.setTextSize(20);
        canvas.drawText("Shader.TileMode.CLAMP",100,50,mPaint);
        Shader shader = new LinearGradient(100,100,100,160, Color.RED, Color.YELLOW, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        canvas.drawRect(100,100,300,400,mPaint);

        canvas.drawText("Shader.TileMode.REPEAT",400,50,mPaint);
        shader = new LinearGradient(400,100,400,160,Color.RED, Color.YELLOW, Shader.TileMode.REPEAT);
        mPaint.setShader(shader);
        canvas.drawRect(400,100,600,400,mPaint);

        mPaint.setTextSize(20);
        canvas.drawText("Shader.TileMode.MIRROR",700,50,mPaint);
        shader = new LinearGradient(700,100,700,160,Color.RED, Color.YELLOW, Shader.TileMode.MIRROR);
        mPaint.setShader(shader);
        canvas.drawRect(700,100,900,400,mPaint);

        RadialGradient radialGradient = new RadialGradient(200, 600, 50, Color.RED, Color.YELLOW, Shader.TileMode.CLAMP);
        mPaint.setShader(radialGradient);
        canvas.drawCircle(200,600,100,mPaint);

        radialGradient = new RadialGradient(500,600,50,Color.RED,Color.YELLOW, Shader.TileMode.REPEAT);
        mPaint.setShader(radialGradient);
        canvas.drawCircle(500,600,100,mPaint);

        radialGradient = new RadialGradient(800,600,50,Color.RED,Color.YELLOW,Shader.TileMode.MIRROR);
        mPaint.setShader(radialGradient);
        canvas.drawCircle(800,600,100,mPaint);

        canvas.drawText("sweepGradient不存在Shader.TileMode",100,750,mPaint);
        SweepGradient sweepGradient = new SweepGradient(200, 900, Color.RED, Color.YELLOW);
        mPaint.setShader(sweepGradient);
        canvas.drawCircle(200,900,100,mPaint);

        sweepGradient = new SweepGradient(500,900,Color.RED,Color.YELLOW);
        mPaint.setShader(sweepGradient);
        canvas.drawCircle(500,900,100,mPaint);

        sweepGradient = new SweepGradient(800,900,Color.RED,Color.YELLOW);
        mPaint.setShader(sweepGradient);
        canvas.drawCircle(800,900,100,mPaint);

    }
}
