package test.cn.example.com.androidskill.model.explode.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import test.cn.example.com.androidskill.model.explode.Particle;
import test.cn.example.com.androidskill.model.explode.factory.FallingParticleFactory;

/**
 * Created by xgxg on 2017/8/28.
 */

public class FallingParticle extends Particle {
    static Random random = new Random();
    float radius = FallingParticleFactory.PART_WH;
    float alpha = 1.0f;
    private Rect rect;
    public FallingParticle(int color, float x, float y) {
        super(color, x, y);
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int)(Color.alpha(color) * alpha));
        canvas.drawCircle(x,y,radius,paint);
    }

    public FallingParticle(int color,float x,float y ,Rect rect){
        this(color,x,y);
        this.rect = rect;
    }

    @Override
    protected void caculate(float factor) {
        x = x + factor * random.nextInt(rect.width()) * (random.nextFloat() * 0.5f);
        y = y + factor * random.nextInt(rect.height()/2) ;
        radius = radius - factor * random.nextInt(2);
        alpha = (1f - factor) * (1 + random.nextFloat());
    }
}
