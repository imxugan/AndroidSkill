package test.cn.example.com.androidskill.model.explode.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import test.cn.example.com.androidskill.model.explode.Particle;

/**
 * Created by xgxg on 2017/8/29.
 */

public class BooleanParticle extends Particle {
    static Random mRandom = new Random();
    float mRadius = 8f;
    float mAlpha;
    Rect mRect;
    public BooleanParticle(int color, float x, float y,Rect rect) {
        super(color, x, y);
        mRect = rect;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int)(Color.alpha(color) * mAlpha));
        canvas.drawCircle(x,y,mRadius,paint);
    }

    @Override
    protected void caculate(float factor) {
        x = x + factor * mRandom.nextInt(mRect.width()) * (mRandom.nextFloat() - 0.5f);
        y = y + factor * mRandom.nextInt(mRect.height()) * (mRandom.nextFloat() - 0.5f);
        mRadius = mRadius - factor * mRandom.nextInt(2);

        mAlpha = (1f - factor) * (1 + mRandom.nextFloat());
    }
}
