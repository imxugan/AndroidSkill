package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;

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
        mPaint.setTextSize(20);
        canvas.drawText("Shader.TileMode.CLAMP",100,50,mPaint);
        Shader shader = new LinearGradient(100,100,300,300, Color.RED,Color.YELLOW, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        canvas.drawRect(100,100,300,300,mPaint);

        canvas.drawText("Shader.TileMode.MIRROR",400,50,mPaint);
        shader = new LinearGradient(400,100,600,300,Color.RED,Color.YELLOW, Shader.TileMode.MIRROR);
        mPaint.setShader(shader);
        canvas.drawRect(400,100,600,300,mPaint);
    }
}
