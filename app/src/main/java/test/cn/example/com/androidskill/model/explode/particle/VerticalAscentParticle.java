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

public class VerticalAscentParticle extends Particle {
    static Random mRandom = new Random();
    float mRadius = 8f;
    float mAlpah;
    Rect mRect;
    float mX,mY;
    public VerticalAscentParticle(int color, float x, float y,Rect rect) {
        super(color, x, y);
        mX = x;
        mY = y;
        mRect = rect;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int)(Color.alpha(color) * mAlpah));
        canvas.drawCircle(x,y,mRadius,paint);
    }

    @Override
    protected void caculate(float factor) {
        if(mX > mRect.exactCenterX()){
            x = x + factor * mRandom.nextInt(mRect.width()) * mRandom.nextFloat();
        }else {
            x = x - factor * mRandom.nextInt(mRect.width()) * mRandom.nextFloat();
        }

        if(factor <=0.5){
            y = y - factor * mRandom.nextInt(mRect.height()/2);
        }else {
            y = y + factor * mRandom.nextInt(mRect.height()/2);
        }

        mRadius = mRadius - factor * mRandom.nextInt(2);

        mAlpah = (1f - factor) * (1 + mRandom.nextFloat());
    }
}
