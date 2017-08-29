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

public class InnerFallingParticle extends Particle {
    static Random mRandom = new Random();
    float mRadius = 8f;
    float mAlpha = 1.0f;
    float mX,mY;
    Rect mRect;
    public InnerFallingParticle(int color, float x, float y,Rect rect) {
        super(color, x, y);
        mRect = rect;
        mX = x;
        mY = y;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * mAlpha));
        canvas.drawCircle(x,y,mRadius,paint);
    }

    @Override
    protected void caculate(float factor) {
        if(factor <=0.5){
            if(mY < mRect.exactCenterY()){
                y = y + factor * mRandom.nextInt(mRect.height()/2);
                if(mX>mRect.exactCenterX()){
                    x = x - factor * mRandom.nextInt(mRect.width()/2) * (mRandom.nextFloat());
                }else {
                    x = x + factor * mRandom.nextInt(mRect.width()/2) * (mRandom.nextFloat());
                }
            }else {
                y = y + factor * mRandom.nextInt(mRect.height()/2);
                if(mX>mRect.exactCenterX()){
                    x = x - factor * mRandom.nextInt(mRect.width()/2) * mRandom.nextFloat();
                }else {
                    x = x + factor * mRandom.nextInt(mRect.width()/2) * mRandom.nextFloat();
                }
            }

            mRadius = mRadius - factor * mRandom.nextInt(2);
            mAlpha = (1f - factor) * (1 + mRandom.nextFloat());
        }
    }
}
